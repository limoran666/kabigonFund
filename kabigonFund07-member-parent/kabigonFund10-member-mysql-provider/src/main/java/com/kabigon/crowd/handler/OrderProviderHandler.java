package com.kabigon.crowd.handler;


import com.kabigon.crowd.entity.po.AddressPO;
import com.kabigon.crowd.entity.vo.AddressVO;
import com.kabigon.crowd.entity.vo.OrderProjectVO;
import com.kabigon.crowd.entity.vo.OrderVO;
import com.kabigon.crowd.service.api.OrderService;
import com.kabigon.crowd.service.api.OrderServiceImpl;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderProviderHandler {

    @Autowired
    private OrderService orderService;


    @RequestMapping("get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId){
        OrderProjectVO orderProjectVO= null;
        try {
            orderProjectVO = orderService.getOrderProjectVO(returnId);
            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }


    }

    @RequestMapping("get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId){

        try {
            List<AddressVO>  addressVOList=orderService.getAddressVOList(memberId);
            return ResultEntity.successWithData(addressVOList);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }

    }

    @RequestMapping("save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody  AddressVO addressVO){
        try {
            orderService.saveAddress(addressVO);
            return ResultEntity.successWithoutData();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }

    }

    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO){

        try {
            orderService.saveOrder(orderVO);
            return ResultEntity.successWithoutData();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }
}
