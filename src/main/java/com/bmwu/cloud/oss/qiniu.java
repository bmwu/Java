package com.bmwu.cloud.oss;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by michael on 3/23/17.
 */
public class qiniu {

    private static String accessKey = "";
    private static String secretKey = "";
    private static String bucket = "";

    public static void main(String[] args) {
        putObject();
        getObject();
    }

    public static void putObject() {
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        String localFilePath = "";
        String key = "qiniu";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException e) {
            Response r = e.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void getObject() {
        String fileName = "qiniu";
        String domainOfBucket = "";
        try {
            String encodedFileName = URLEncoder.encode(fileName, "utf-8");
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            System.out.println(publicUrl);

            Auth auth = Auth.create(accessKey, secretKey);
            long expireInSeconds = 3600;
            String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);

            Configuration cfg = new Configuration(Zone.zone2());
            BucketManager bucketManager = new BucketManager(auth, cfg);

            try {
                FetchRet fetchRet = bucketManager.fetch(finalUrl, bucket);
                System.out.println(fetchRet.hash);
                System.out.println(fetchRet.key);
                System.out.println(fetchRet.mimeType);
                System.out.println(fetchRet.fsize);

                FileInfo fileInfo = bucketManager.stat(bucket, fileName);
                System.out.println(fileInfo.hash);
                System.out.println(fileInfo.fsize);
                System.out.println(fileInfo.mimeType);
                System.out.println(fileInfo.putTime);

            } catch (QiniuException e) {
                e.printStackTrace();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
