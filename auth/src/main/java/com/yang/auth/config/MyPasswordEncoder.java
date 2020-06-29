package com.yang.auth.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 从数据库中验证需要实现PasswordEncoder接口
 * 否则报“java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"”
 * */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
