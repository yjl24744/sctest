package com.yang.consumer.service.impl;

import com.yang.consumer.entity.vo.Payment;
import com.yang.consumer.rest.PaymentRest;
import com.yang.consumer.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRest paymentRest;

    @Override
    public Payment getPayment() {
        return paymentRest.getPayment();
    }
}
