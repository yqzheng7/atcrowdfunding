package com.atguigu.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 标记为配置类
@Configuration
// 开启Web环境下权限控制功能
@EnableWebSecurity
// 开启全局方法权限控制功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 内存版：临时测试使用
//        builder.inMemoryAuthentication().withUser("admin003").password("123123").roles("ADMIN");

        // 使用数据库版
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(getbCryptPasswordEncoder())
        ;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers("/admin/to/login/page.html", "/bootstrap/**", "/crowd/**", "/css/**",
                        "/fonts/**", "/img/**", "/jquery/**", "/layer/**", "/script/**", "/ztree/**") // 针对登录页及表态资源进行设置
                .permitAll()    // 放行
                .antMatchers("/role/get/page/info.json")
                .hasAuthority("role:get")
                .anyRequest()   // 其他任意请求
                .authenticated()    // 认证后访问
                .and()
                .formLogin()    // 开启表单登录的功能
                .loginPage("/admin/to/login/page.html") // 指定登录页面
                .loginProcessingUrl("/security/do/login.html")  // 指定处理登录请求的地址
                .usernameParameter("loginAcct") // 账号的请求参数名称
                .passwordParameter("userPswd")  // 密码的请求参数名称
                .defaultSuccessUrl("/admin/to/main/page.html")  // 指定登录成功后前往的地址
                .and()
                .logout()   // 开启退出登录功能
                .logoutUrl("/security/do/logout.html")  // 指定处理退出登录请求的地址
                .logoutSuccessUrl("/admin/to/login/page.html")  // 指定退出后前往的地址
                .and()
                .csrf() // 防跨站请求伪造功能
                .disable()  // 禁用，实际开发时不要禁用
        ;
    }
}
