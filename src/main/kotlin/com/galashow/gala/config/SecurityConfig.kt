package com.galashow.gala.config

import com.galashow.gala.oauth.Oauth2LoginSuccessHandler
import com.galashow.gala.service.Oauth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity,
                            oauth2UserService: Oauth2UserService,
                            successHandler: Oauth2LoginSuccessHandler): SecurityFilterChain {
        // 폼 기반 로그인 비활성화
        httpSecurity.formLogin { it -> it.disable() }
        // HTTP 기본 인증 비활성화
        httpSecurity.httpBasic { it -> it.disable() }

        // CSRF 공격 방어 기능 비활성화
        httpSecurity.csrf { it -> it.disable() }

        // 세션 이용 안함
        httpSecurity.sessionManagement { it -> it.disable() }


        httpSecurity.authorizeHttpRequests{authorize -> authorize
            .requestMatchers("/login/oauth2/code/**").permitAll()
            .anyRequest().authenticated()
        }
            .oauth2Login { oauth2 -> oauth2
                .userInfoEndpoint { userInfo -> userInfo.userService(oauth2UserService) }
                //TODO : 소셜 로그인 성공 or 실패 시 핸들러를 작성해야 한다.
                .successHandler(successHandler)
            }

        return httpSecurity.build()
    }

}