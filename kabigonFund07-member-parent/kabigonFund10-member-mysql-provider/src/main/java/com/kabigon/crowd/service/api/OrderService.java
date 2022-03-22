package com.kabigon.crowd.service.api;

import com.kabigon.crowd.entity.vo.AddressVO;
import com.kabigon.crowd.entity.vo.OrderProjectVO;
import com.kabigon.crowd.entity.vo.OrderVO;

import java.util.List;

public interface OrderService {
   OrderProjectVO getOrderProjectVO(Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
