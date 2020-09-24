package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.contants.ProjectConstant;
import com.offcn.project.po.TReturn;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectBaseInfoVo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import com.offcn.vo.BaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project")
@Slf4j
@Api(tags = "项目基本功能模块（创建、保存、项目信息获取、文件上传等）")
public class ProjectCreateController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectCreateService projectCreateService;


    @GetMapping("/init")
    @ApiOperation(value = "项目发起第1步-阅读同意协议")
    public AppResponse<String> init(BaseVo vo) {
        String accessToken = vo.getAccessToken();

        String memberId = stringRedisTemplate.opsForValue().get(accessToken);

        if (StringUtils.isEmpty(memberId)) {
            return AppResponse.fail("无此权限，请先登录");
        }

        int id = Integer.parseInt(memberId);
        String projectToken = projectCreateService.initCreateProject(id);

        return AppResponse.ok(projectToken);
    }

    @ApiOperation("项目发起第2步-保存项目的基本信息")
    @PostMapping("/savebaseInfo")
    public AppResponse<String> saveBaseInfo(ProjectBaseInfoVo vo) {
        String orignal = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken());

        ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(orignal, ProjectRedisStorageVo.class);

        BeanUtils.copyProperties(vo, projectRedisStorageVo);

        String string = JSON.toJSONString(projectRedisStorageVo);

        stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken(), string);
        return AppResponse.ok("ok");
    }

    @ApiOperation("项目发起第3步-项目保存项目回报信息")
    @PostMapping("/savereturn")
    public AppResponse<String> saveReturnInfo(@RequestBody List<ProjectReturnVo> pro) {
        ProjectReturnVo projectReturnVo = pro.get(0);
        String projectToken = projectReturnVo.getProjectToken();

        String projectContext = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken);
        ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(projectContext, ProjectRedisStorageVo.class);

        List<TReturn> returns = new ArrayList<>();

        for (ProjectReturnVo projectReturnVo1 : pro) {
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(projectReturnVo1, tReturn);
            returns.add(tReturn);
        }

        projectRedisStorageVo.setProjectReturns(returns);
        String jsonString = JSON.toJSONString(projectRedisStorageVo);
        //5、重新更新到redis
        stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken, jsonString);
        return AppResponse.ok("OK");
    }
    @ApiOperation("项目发起第4步-项目保存项目回报信息")
    @PostMapping("/submit")
    public AppResponse<Object> submit(String accessToken,String projectToken,String ops){
        String memberId  = stringRedisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberId)){
            return AppResponse.fail("无权限，请先登录");
        }
        String projectJsonStr  = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken);
        ProjectRedisStorageVo storageVo = JSON.parseObject(projectJsonStr, ProjectRedisStorageVo.class);
        if (!StringUtils.isEmpty(ops)){
            if (ops.equals("0")){
                //草稿
                projectCreateService.saveProjectInfo(ProjectStatusEnume.DRAFT,storageVo);
            }else if (ops.equals("1")){
                //提交
                projectCreateService.saveProjectInfo(ProjectStatusEnume.SUBMIT_AUTH,storageVo);
            }

        }else {
            AppResponse<Object> appResponse = AppResponse.fail(null);
            appResponse.setMsg("不支持此操作");
            return appResponse;
        }
        return AppResponse.fail("null");
    }
}
