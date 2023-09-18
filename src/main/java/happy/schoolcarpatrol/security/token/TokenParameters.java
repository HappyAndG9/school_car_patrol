package happy.schoolcarpatrol.security.token;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author 木月丶
 * @Description
 */
@Data
@Component
public class TokenParameters {
    //token密钥
    private String jwtTokenSecret = "nC2eY4";
    //token过期时间
    private long tokenExpiredMs = 7*24*60*60*1000;
}
