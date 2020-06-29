package com.yang.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.annotation.Resource;

/**
 * Spring Security从2.0版本开始，提供了方法级别的安全支持
 * 并提供了JSR-250的支持
 * 写一个配置类SecurityConfig继承WebSecurityConfigurerAdapter
 * 并加上相关注解，就可以开启方法级别的保护
 *
 * @EnableGlobalMethodSecurity注解开启了方法级别的保护
 * 括号后面的参数可选，可选的参数如下：
 *  1) prePostEnabled：Spring Security的Pre和Post注解是否可用，即@PreAuthorize和@PostAuthorize是否可用
 *  2) securedEnabled：Spring Security的@Secured注解是否可用
 *  3) jsr250Enabled：Spring Security对JSR-250的注解是否可用
 *  一般来说，只会用到prePostEnabled
 *  因为@PreAuthorize和@PostAuthorize注解更适合方法级别的安全控制，并且支持Spring EL表达式
 *  其中，@PreAuthorize注解会在进入方法前进行权限验证
 * @postAuthorize注解在方法执行后在进行权限验证，该注解的应用场景很少
 *
 * 如何在方法上写权限注解？
 * 例如，@PreAuthorize("hasRole('ADMIN')"),
 * 也可以写为@PreAuthorize("hasAuthority('ROLE_ADMIN')")
 * 也可以写为@PreAuthorize("hasAnyRole('ADMIN','USER')")
 * 也可以写为@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
 * */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailsService userDetailsService;

    /*
    * 对Spring Security进行基本配置
    * 通过AuthenticationManagerBuilder在内存中创建了一个认证用户的信息
    * 该认证用户名为yang，密码为123456
    * 密码需要使用PasswordEncoder去加密
    * */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*
        * 此处代码虽然简单，但做了很多安全防护的工作，包括以下内容
        * 1，应用的每一个请求都需要认证
        * 2，自动生成了一个登录表单
        * 3，可以用username和password来进行认证
        * 4，用户可以注销
        * 5，阻止了CSRF攻击
        * 6，Session Fixation保护
        * 7，安全Header集成了以下内容
        *   1）HTTP Strict Transport Security for secure requests
        *   2）X-Content-Type-Options integration
        *   3)Cache Control
        *   4)X-XSS-Protection integration
        * 8，集成了以下的Servlet API的方法
        *   1）HttpServletRequest#getRemoteUser();
        *   2）HttpServletRequest.html#getUserPrincipal()
        *   3）HttpServletRequest.html#isUserInRole(java.lang.String)
        *   4）HttpServletRequest.html#login(java.lang.String,java.lang.String)
        *   5）HttpServletRequest.html#logout()
        * */
        /*
         * 演示“/user/index”界面只有“USER”角色才能访问，新建一个admin用户
         * 该用户只有“ADMIN”的角色，没有“USER”角色
         * 所以没有权限访问“/user/index”界面
         * */
        /*auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("yang")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("USER");
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("ADMIN", "USER");*/



        /**
         * 修改配置，让Security从数据库中读取，而不是之前从内存中读取
         * */
        auth.userDetailsService(userDetailsService);
    }

    /*
    * 配置HttpSecurity
    * 作用是使所有的用户都需要进行身份验证
    * 支持基于表单的身份验证
    * 哪些资源需要验证，哪些资源不需要验证
    * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 以"/css/**"开头的资源和"/index"资源不需要验证，外界请求可以直接访问这些资源
        // 以"/user/**"和"/blogs/**"开头的资源需要验证，并且需要用户的角色是“USER”
        // 表单登录的地址是“/login”，登录失败的地址是“/login-error”
        // 异常处理会重定向到“/401”界面
        // 注销登录成功，重定向到首页
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/blogs/**").hasRole("USER")
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error")
                .and()
                .exceptionHandling().accessDeniedPage("/401");
        http.logout().logoutSuccessUrl("/");
    }
}
