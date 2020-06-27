package com.yang.eurekaclient.rest;

import com.yang.api.entity.vo.Payment;
import com.yang.eurekaclient.service.PaymentFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/fallback")
public class FallbackPaymentController implements PaymentFeignClient {

    @Override
    public Payment getPayment() {
        Payment payment = new Payment("2", "错误", "404");
        return payment;
    }
}
