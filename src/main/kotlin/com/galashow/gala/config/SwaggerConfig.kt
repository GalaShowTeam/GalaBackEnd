package com.galashow.gala.config


import com.galashow.gala.common.dto.ResponseDTO
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI() : OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Gala Back End API")
                .version("v1.0.0")
                .description("Gala 프로젝트의 백엔드 API"))
            .components(Components()
                .addSecuritySchemes(
                    "bearer-jwt",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT 인증을 위한 Bearer Token")
                )
                .addExamples("GalaUserDtoExample",
                    Example().
                        value(
                            mapOf(
                                "result" to "SUCCESS",
                                "msg" to "성공했습니다.",
                                "contents" to mapOf(
                                    "userNo" to 0,
                                    "userEmail" to "galaUser@email.com",
                                    "userNickname" to "google",
                                    "profileImg" to "profile.jpg",
                                    "points" to 700,
                                    "role" to "USR"
                                )
                        )
                    )
                )
            )
            .addSecurityItem(
                SecurityRequirement()
                    .addList("bearer-jwt")
            )

    }
}