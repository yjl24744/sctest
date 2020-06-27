package com.yang.eurekaclient.service;

import com.yang.api.entity.vo.Payment;
import com.yang.eurekaclient.rest.FallbackPaymentController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment", fallback = FallbackPaymentController.class)
public interface PaymentFeignClient {

    @GetMapping("/get")
    public Payment getPayment();
}
