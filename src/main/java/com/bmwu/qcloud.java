package com.bmwu;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;

/**
 * Created by michael on 3/9/17.
 */
public class qcloud {

    public static void main(String[] args) {
        test();
    }

    public static void test() {

        long appId = 1000;
        String secretId = "";
        String secretKey = "";
        // 设置要操作的bucket
        String bucketName = "";

        String region = "tj";
        boolean useCDN = false;
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



    }
}
