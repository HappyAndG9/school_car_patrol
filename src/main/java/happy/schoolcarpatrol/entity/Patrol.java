package happy.schoolcarpatrol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 木月丶
 * @since 2023-04-06
 */
@Getter
@Setter
@TableName("patrol")
@ApiModel(value = "Patrol对象", description = "")
public class Patrol implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "patrol_id", type = IdType.AUTO)
    private Long patrolId;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;


}
