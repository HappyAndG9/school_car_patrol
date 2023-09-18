package happy.schoolcarpatrol.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author 木月丶
 * @Description 自定义认证失败异常
 */
public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String msg) {
        super(msg);
    }
}
