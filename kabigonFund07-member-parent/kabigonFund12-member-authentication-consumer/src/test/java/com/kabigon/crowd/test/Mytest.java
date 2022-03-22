package com.kabigon.crowd.test;


import com.kabigon.crowd.config.ShortMessageProperties;
import com.kabigon.crowd.util.CrowdUtil;
import com.kabigon.crowd.util.ResultEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Mytest {

    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @Test
    public void test(){
        String phoneNum="19825277809";
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(phoneNum, shortMessageProperties.getAccessKeyId(), shortMessageProperties.getAccessKeySecret(), shortMessageProperties.getSignName(), shortMessageProperties.getTemplateCode());

        String data = sendMessageResultEntity.getData();
        System.out.println(data);
    }


}
