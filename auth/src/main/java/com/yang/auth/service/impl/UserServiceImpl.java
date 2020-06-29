package com.yang.auth.service.impl;

import com.yang.auth.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service层需要实现UserDetailService接口
 * 该接口是根据用户名获取该用户的所有信息，包括用户信息和权限点
 * */
@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("用户名称为：" + s);
        return userDao.findByUsername(s);
    }
}
