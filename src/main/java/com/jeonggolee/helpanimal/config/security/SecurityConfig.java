package com.jeonggolee.helpanimal.config.security;

import com.jeonggolee.helpanimal.common.jwt.JwtFilter;
import com.jeonggolee.helpanimal.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Environment env;
    private final JwtTokenProvider tokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (isLocalMode()) {
            setLocalMode(http);
        }
        setCommonConfig(http);
    }
    private boolean isLocalMode() {
        String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "";
        return profile.equals("local");
    }

    private void setLocalMode(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .sameOrigin();

        http
                .authorizeRequests()
                .antMatchers("/h2-console/*").permitAll();
    }

    private void setCommonConfig(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .headers()
                .frameOptions()
                .sameOrigin();

        http
                .authorizeRequests()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/api/v1/user/login/").anonymous()
                .antMatchers("/api/v1/user/{email}/").authenticated()
                .antMatchers("/api/v1/crew/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("accessToken","Cache-Control","Content-Type"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;

    }
}

