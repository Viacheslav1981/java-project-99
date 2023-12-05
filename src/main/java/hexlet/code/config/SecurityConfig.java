package hexlet.code.config;

import hexlet.code.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userService;

    /*
    @Value("${base-url}")
    @Autowired
    private String baseUrl;

     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {
        var mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(mvcMatcherBuilder.pattern("/index.html")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/assets/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()

                        .requestMatchers(mvcMatcherBuilder.pattern("/welcome")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(POST, "/api/login")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(POST, "/api/users")).permitAll()

                        .requestMatchers(mvcMatcherBuilder.pattern(GET, "/api/users")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(GET, "/api/users/{id}")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(PUT, "/api/users/{id}")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(DELETE, "/api/users/{id}")).permitAll()


                       // .requestMatchers(mvcMatcherBuilder.pattern(GET, "/api/task_statuses")).permitAll()


                        //  .requestMatchers(mvcMatcherBuilder.pattern(baseUrl + "/login")).permitAll()
                        //  .requestMatchers(mvcMatcherBuilder.pattern("/api/login")).permitAll()
                        //  .requestMatchers(mvcMatcherBuilder.pattern(POST, "/api/login")).permitAll()
                        // .requestMatchers(mvcMatcherBuilder.pattern("/h2console/")).permitAll()
                        //  .requestMatchers(mvcMatcherBuilder.pattern(POST,"/api/users")).permitAll()
                        //   .requestMatchers(mvcMatcherBuilder.pattern(POST, baseUrl + "/users")).permitAll()
                        //   .requestMatchers(mvcMatcherBuilder.pattern(POST, baseUrl + "/users")).permitAll()
                        //   .requestMatchers(mvcMatcherBuilder.pattern(GET, baseUrl + "/task_statuses")).permitAll()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((rs) -> rs.jwt((jwt) -> jwt.decoder(jwtDecoder)))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public AuthenticationProvider daoAuthProvider(AuthenticationManagerBuilder auth) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


}