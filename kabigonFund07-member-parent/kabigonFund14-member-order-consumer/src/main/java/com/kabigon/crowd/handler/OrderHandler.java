package com.kabigon.crowd.handler;


import com.kabigon.crowd.api.MySQLRemoteService;
import com.kabigon.crowd.constant.CrowdConstant;
import com.kabigon.crowd.entity.vo.AddressVO;
import com.kabigon.crowd.entity.vo.MemberLoginVO;
import com.kabigon.crowd.entity.vo.OrderProjectVO;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/confirm/return/info/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("returnId") Integer returnId, HttpSession session){
       ResultEntity<OrderProjectVO> resultEntity=mySQLRemoteService.getOrderProjectVORemote(returnId);
       if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
           OrderProjectVO orderProjectVO =resultEntity.getData();
           session.setAttribute("orderProjectVO",orderProjectVO);
       }
    return "confirm-return";
    }


    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount,HttpSession session){
        //吧接收到的回报数量合并到Session域中
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        orderProjectVO.setReturnCount(returnCount);

        session.setAttribute("orderProjectVO",orderProjectVO);

        //获取当前用户的id
        MemberLoginVO memberLoginVO=(MemberLoginVO)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberId = memberLoginVO.getId();
        //查询目前的收货地址数据
        ResultEntity<List<AddressVO>> resultEntity=mySQLRemoteService.getAddressVORemote(memberId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            List<AddressVO> list = resultEntity.getData();
            session.setAttribute("addressVOList",list);
            return "confirm-order";
        }
        return "confirm-order";

    }

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO,HttpSession session){
        OrderProjectVO  orderProjectVO =(OrderProjectVO)session.getAttribute("orderProjectVO");
        ResultEntity<String> resultEntity= mySQLRemoteService.saveAddressRemote(addressVO);
        Integer returnCount = orderProjectVO.getReturnCount();
        return "redirect:http://localhost:81/order/confirm/order/"+returnCount;

    }

}
