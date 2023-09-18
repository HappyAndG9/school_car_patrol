package happy.schoolcarpatrol.security.handler;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import happy.schoolcarpatrol.entity.Patrol;
import happy.schoolcarpatrol.mapper.PatrolMapper;
import happy.schoolcarpatrol.result.ResponseUtil;
import happy.schoolcarpatrol.result.Result;
import happy.schoolcarpatrol.security.token.JwtTokenProvider;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author 木月丶
 * @Description 登录成功处理器
 */
@Service
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    JwtTokenProvider provider;

    @Resource
    PatrolMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String token = provider.creatToken(authentication);
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Patrol> wrapper = new QueryWrapper<>();
        wrapper.eq("username", admin.getUsername());
        Patrol patrol = mapper.selectOne(wrapper);
        map.put("username",patrol.getUsername());
        map.put("token",token);
        ResponseUtil.out(response, Result.successWithData(map));
        //返回token给前端
        returnJson(response, authentication);
    }

    private void returnJson(HttpServletResponse response,Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter()
                .println("{\"tokenType\":\"Happy\",\"token\": \"" + provider.creatToken(authentication) + "\"}");
    }
}
