package com.galashow.gala.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity,): SecurityFilterChain {
        // 폼 기반 로그인 비활성화
        httpSecurity.formLogin { it -> it.disable() }
        // HTTP 기본 인증 비활성화
        httpSecurity.httpBasic { it -> it.disable() }

        // CSRF 공격 방어 기능 비활성화
        httpSecurity.csrf { it -> it.disable() }

        // 세션 이용 안함
        httpSecurity.sessionManagement { it -> it.disable() }


        httpSecurity.authorizeHttpRequests{authorize -> authorize
            .requestMatchers("/login/oauth2/code/google").permitAll()
            .anyRequest().authenticated()
        }
            .oauth2Login {
                it -> it.defaultSuccessUrl("/api/login/success",false)
            }

        return httpSecurity.build()
    }
}