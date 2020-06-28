package com.yang.auth.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Role类实现了GrantedAuthority接口，并重写了其getAuthority方法
 * 权限点可以为任何字符串，不一定是角色名的字符串，关键是getAuthority方法如何实现
 * 本例的权限点是从数据库中读取Role表的name字段
 * */
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
