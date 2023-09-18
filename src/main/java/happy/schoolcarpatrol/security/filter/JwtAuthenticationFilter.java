package happy.schoolcarpatrol.security.filter;


import happy.schoolcarpatrol.security.service.MyUserDetailsService;
import happy.schoolcarpatrol.security.token.JwtTokenProvider;
import happy.schoolcarpatrol.security.token.TokenParameters;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 木月丶
 * @Description
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtTokenProvider jwtTokenProvider;

    @Resource
    private TokenParameters tokenParameters;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {

        //1.从每个请求header获取token
        String token = getJwtFromRequest(request);
        //2.验证token的合法性
        if (token != null && jwtTokenProvider.validateToken(token)) {
            //3.解析得到username
            String username = getUsernameFromJwt(token, tokenParameters.getJwtTokenSecret());
            //4.根据用户名在数据库中查询用户
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            //5.获取查询到的用户的信息，将其放入上下文以备整个请求过程使用
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            //6.没有token的进行日志输出提示
//            logger.error(request.getParameter("username") + " :Token is null");
        }
        super.doFilter(request, response, filterChain);
    }

    /**
     * @author 木月丶
     * @description request中获取token
     * @return String
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        return token;
    }

    /**
     * @author 木月丶
     * @description 从token中获取用户名
     * @return String
     */
    private String getUsernameFromJwt(String token, String signKey) {
        return Jwts.parser().setSigningKey(signKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}

