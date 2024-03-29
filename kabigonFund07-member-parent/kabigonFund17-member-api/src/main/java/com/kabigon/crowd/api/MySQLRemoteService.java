package com.kabigon.crowd.api;


import com.kabigon.crowd.entity.po.AddressPO;
import com.kabigon.crowd.entity.po.MemberPO;
import com.kabigon.crowd.entity.vo.*;
import com.kabigon.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("kabigon-crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/member/remote")
    ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody  ProjectVO projectVO, @RequestParam("memberId") Integer memberId);
    @RequestMapping("/get/portal/type/project/data")
    ResultEntity<List<PortalTypeVO>> getPortalTypeProjectData();

    @RequestMapping("/get/project/detail/remote/{projectId}")
    ResultEntity<DetailProjectVO> getDetailProjecrtVORemote(@PathVariable("projectId") Integer projectId);

    @RequestMapping("get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId);

    @RequestMapping("get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId);

    @RequestMapping("save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);

    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);
}
