package happy.schoolcarpatrol.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author 木月丶
 * @Description jwt生成token
 */
@Component
public class JwtTokenProvider {

    @Resource
    private TokenParameters tokenParameters;

    public String creatToken(Authentication authentication) {
        //用户名
        String username = ((User) authentication.getPrincipal()).getUsername();
        //过期时间
        Date expireTime = new Date(System.currentTimeMillis() + tokenParameters.getTokenExpiredMs());
        //token
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expireTime)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, tokenParameters.getJwtTokenSecret())
                .compact();
        return token;
    }

    /**
     * @author 木月丶
     * @description 验证token是否合法
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(tokenParameters.getJwtTokenSecret())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //一共可能会有五个异常，因为懒所以全catch了
            //ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
            e.printStackTrace();
            return false;
        }
    }
}