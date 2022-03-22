package com.kabigon.crowd.service.api;

import com.kabigon.crowd.entity.po.AddressPO;
import com.kabigon.crowd.entity.po.AddressPOExample;
import com.kabigon.crowd.entity.po.OrderPO;
import com.kabigon.crowd.entity.po.OrderProjectPO;
import com.kabigon.crowd.entity.vo.AddressVO;
import com.kabigon.crowd.entity.vo.OrderProjectVO;
import com.kabigon.crowd.entity.vo.OrderVO;
import com.kabigon.crowd.mapper.AddressPOMapper;
import com.kabigon.crowd.mapper.OrderPOMapper;
import com.kabigon.crowd.mapper.OrderProjectPOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {
        OrderProjectVO orderProjectVO = orderProjectPOMapper.selectOrderProjectVO(returnId);
        return orderProjectVO;

    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {
        AddressPOExample addressPOExample = new AddressPOExample();
        AddressPOExample.Criteria criteria = addressPOExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);
        List<AddressVO> addressVOList=new ArrayList<>();

        for (AddressPO addressPO:addressPOList){
            AddressVO addressVO=new AddressVO();
            BeanUtils.copyProperties(addressPO,addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
    @Override
    public void saveAddress(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO,addressPO);
        addressPOMapper.insert(addressPO);

    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
    @Override
    public void saveOrder(OrderVO orderVO) {
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(),orderProjectPO);

        //保存orderpo自动生成的主键是orderprojectpo需要的外键
        orderPOMapper.insert(orderPO);
        Integer id = orderPO.getId();
        orderProjectPO.setOrderId(id);
        orderProjectPOMapper.insert(orderProjectPO);

    }
}
