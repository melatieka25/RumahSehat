package TA_C_SHA_90.RumahSehatWeb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/login-sso", "/validate-ticket").permitAll()
                .antMatchers("/pasien").hasRole("Admin")
                .antMatchers("/apoteker").hasRole("Admin")
                .antMatchers("/apoteker/create-apoteker").hasRole("Admin")
                .antMatchers("/dokter").hasRole("Admin")
                .antMatchers("/dokter/create-dokter").hasRole("Admin")
                .antMatchers("/resep/create-resep").hasRole("Dokter")
                .antMatchers("/resep").hasAnyRole("Admin", "Apoteker")
                .antMatchers("/resep/confirm").hasRole("Apoteker")
				.antMatchers("/obat").hasAnyRole("Admin", "Apoteker")
				.antMatchers("/obat/stok").hasRole("Apoteker")
                .antMatchers("/appointment").hasAnyRole("Admin", "Dokter")
                .antMatchers("/appointment/detail/{kode}").hasAnyRole("Admin", "Dokter")
				.antMatchers("/statistics").hasRole("Admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .sessionManagement().sessionFixation().newSession().maximumSessions(1);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
