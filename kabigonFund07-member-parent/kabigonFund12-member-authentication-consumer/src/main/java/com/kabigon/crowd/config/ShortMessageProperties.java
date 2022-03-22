package com.kabigon.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component//加到ioc容器里面
@ConfigurationProperties(prefix = "short.message")
public class ShortMessageProperties {
    private String TemplateCode;
    private String SignName;
    private String accessKeyId;
    private String accessKeySecret;

    public ShortMessageProperties() {
    }

    public ShortMessageProperties(String templateCode, String signName, String accessKeyId, String accessKeySecret) {
        TemplateCode = templateCode;
        SignName = signName;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public String getTemplateCode() {
        return TemplateCode;
    }

    public void setTemplateCode(String templateCode) {
        TemplateCode = templateCode;
    }

    public String getSignName() {
        return SignName;
    }

    public void setSignName(String signName) {
        SignName = signName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
