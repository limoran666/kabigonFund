package com.kabigon.crowd.util;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.kabigon.crowd.constant.CrowdConstant;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
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
    public static ResultEntity<String> sendCodeByShortMessage(


            //接受验证码的手机号
            String phoneNum,
            //用来调用第三方短信的API的accID和Secret
            String accessKeyId,String accessKeySecret,
            //签名编号
            String SignName,
            //模板编号
            String TemplateCode){

        {
            java.util.List<String> args = java.util.Arrays.asList();
            com.aliyun.dysmsapi20170525.Client client = null;
            try {
                //client = CrowdUtil.createClient("LTAI5t6SwYeFrQhxxNv7yGsr", "9lBJOVRdLbTWvr4jp7ME01sZDmXN6k");
                client = CrowdUtil.createClient(accessKeyId, accessKeySecret);
                StringBuffer buffer=new StringBuffer();
                for (int i=0;i<4;i++){
                    int random=(int)(Math.random()*10);
                    buffer.append(random);
                }
                String code1= buffer.toString();
                String codestr="{\"code\":\""+code1+"\"}";
                SendSmsRequest sendSmsRequest = new SendSmsRequest()
                        //.setSignName("阿里云短信测试")
                        .setSignName(SignName)
//                        .setTemplateCode("SMS_154950909")
                      .setTemplateCode(TemplateCode)
                        .setPhoneNumbers(phoneNum)
                        //.setTemplateParam("{\"code\":\"2667\"}");
                        .setTemplateParam(codestr);
                SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
                   /*{
                      "RequestId": "5796BEF4-7F7A-5E99-950C-A635D416FF78",
                      "Message": "OK",
                      "BizId": "871004847642375910^0",
                      "Code": "OK"
                    }*/
                String message = sendSmsResponse.getBody().getMessage();
                if ("OK".equals(message)){
                    return ResultEntity.successWithData(code1);
                }
                return ResultEntity.failed(message);



            } catch (Exception exception) {
                return ResultEntity.failed(exception.getMessage());
            }



        }


    }





    /*判断当前请求是否为Ajax请求
    * true是ajax请求
    * false不是ajax请求
    * */
    public static boolean judgeRequestType(HttpServletRequest request) {
        //1.获取请求消息头
        String requestHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-with");

        //判断
        if ((requestHeader != null && requestHeader.contains("application/json")) || (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"))) {
            return true;
        }

        return false;

    }

    public static  String MD5(String source){
        // 1.判断source是否有效
        if (source==null||source.length()==0){
            //如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);

        }


        try {

            //获取MessageDigest对象
            String algorithm="md5";

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            /*获取明文字符串对应的字节数组*/
            byte[] input = source.getBytes();
            /*执行加密*/
            byte[] output= messageDigest.digest(input);
            //创建BigInteger对象
            int signum=1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 按照16进制将bigInteger的值转为字符串
            int radix=16;
            String encoded = bigInteger.toString(16);
            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    return null;
    }
}