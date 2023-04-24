package com.backend.vse;

import com.backend.vse.service.OssService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TjTablePartySpringbootApplicationTests {

    @Autowired
    OssService ossService;

    @Test
    void contextLoads() {
    }

    @Test
    void testOssString() {
        String res = ossService.uploadLongText("你好", "noticetest.txt");
        System.out.println(res);
    }
}
