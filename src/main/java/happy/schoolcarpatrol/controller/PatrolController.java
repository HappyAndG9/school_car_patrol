package happy.schoolcarpatrol.controller;

import happy.schoolcarpatrol.mapper.UserMapper;
import happy.schoolcarpatrol.result.Result;
import happy.schoolcarpatrol.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 木月丶
 * @since 2023-04-06
 */
@RestController
@RequestMapping("/patrol")
public class PatrolController {

    @Resource
    UserMapper mapper;

    @GetMapping("/queryBaseInfo")
    @ApiOperation("巡查人员扫码查询车辆基本信息")
    public Result queryBaseInfo(String licenseNumber){
        UserVo info = mapper.queryBasicInfo(licenseNumber);
        return info != null ? Result.successWithData(info) : Result.error("暂无信息");
    }


}
