package com.offcn.user.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.service.UserService;
import com.offcn.user.vo.req.UserRespVo;
import com.offcn.user.vo.resp.UserAddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户信息")
public class UserInfoController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    //根据用户编号获取用户信息
    @GetMapping("/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id) {
        TMember tmember = userService.findTmemberById(id);
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(tmember, userRespVo);

        return AppResponse.ok(userRespVo);
    }

    @ApiOperation(value = "获取用户收货地址")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "访问令牌", name = "accessToken", required = true)
    })
    @GetMapping("/info/address")
    public AppResponse<List<UserAddressVo>> address(String accessToken) {
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberId)) {
            return AppResponse.fail(null);
        }
        List<TMemberAddress> addressList = userService.addressList(Integer.parseInt(memberId));
        List<UserAddressVo> voList = new ArrayList<>();
        for (TMemberAddress address : addressList) {
            UserAddressVo vo = new UserAddressVo();
            vo.setId(address.getId());
            vo.setAddress(address.getAddress());
            voList.add(vo);
        }
        return AppResponse.ok(voList);

    }
}
