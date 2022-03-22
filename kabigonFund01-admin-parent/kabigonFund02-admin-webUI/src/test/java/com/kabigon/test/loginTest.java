package com.kabigon.test;

import com.kabigon.crowd.util.CrowdUtil;
import org.junit.Test;

public class loginTest {
    @Test
    public void test1(){
        String s = CrowdUtil.MD5("123");
        System.out.println(s);
    }
}
