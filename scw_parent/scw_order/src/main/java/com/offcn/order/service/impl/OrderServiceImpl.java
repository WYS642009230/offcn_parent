package com.offcn.order.service.impl;

import com.offcn.dycommon.enums.OrderStatusEnumes;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.po.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.order.vo.resp.TReturn;
import com.offcn.utils.AppDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Override
    public TOrder saveOrder(OrderInfoSubmitVo vo) {
        TOrder order = new TOrder();
        String accessToken = vo.getAccessToken();
        String memberId = redisTemplate.opsForValue().get(accessToken);

        order.setMemberid(Integer.parseInt(memberId));

        order.setProjectid(vo.getProjectid());
        order.setReturnid(vo.getReturnid());
        String orderNum = UUID.randomUUID().toString().replace("-", "");
        order.setOrdernum(orderNum);

        order.setCreatedate(AppDateUtils.getFormatTime());
        AppResponse<List<TReturn>> returnAppResponse = projectServiceFeign.returnInfo(vo.getProjectid());

        List<TReturn> returns = returnAppResponse.getData();
        TReturn tReturn = returns.get(0);
        //计算回报金额   支持数量*支持金额+运费
        Integer totalMoney = vo.getRtncount() * tReturn.getSupportmoney() + tReturn.getFreight();
        order.setMoney(totalMoney);
        //回报数量
        order.setRtncount(vo.getRtncount());
        //支付状态  未支付
        order.setStatus(OrderStatusEnumes.UNPAY.getCode() + "");
        //收货地址
        order.setAddress(vo.getAddress());
        //是否开发票
        order.setInvoice(vo.getInvoice().toString());
        //发票名头
        order.setInvoictitle(vo.getInvoictitle());
        //备注
        order.setRemark(vo.getRemark());
        orderMapper.insertSelective(order);
        return order;

    }
}
