package com.kabigon.crowd.test;


import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import org.junit.Test;


public class CrowdTest {

    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
    @Test
    public void test() throws Exception {
        java.util.List<String> args = java.util.Arrays.asList();
        StringBuffer buffer=new StringBuffer();
        for (int i=0;i<4;i++){
            int random=(int)(Math.random()*10);
            buffer.append(random);
        }
        String code1= buffer.toString();
        String codestr="{\"code\":\""+code1+"\"}";
        com.aliyun.dysmsapi20170525.Client client = CrowdTest.createClient("LTAI5t6SwYeFrQhxxNv7yGsr", "9lBJOVRdLbTWvr4jp7ME01sZDmXN6k");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers("19825277809")
                .setTemplateParam(codestr);

                   /*{
  "RequestId": "5796BEF4-7F7A-5E99-950C-A635D416FF78",
  "Message": "OK",
  "BizId": "871004847642375910^0",
  "Code": "OK"
}*/
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        SendSmsResponseBody body = sendSmsResponse.body;
        String message = body.getMessage();
        String code = body.getCode();
        String bizId = body.getBizId();
        String requestId = body.getRequestId();
        System.out.println(""+message+code+bizId+requestId);

    }

    @Test
    public void test1(){
        StringBuffer buffer=new StringBuffer();
        for (int i=0;i<4;i++){
            int random=(int)(Math.random()*10);
            buffer.append(random);
        }
        String code1= buffer.toString();
        String codestr1="{\"code\":\""+code1+"\"}";
        System.out.println(codestr1);
    }
}
