package com.offcn.project.service.impl;

import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    private TReturnMapper returnMapper;
    @Autowired
    private TProjectMapper projectMapper;
    @Autowired
    private TProjectImagesMapper imagesMapper;
    @Autowired
    private TTagMapper tagMapper;
    @Autowired
    private TTypeMapper typeMapper;

    @Override
    public List<TReturn> getProjectReturns(Integer projectId) {
        TReturnExample example = new TReturnExample();
        TReturnExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(projectId);
        List<TReturn> tReturns = returnMapper.selectByExample(example);
        return tReturns;
    }

    @Override
    public List<TProject> getAllProjects() {

        return projectMapper.selectByExample(null);
    }

    @Override
    public List<TProjectImages> getProjectImages(Integer id) {
        TProjectImagesExample example = new TProjectImagesExample();
        TProjectImagesExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(id);
        List<TProjectImages> images = imagesMapper.selectByExample(example);
        return images;
    }

    @Override
    public TProject getProjectInfo(Integer projectId) {
        TProjectExample example = new TProjectExample();
        TProjectExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(projectId);

        return projectMapper.selectByExample(example).get(0);
    }

    @Override
    public List<TTag> getAllProjectTags() {

        return tagMapper.selectByExample(null);
    }

    @Override
    public List<TType> getProjectTypes() {
        return typeMapper.selectByExample(null);
    }

    @Override
    public TReturn getReturnInfo(Integer returnId) {
        TReturnExample example = new TReturnExample();
        TReturnExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(returnId);
        return returnMapper.selectByExample(example).get(0);
    }
}
