package com.bmwu;

import com.bmwu.utils.Base64Coder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

/**
 * Created by michael on 3/18/17.
 */
public class loggerTrack {

    private interface Logger {
        void send(Map<String, Object> message);

        void flush();

        void close();
    }

    public interface AsyncBatchLoggerCallback {
        void onFlushTask(Future<Boolean> task);
    }

    /**
     * Async模式下，需要仔细处理缓存中的数据，如由于异步发送不及时导致缓存队列过大、程序停止时缓存队列清空等问题。
     */
    public static class AsyncBatchLogger implements Logger {

        private final static int MAX_FLUSH_BULK_SIZE = 1000;

        private final List<Map<String, Object>> messageList;
        private final HttpLogger httpLogger;
        private final ObjectMapper jsonMapper;
        private final ThreadPoolExecutor executor;
        private final AsyncBatchLoggerCallback callback;
        private final int bulkSize;

        public AsyncBatchLogger(final String serverUrl, final int bulkSize,
                                final ThreadPoolExecutor executor, final AsyncBatchLoggerCallback callback) {
            this.messageList = new ArrayList<>();
            this.httpLogger = new HttpLogger(serverUrl, null);
            this.jsonMapper = getJsonObjectMapper();
            this.bulkSize = Math.min(MAX_FLUSH_BULK_SIZE, bulkSize);
            this.executor = executor;
            this.callback = callback;
        }

        @Override public void send(Map<String, Object> message) {
            synchronized (messageList) {
                messageList.add(message);
                if (messageList.size() >= bulkSize) {
                    flush();
                }
            }
        }

        @Override public void flush() {
            synchronized (messageList) {
                try {
                    final String sendingData = jsonMapper.writeValueAsString(messageList);
                    final Future<Boolean> task = executor.submit(new Callable<Boolean>() {
                        @Override public Boolean call() throws Exception {
                            int reties = 0;
                            while (reties < 5) {
                                try {
                                    httpLogger.consume(sendingData);
                                    break;
                                } catch (IOException e) {
                                    log.error(String.format(
                                            "Failed to dump message with AsyncBatchConsumer, retied %d times:" + " "
                                                    + "%s", reties, sendingData), e);
                                    // XXX: 发生错误时，默认1秒后才重试
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e1) {
                                    }
                                    reties = reties + 1;
                                } catch (HttpLogger.HttpConsumerException e) {
                                    log.error(String.format(
                                            "Failed to dump message with AsyncBatchConsumer, retied %d times:" + " "
                                                    + "%s", reties, sendingData), e);
                                    // XXX: 发生错误时，默认1秒后才重试
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e1) {
                                    }
                                    reties = reties + 1;
                                }
                            }

                            if (reties >= 5) {
                                return false;
                            }
                            return true;
                        }
                    });
                    if (callback != null) {
                        callback.onFlushTask(task);
                    }
                } catch (JsonProcessingException e) {
                    log.error("Failed to serialize data.", e);
                } finally {
                    messageList.clear();
                }
            }
        }

        @Override public void close() {
            flush();

            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static class HttpLogger {

        static class HttpConsumerException extends Exception {

            HttpConsumerException(String error, String sendingData, int httpStatusCode, String
                    httpContent) {
                super(error);
                this.sendingData = sendingData;
                this.httpStatusCode = httpStatusCode;
                this.httpContent = httpContent;
            }

            String getSendingData() {
                return sendingData;
            }

            int getHttpStatusCode() {
                return httpStatusCode;
            }

            String getHttpContent() {
                return httpContent;
            }

            final String sendingData;
            final int httpStatusCode;
            final String httpContent;
        }

        HttpLogger(String serverUrl, Map<String, String> httpHeaders) {
            this.serverUrl = serverUrl;
            this.httpHeaders = httpHeaders;

            this.compressData = true;
        }

        HttpResponse consume(final String data) throws IOException, HttpConsumerException {
            HttpResponse response = new DefaultHttpClient().execute(getHttpRequest(data));

            int httpStatusCode = response.getStatusLine().getStatusCode();
            if (httpStatusCode < 200 || httpStatusCode >= 300) {
                String httpContent = EntityUtils.toString(response.getEntity(), "UTF-8");
                throw new HttpConsumerException(String.format("Unexpected response %d from Sensors "
                        + "Analytics: %s", httpStatusCode, httpContent), data, httpStatusCode, httpContent);
            }

            return response;
        }

        HttpUriRequest getHttpRequest(final String data) throws IOException {
            HttpPost httpPost = new HttpPost(this.serverUrl);

            httpPost.setEntity(getHttpEntry(data));
            httpPost.addHeader("User-Agent", "SensorsAnalytics Java SDK");

            if (this.httpHeaders != null) {
                for (Map.Entry<String, String> entry : this.httpHeaders.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            return httpPost;
        }

        UrlEncodedFormEntity getHttpEntry(final String data) throws IOException {
            byte[] bytes = data.getBytes(Charset.forName("UTF-8"));

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            if (compressData) {
                ByteArrayOutputStream os = new ByteArrayOutputStream(bytes.length);
                GZIPOutputStream gos = new GZIPOutputStream(os);
                gos.write(bytes);
                gos.close();
                byte[] compressed = os.toByteArray();
                os.close();

                nameValuePairs.add(new BasicNameValuePair("gzip", "1"));
                nameValuePairs.add(new BasicNameValuePair("data_list", new String(Base64Coder.encode
                        (compressed))));
            } else {
                nameValuePairs.add(new BasicNameValuePair("gzip", "0"));
                nameValuePairs.add(new BasicNameValuePair("data_list", new String(Base64Coder.encode
                        (bytes))));
            }

            return new UrlEncodedFormEntity(nameValuePairs);
        }

        final String serverUrl;
        final Map<String, String> httpHeaders;

        final Boolean compressData;
    }

    private static ObjectMapper getJsonObjectMapper() {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        // 容忍json中出现未知的列
        jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 兼容java中的驼峰的字段名命名
        jsonObjectMapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        jsonObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        return jsonObjectMapper;
    }

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(loggerTrack.class);

    private final static String SDK_VERSION = "1.0.0";
    private final static String APP_VERSION = "1.0.0";
    private final static Pattern KEY_PATTERN = Pattern.compile(
            "^((?!^distinct_id$|^time$|^properties$|^id$|^users$|^events$|^event$|^user_id$|^date$|^datetime$)[a-zA-Z_$][a-zA-Z\\d_$]{0,99})$",
            Pattern.CASE_INSENSITIVE);

    private final AsyncBatchLogger asyncBatchLogger;
    private final Map<String, Object> superProperties;

    public loggerTrack(final AsyncBatchLogger logger) {
        this.asyncBatchLogger = logger;
        this.superProperties = new ConcurrentHashMap<>();
        setSuperProperties();
    }

    public void track(String distinctId, String eventName, Map<String, Object> properties)
            throws InvalidArgumentException {
        addEvent(distinctId, "track", eventName, properties);
    }

    private void addEvent(String distinctId, String actionType, String eventName, Map<String, Object> properties)
            throws InvalidArgumentException {
        assertKey("Distinct Id", distinctId);
        assertProperties(actionType, properties);
        assertKeyWithRegex("Event Name", eventName);

        // Event time
        long time = System.currentTimeMillis();
        if (properties != null && properties.containsKey("$time")) {
            Date eventTime = (Date) properties.get("$time");
            properties.remove("$time");
            time = eventTime.getTime();
        }

        Map<String, Object> eventProperties = new HashMap<>();
        eventProperties.putAll(this.superProperties);
        eventProperties.putAll(properties);

        Map<String, String> libProperties = getLibProperties();

        Map<String, Object> event = new HashMap<>();

        event.put("type", actionType);
        event.put("time", time);
        event.put("distinct_id", distinctId);
        event.put("properties", eventProperties);
        event.put("lib", libProperties);
        event.put("event", eventName);


        this.asyncBatchLogger.send(event);
    }

    private void setSuperProperties() {
        this.superProperties.clear();
        this.superProperties.put("$app_version", APP_VERSION);
    }

    private Map<String, String> getLibProperties() {
        Map<String, String> libProperties = new HashMap<>();
        libProperties.put("$lib", "Java");
        libProperties.put("$lib_version", SDK_VERSION);
        libProperties.put("$lib_method", "code");

        StackTraceElement[] trace = (new Exception()).getStackTrace();

        if (trace.length > 3) {
            StackTraceElement traceElement = trace[3];
            libProperties.put("$lib_detail", String.format("%s##%s##%s##%s", traceElement.getClassName(),
                    traceElement.getMethodName(), traceElement.getFileName(), traceElement.getLineNumber()));
        }

        return libProperties;
    }

    private void assertKey(String type, String key) throws InvalidArgumentException {
        if (key == null || key.length() < 1) {
            throw new InvalidArgumentException("The " + type + " is empty.");
        }
        if (key.length() > 255) {
            throw new InvalidArgumentException("The " + type + " is too long, max length is 255.");
        }
    }

    private void assertKeyWithRegex(String type, String key) throws InvalidArgumentException {
        assertKey(type, key);
        if (!(KEY_PATTERN.matcher(key).matches())) {
            throw new InvalidArgumentException("The " + type + "'" + key + "' is invalid.");
        }
    }

    private void assertProperties(String eventType, Map<String, Object> properties)
            throws InvalidArgumentException {
        if (null == properties) {
            return;
        }
        for (Map.Entry<String, Object> property : properties.entrySet()) {
            assertKeyWithRegex("property", property.getKey());

            if (!(property.getValue() instanceof Number) && !(property.getValue() instanceof Date) && !
                    (property.getValue() instanceof String) && !(property.getValue() instanceof Boolean) &&
                    !(property.getValue() instanceof List<?>)) {
                throw new InvalidArgumentException("The property value should be asyncBatchLogger basic type: "
                        + "Number, String, Date, Boolean, List<String>.");
            }

            if (property.getKey().equals("$time") && !(property.getValue() instanceof Date)) {
                throw new InvalidArgumentException(
                        "The property value of key '$time' should be asyncBatchLogger java.util.Date type.");
            }

            // List 类型的属性值，List 元素必须为 String 类型
            if (property.getValue() instanceof List<?>) {
                for (final ListIterator<Object> it = ((List<Object>)property.getValue()).listIterator
                        (); it.hasNext();) {
                    Object element = it.next();
                    if (!(element instanceof String)) {
                        throw new InvalidArgumentException("The property value should be asyncBatchLogger basic type: "
                                + "Number, String, Date, Boolean, List<String>.");
                    }
                    if (((String) element).length() > 8191) {
                        it.set(((String) element).substring(0, 8191));
                        log.warn(String.format("Element in property '%s' with LIST type is cut off while it's "
                                + "too long", (String) element));
                    }
                }
            }

            // String 类型的属性值，长度不能超过 8192
            if (property.getValue() instanceof String) {
                String value = (String) property.getValue();
                if (value.length() > 8191) {
                    property.setValue(value.substring(0, 8191));
                    log.warn(String.format("Property '%s' with STRING type is cut off while it's too long"
                            + "."), value);
                }
            }
        }
    }
}
