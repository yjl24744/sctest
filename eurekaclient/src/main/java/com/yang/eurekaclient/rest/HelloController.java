package com.yang.eurekaclient.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @Value("${server.port}")
    String port;

    @GetMapping("hello")
    public String sayHello(@RequestParam String name) {
        log.info("输入的名称1111为：" + name);
        return "hello spring cloud, my " + name + " s port is " + port;
    }
}
