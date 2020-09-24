package com.offcn.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@ToString
public class OssTemplate {
    private String endpoint;
    private String bucketDomain;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public String upload(InputStream inputStream, String fileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = sdf.format(new Date());

        fileName = UUID.randomUUID().toString().replace("-", "") + fileName;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, "pic/" + folderName+"/" + fileName, inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
        String url = "https://" + bucketDomain + "/pic/" + folderName + "/" + fileName;
        return url;
    }

}
