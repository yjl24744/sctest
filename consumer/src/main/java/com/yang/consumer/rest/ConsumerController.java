package com.yang.consumer.rest;

import com.yang.consumer.entity.vo.Payment;
import com.yang.consumer.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ConsumerController {

    public static final String REMOTE_URI = "http://payment/get";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentService paymentService;

    // @GetMapping("/consumer/${name}")
    // public Payment consumer(@PathVariable String name) {
    //     Payment payment = restTemplate.getForEntity(REMOTE_URI + name, Payment.class).getBody();
    //     log.info("名字为" + name);
    //     return payment;
    // }

    @RequestMapping("/cosumer/get")
    public Payment getPayment() {
        Payment payment = paymentService.getPayment();
        log.info(payment.toString());
        return payment;
    }
}
