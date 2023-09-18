package happy.schoolcarpatrol.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import happy.schoolcarpatrol.entity.Patrol;
import happy.schoolcarpatrol.mapper.PatrolMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 木月丶
 * @Description
 */
@Service("MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    PatrolMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Patrol> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Patrol patrol = mapper.selectOne(wrapper);
        if (patrol == null) {
            throw new UsernameNotFoundException("user" + username + "is not found");
        }

        ArrayList<String> roleList = new ArrayList<>();

        List<SimpleGrantedAuthority> authorities = roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return new User(patrol.getUsername(), new BCryptPasswordEncoder().encode(patrol.getPassword()), authorities);
    }
}
