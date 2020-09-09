package com.yang.consumer.rest;

import com.yang.consumer.entity.vo.Payment;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackFactory implements FallbackFactory<PaymentRest> {
    public static final Logger logger = LoggerFactory.getLogger(PaymentFallbackFactory.class);

    @Override
    public PaymentRest create(Throwable throwable) {
        System.out.println(throwable.getMessage());
        logger.info("远程服务出错，熔断成功：", throwable.getMessage());
        return new PaymentRest() {
            @Override
            public Payment getPayment() {
                Payment payment = new Payment("2","测试熔断","222");
                return payment;
            }
        };
    }
}
