package com.offcn.user.service.impl;

import com.offcn.user.enums.UserExceptionEnum;
import com.offcn.user.exception.UserException;
import com.offcn.user.mapper.TMemberAddressMapper;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.pojo.TMemberAddressExample;
import com.offcn.user.pojo.TMemberExample;
import com.offcn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TMemberMapper memberMapper;
    @Autowired
    private TMemberAddressMapper addressMapper;

    @Override
    public void registerUser(TMember member) {
        //1.检查此手机号是否存在
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(member.getLoginacct());
        long l = memberMapper.countByExample(example);
        if (l > 0) {
            throw new UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }
        //2.手机号未背注册,设置相关参数，保存注册信息
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(member.getUserpswd());
        member.setUserpswd(encode);
        member.setUsername(member.getLoginacct());
        member.setEmail(member.getEmail());

        //实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证
        member.setAuthstatus("0");
        //用户类型: 0 - 个人， 1 - 企业
        member.setUsertype("0");
        //账户类型: 0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府
        member.setAccttype("2");
        System.out.println("插入数据:" + member.getLoginacct());
        memberMapper.insertSelective(member);

    }

    @Override
    public TMember login(String loginAcct, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(loginAcct);
        List<TMember> tMembers = memberMapper.selectByExample(example);
        if (tMembers != null && tMembers.size() == 1) {
            TMember member = tMembers.get(0);
            boolean matches = encoder.matches(password, member.getUserpswd());
            return matches ? member : null;
        }
        return null;
    }

    @Override
    public TMember findTmemberById(Integer id) {
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return memberMapper.selectByExample(example).get(0);
    }

    @Override
    public List<TMemberAddress> addressList(Integer memberId) {
        TMemberAddressExample example = new TMemberAddressExample();
        TMemberAddressExample.Criteria criteria = example.createCriteria();
        criteria.andMemberidEqualTo(memberId);
        List<TMemberAddress> tMemberAddresses = addressMapper.selectByExample(example);
        return tMemberAddresses;
    }
}
