package happy.schoolcarpatrol.config;

import happy.schoolcarpatrol.security.filter.JwtAuthenticationFilter;
import happy.schoolcarpatrol.security.handler.AuthenticationFailHandler;
import happy.schoolcarpatrol.security.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;


/**
 * @Author 木月丶
 * @Description
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Resource
        private MyUserDetailsService myUserDetailsService;

        @Resource
        private AuthenticationSuccessHandler successHandler;

        @Resource
        private AuthenticationFailHandler failHandler;

        @Resource
        private AuthenticationEntryPoint entryPoint;

        //放行的资源
        public static final String REQUEST_RESOURCES_PERMIT_ALL =
                "/login," +
                "/swagger-ui.html," +
                "/webjars/**," +
                "/swagger-resources/**," +
                "/v2/**," +
                "/logout";

        @Override
        public void configure(HttpSecurity http) throws Exception {

            //配置session策略
            http.addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            //配置csrf
            http.csrf().disable();

            //配置跨域问题
            http.cors(Customizer.withDefaults());

            //配置请求认证
            http.authorizeRequests().antMatchers(REQUEST_RESOURCES_PERMIT_ALL.split(",")).permitAll()   //放行的资源路径
                    .anyRequest().authenticated();
            //配置登录策略
            http.formLogin().loginProcessingUrl("/loginProcess")    //配置登录请求url
                    .successHandler(successHandler)     //配置登录成功处理器
                    .failureHandler(failHandler);       //配置登录失败处理器

            //配置登出策略
            http.logout().clearAuthentication(true)     //清除认证用户的信息
                    .deleteCookies("JSESSIONID")     //删除cookie
                    .invalidateHttpSession(true)      //移除session会话
                    .logoutUrl("/logout");                      //配置登出请求url

            //配置未登录请求资源策略
            http.exceptionHandling().authenticationEntryPoint(entryPoint);  //配置未登录请求资源的处理器
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(myUserDetailsService);
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public JwtAuthenticationFilter getJwtAuthenticationFilter(){
                return new JwtAuthenticationFilter();
        }

        @Bean
        public SecurityContext securityContext(){
            return new SecurityContextImpl();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            configuration.setAllowCredentials(true);
            configuration.addAllowedOrigin("*");
            configuration.addAllowedHeader("*");
            configuration.addAllowedMethod("*");
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }


    }

