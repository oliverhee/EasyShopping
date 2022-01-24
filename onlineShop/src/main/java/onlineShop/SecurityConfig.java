package onlineShop;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.context.annotation.Bean;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //网络缺陷，默认enable
                .formLogin()
                .loginPage("/login");//可不写，如果不行会转到框架给的的login界面
        http
                .authorizeRequests()
                .antMatchers("/cart/**").hasAuthority("ROLE_USER")
                .antMatchers("/get*/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/admin*/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().withUser("admin@gmail.com").password("admin1234").authorities("ROLE_ADMIN");

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT emailId, password, enabled FROM users WHERE emailId=?")
                .authoritiesByUsernameQuery("SELECT emailId, authorities FROM authorities WHERE emailId=?");
        // 只需要提供数据，框架本身会去比较给的pwd是否与数据库中的一样

    }

    @SuppressWarnings("deprecation") // 加密
    //@SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
