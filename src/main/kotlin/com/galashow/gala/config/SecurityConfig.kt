package com.galashow.gala.config

import com.galashow.gala.jwt.filter.JwtRequestFilter
import com.galashow.gala.oauth.Oauth2LoginFailureHandler
import com.galashow.gala.oauth.Oauth2LoginSuccessHandler
import com.galashow.gala.oauth.service.Oauth2UserService
import com.galashow.gala.security.CustomAccessDeniedHandler
import com.galashow.gala.security.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity,
                            oauth2UserService: Oauth2UserService,
                            successHandler: Oauth2LoginSuccessHandler,
                            failureHandler: Oauth2LoginFailureHandler,
                            jwtRequestFilter: JwtRequestFilter,
                            customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
                            customAccessDeniedHandler: CustomAccessDeniedHandler): SecurityFilterChain {
        // 폼 기반 로그인 비활성화
        httpSecurity.formLogin { it -> it.loginPage("/login").permitAll() }
        // HTTP 기본 인증 비활성화
        httpSecurity.httpBasic { it -> it.disable() }

        // CSRF 공격 방어 기능 비활성화
        httpSecurity.csrf { it -> it.disable() }

        // 세션 이용 안함
        httpSecurity.sessionManagement { it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }


        httpSecurity.authorizeHttpRequests{authorize -> authorize
            .requestMatchers("/login/oauth2/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/user/get").hasAnyAuthority("ADM","USR")
            .requestMatchers("/api-document").permitAll()
            .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
            .requestMatchers("/login").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .requestMatchers(HttpMethod.GET,"/board").permitAll()
            .requestMatchers(HttpMethod.GET,"/board/*").permitAll()
            .anyRequest().authenticated()

        }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .oauth2Login { oauth2 -> oauth2
                .userInfoEndpoint { userInfo -> userInfo.userService(oauth2UserService) }
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            }

            .exceptionHandling { except ->
                except.authenticationEntryPoint(customAuthenticationEntryPoint)
                except.accessDeniedHandler(customAccessDeniedHandler)
            }

        
        return httpSecurity.build()
    }

}