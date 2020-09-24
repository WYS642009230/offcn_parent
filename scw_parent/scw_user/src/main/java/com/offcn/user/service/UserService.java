package com.offcn.user.service;

import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;

import java.util.List;

public interface UserService {
    //注册
    public void registerUser(TMember member);
    //登录
    public TMember login(String loginAcct,String password);

    public TMember findTmemberById(Integer id);

    public List<TMemberAddress> addressList(Integer memberId);
}
