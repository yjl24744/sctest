package com.yang.auth.filter;

import com.yang.auth.service.impl.UserServiceImpl;
import com.yang.auth.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当用户第一次登录之后，我们将JWT令牌返回给了客户端，客户端应该将令牌保存起来
 * 在进行接口请求的时候，将令牌带上，放到Http的header里面，
 * header的名称要和jwt.header配置的名称一样，这样服务端才能解析到
 *
 * 拦截接口请求，从请求request中获取token，从token中解析得到用户名
 *
 * 然后通过UserDetailService获取系统用户信息（从数据库或其他存储介质）
 *
 * 根据用户信息和JWT令牌，验证系统用户与输入用户信息的一致性，并判断JWT令牌是否过期
 * 如果没有过期，至此表明了输入用户的确是该系统的用户
 *
 * 但是，你是系统用户不代表你可以访问所有的接口
 * 所以需要构造UsernamePasswordAuthenticationToken传递用户、权限信息，
 * 并将这些信息通过authentication告知Spring Security
 * Spring Security会以此判断你的接口访问权限
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取request中的jwt token
        String authHeader = httpServletRequest.getHeader(jwtTokenUtil.getHeader());
        log.info("authHeader: {}", authHeader);
        // 验证token是否存在
        if (!StringUtils.isEmpty(authHeader)) {
            // 根据token获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(authHeader);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 通过用户名获取用户信息
                UserDetails userDetails = this.userService.loadUserByUsername(username);
                // 验证token是否过期
                if (jwtTokenUtil.validateToken(authHeader, userDetails)) {
                    // 加载用户、角色、权限信息，Spring Security根据这些信息判断接口的访问权限
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
