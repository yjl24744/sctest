package com.yang.auth.dao;

import com.yang.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserDao继承了JpaRepository
 * JpaRepository默认实现了大多数单表查询的操作
 * UserDao中自定义一个根据username获取User的方法
 * 由于JPA已经自动实现了根据username字段去查找用户的方法
 * 因此不需要额外的工作
 * */
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
