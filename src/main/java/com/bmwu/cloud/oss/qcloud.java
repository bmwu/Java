package com.bmwu.cloud.oss;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.common_utils.CommonPathUtils;
import com.qcloud.cos.exception.AbstractCosException;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;

/**
 * Created by michael on 3/9/17.
 */
public class qcloud {

    private static long appId = 1000;
    private static String secretId = "";
    private static String secretKey = "";
    // 设置要操作的bucket
    private static String bucketName = "";

    private static String region = "tj";
    private static boolean useCDN = false;

    // cos 要求 以/开头,, 不以/结尾
    private static String cosPath = "/xxx/a.txt";

    public static void main(String[] args) {
        test();
    }

    /**
     * post 时 cos会组装请求链接
     * @return
     * @throws AbstractCosException
     */
    protected String buildUrl() throws AbstractCosException {

        ClientConfig config = new ClientConfig();
        config.setRegion(region);
        String endPoint = new StringBuilder().append(config.getUploadCosEndPointPrefix())
                .append(config.getUploadCosEndPointDomain())
                .append(config.getUploadCosEndPointSuffix()).toString();
        cosPath = CommonPathUtils.encodeRemotePath(cosPath);
        return String.format("%s/%d/%s%s", endPoint, appId, bucketName, cosPath);
    }

    /**
     * /cos_api/4.2/cos_api-4.2-sources.jar!/com/qcloud/cos/op/FileOp.java
     * get 时 cos
     * @return
     */
    private String buildGetFileUrl() {

        ClientConfig config = new ClientConfig();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(config.getDownCosEndPointPrefix()).append(bucketName)
                .append("-").append(appId).append(".");
        if (useCDN) {
            strBuilder.append("file.myqcloud.com");
        } else {
            strBuilder.append(config.getDownCosEndPointDomain());
        }
        strBuilder.append(cosPath).toString();
        String url = strBuilder.toString();
        return url;
    }

    public static void test() {


        // 初始化秘钥信息
        Credentials cred = new Credentials(appId, secretId, secretKey);

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(region);

        COSClient cosClient = new COSClient(clientConfig, cred);

        UploadFileRequest request = new UploadFileRequest(bucketName, "/xxx/a.txt", "/Users/michael/Documents/qcloudtest.txt");
        cosClient.uploadFile(request);

        GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(bucketName, "/xxx/a.txt", "/Users/michael/Documents/qcloudtestgett.txt");
        getFileLocalRequest.setUseCDN(useCDN);

        String getFileResult = cosClient.getFileLocal(getFileLocalRequest);
        cosClient.shutdown();
    }
}
