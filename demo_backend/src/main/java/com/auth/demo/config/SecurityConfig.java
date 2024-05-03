package com.auth.demo.config;


import com.auth.demo.service.CustomOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
public class SecurityConfig  {

    @Autowired
    private CustomOauth2Service customOauth2Service;

    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        System.out.println("here");
        return customOauth2Service;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // set the name of the attribute the CsrfToken will be populated on
        http
                .csrf(AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/oauth2/google").permitAll()
//                        .requestMatchers("/**").authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(oauth2UserService())
//                        )
////                        .redirectionEndpoint(redirection -> redirection
////                                .baseUri("/login/oauth2/callback/*")
////                        )
//                );

        return http.build();
    }


}
