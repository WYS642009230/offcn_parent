package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.contants.ProjectConstant;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TProjectMapper projectMapper;
    @Autowired
    private TProjectImagesMapper projectImagesMapper;
    @Autowired
    private TProjectTagMapper projectTagMapper;
    @Autowired
    private TProjectTypeMapper projectTypeMapper;
    @Autowired
    private TReturnMapper returnMapper;

    @Override
    public String initCreateProject(Integer memberId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        ProjectRedisStorageVo initVo = new ProjectRedisStorageVo();

        initVo.setMemberid(memberId);

        String jsonString = JSON.toJSONString(initVo);
        //TEMP_PROJECT_PREFIX = "project:create:temp:";
        stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + token, jsonString);

        return token;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo projectVo) {
        TProject projectBase = new TProject();
        BeanUtils.copyProperties(projectVo, projectBase);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        projectBase.setCreatedate(sdf.format(new Date()));

        projectMapper.insertSelective(projectBase);

        Integer projectId = projectBase.getId();

        String headerImage = projectVo.getHeaderImage();

        TProjectImages images = new TProjectImages(null, projectId, headerImage, ProjectImageTypeEnume.HEADER.getCode());

        projectImagesMapper.insertSelective(images);

        List<String> detailsImage = projectVo.getDetailsImage();

        for (String s : detailsImage) {
            TProjectImages tProjectImages = new TProjectImages(null, projectId, s, ProjectImageTypeEnume.DETAILS.getCode());
            projectImagesMapper.insertSelective(tProjectImages);

        }

        List<Integer> tagids = projectVo.getTagids();
        for (Integer tagid : tagids) {
            TProjectTag tProjectTag = new TProjectTag(null, projectId, tagid);
            projectTagMapper.insertSelective(tProjectTag);
        }


        List<Integer> typeids = projectVo.getTypeids();
        for (Integer typeid : typeids) {
            TProjectType tProjectType = new TProjectType(null, projectId, typeid);
            projectTypeMapper.insertSelective(tProjectType);
        }

        List<TReturn> projectReturns = projectVo.getProjectReturns();
        for (TReturn projectReturn : projectReturns) {
            projectReturn.setProjectid(projectId);
            returnMapper.insertSelective(projectReturn);
        }
        stringRedisTemplate.delete(ProjectConstant.TEMP_PROJECT_PREFIX + projectVo.getProjectToken());

    }
}
