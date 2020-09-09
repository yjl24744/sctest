package com.yang.consumer.rest;

import com.yang.consumer.entity.vo.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(name = "payment", fallbackFactory = PaymentFallbackFactory.class)
public interface PaymentRest {

    @GetMapping("/get")
    public Payment getPayment();
}
