package com.yang.consumer.service;

import com.yang.consumer.entity.vo.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public interface PaymentService {

    public Payment getPayment();
}
