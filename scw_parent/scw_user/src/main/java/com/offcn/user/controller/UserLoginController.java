package com.offcn.user.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.user.pojo.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.sms.SmsTemplate;
import com.offcn.user.vo.req.UserRegistVo;
import com.offcn.user.vo.req.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "短信发送类")
public class UserLoginController {
    @Autowired
    private SmsTemplate smsTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "发送并存储验证码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "mobile", value = "接收验证码的手机号")
    })
    @PostMapping("/sendCode")
    public AppResponse<Object> sendSms(String mobile) {

        //1.生成4位验证码;
        String code = RandomStringUtils.randomNumeric(4);
        //2.保存验证码，发送验证码
        stringRedisTemplate.opsForValue().set(mobile, code, 15, TimeUnit.MINUTES);
        //3.发送
        String result = smsTemplate.sendSms(mobile, code);

        if (result.equals("") || result.equals("fail")) {
            //短信发送失败
            return AppResponse.fail("短信发送失败");
        } else {
            return AppResponse.ok("短信发送成功");
        }

    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/regist")
    public AppResponse<Object> regist(UserRegistVo vo) {

        String code = stringRedisTemplate.opsForValue().get(vo.getLoginacct());
        if (code != null && code != "") {
            if (code.equalsIgnoreCase(vo.getCode())) {
                TMember member = new TMember();
                BeanUtils.copyProperties(vo, member);

                try {
                    userService.registerUser(member);
                    stringRedisTemplate.delete(member.getLoginacct());
                    return AppResponse.ok("注册成功");

                } catch (Exception e) {
                    e.printStackTrace();
                    return AppResponse.fail(e.getMessage());
                }

            } else {
                return AppResponse.fail("验证码错误");
            }
        } else {
            return AppResponse.fail("验证码过期，请重新获取");
        }
    }

    @ApiOperation(value = "登录的方法")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginAcct", value = "登录手机号", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true)
    })
    @PostMapping("/login")
    public AppResponse<UserRespVo> login(String loginAcct, String password) {
        TMember member = userService.login(loginAcct, password);
        if (member == null) {
            AppResponse<UserRespVo> fail = AppResponse.fail(null);
            fail.setMsg("用户名密码错误");
            return fail;
        }
        //2、登录成功;生成令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        UserRespVo vo = new UserRespVo();
        BeanUtils.copyProperties(member, vo);
        vo.setAccessToken(token);

        stringRedisTemplate.opsForValue().set(token, member.getId() + "", 2, TimeUnit.HOURS);
        return AppResponse.ok(vo);

    }

}
