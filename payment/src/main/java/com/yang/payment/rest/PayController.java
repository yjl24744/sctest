package com.yang.payment.rest;

import com.yang.payment.entity.vo.Payment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @GetMapping("/get")
    public Payment getPayment() {
        Payment payment = new Payment("1","安德森","18.1");
        return payment;
    }
}
