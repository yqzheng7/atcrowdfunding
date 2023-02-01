package com.atguigu.crowd.test;

import com.atguigu.crowd.util.CrowdUtil;
import org.junit.Test;

public class StringTest {
    @Test
    public void testMd5() {
        String pswStr = "123123";
        String pswMd5 = CrowdUtil.md5(pswStr);
        System.out.println("pswMd5 = " + pswMd5);
    }
}
