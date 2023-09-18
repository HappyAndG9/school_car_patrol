package happy.schoolcarpatrol.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import happy.schoolcarpatrol.entity.User;
import happy.schoolcarpatrol.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 木月丶
 * @since 2023-03-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT name,phone_number,license_number from user where license_number = #{licenseNumber}")
    UserVo queryBasicInfo(@Param("licenseNumber") String licenseNumber);
}
