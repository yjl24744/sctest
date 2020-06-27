package com.yang.eurekaclient.rest;

import com.yang.api.entity.vo.Payment;
import com.yang.eurekaclient.service.PaymentFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class HelloController {

    public static final String SERVICE_URL = "http://payment/get";

    @Value("${server.port}")
    String port;

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private PaymentFeignClient paymentFeignClient;

    @GetMapping("/hello")
    public String sayHello(@RequestParam String name) {
        log.info("输入的名称1111为：" + name + "，端口为：" + port);
        Payment payment = restTemplate.getForEntity(SERVICE_URL, Payment.class).getBody();
        return payment.toString();
    }

    @GetMapping("/feign")
    public String feign() {
        return paymentFeignClient.getPayment().toString();
    }
}
