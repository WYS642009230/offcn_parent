package com.offcn.user.sms;

import com.offcn.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SmsTemplate {
    @Value("${sms.host}")
    private String host;
    @Value("${sms.path}")
    private String path;
    @Value("${sms.method}")
    private String method;
    @Value("${sms.appCode}")
    private String appCode;
    @Value("${sms.tpl_id}")
    private String tplId;


    public String sendSms(String mobile,String code) {

        //请求头信息
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode);

        //请求参数信息
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("code", "code:" + code);
        params.put("tpl_id", tplId);

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, params, "");
            return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }
}
