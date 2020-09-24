package com.offcn.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OSSTest {
    public static void main(String[] args) throws FileNotFoundException {
        String endpoint = "oss-cn-beijing.aliyuncs.com";

        String accessKeyId = "LTAI4G8snwZR3Z7ydMPmHfN7";
        String accessKeySecret = "8tJM3iJbeWAomR5oev225PvzEaAAjb";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InputStream is = new FileInputStream(new File("C:\\Users\\Sai\\Pictures\\Saved Pictures\\3.jpg"));
        ossClient.putObject("offcn826", "pic/008.jpg", is);
        ossClient.shutdown();
        System.out.println("测试完毕");
    }
}
