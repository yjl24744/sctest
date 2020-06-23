package com.yang.eurekaclient.service;

import com.yang.eurekaclient.entity.vo.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment")
public interface PaymentFeignClient {

    @GetMapping("/get")
    public Payment getPayment();
}
