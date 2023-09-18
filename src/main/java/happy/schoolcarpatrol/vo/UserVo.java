package happy.schoolcarpatrol.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 木月丶
 * @Description
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话号码")
    private String phoneNumber;

    @ApiModelProperty("车牌号")
    private String licenseNumber;


}