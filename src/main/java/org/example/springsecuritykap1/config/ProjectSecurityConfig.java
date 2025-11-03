package org.example.springsecuritykap1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfConfig -> csrfConfig.disable());
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/myAccount").hasRole("ADMIN") //skal være ROLE_ADMIN i databasen.
                    .requestMatchers("/myBalance").hasAnyRole("ADMIN","SALES")
                    .requestMatchers("/contact","/register").permitAll();
        });
        //http.formLogin(flc -> flc.disable() ); //bruger browsers default login skærm, så stadig login skærm.
        http.formLogin(Customizer.withDefaults() );
        http.httpBasic(Customizer.withDefaults());
        var obj = http.build();
        return obj;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance(); //Man kan slippe for at kryptere sine passwords
        //var obj = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return obj;
        return new BCryptPasswordEncoder(10);
    }


}
