package happy.schoolcarpatrol.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import happy.schoolcarpatrol.entity.User;
import happy.schoolcarpatrol.mapper.UserMapper;
import happy.schoolcarpatrol.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 木月丶
 * @since 2023-03-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
