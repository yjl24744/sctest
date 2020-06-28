package com.yang.payment.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @GetMapping("/get")
    public Payment getPayment() {
        Payment payment = new Payment("1","安德森","18.1");
        int i = 1/0;
        return payment;
    }
}
