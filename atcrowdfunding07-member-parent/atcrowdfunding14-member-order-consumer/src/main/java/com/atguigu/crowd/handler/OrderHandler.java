package com.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.atguigu.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    Logger logger = LoggerFactory.getLogger(OrderHandler.class);

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session) {

        // 1.执行地址信息的保存
        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);

        String result = resultEntity.getResult();

        logger.debug(">>>>>>>>>>>>>>>>>> insert address result = " + result);

        // 2.从Session域获取orderProjectVO对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        // 3.从orderProjectVO对象中获取returnCount
        Integer returnCount = orderProjectVO.getReturnCount();

        // 4.重定向到指定地址，重新进入确认订单页面
        return "redirect:http://localhost/order/confirm/order/" + returnCount;
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount,
                                       HttpSession session) {

        // 1.把接收到的回报数量合并到Session域
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        orderProjectVO.setReturnCount(returnCount);

        session.setAttribute("orderProjectVO", orderProjectVO);

        // 2.获取当前已登录用户的id
        MemberLoginVO loginMember = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberId = loginMember.getId();

        // 3.查询目前的收货地址数据
        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(memberId);

        String result = resultEntity.getResult();

        if (ResultEntity.SUCCESS.equals(result)) {

            List<AddressVO> addressVOList = resultEntity.getData();

            session.setAttribute("addressVOList", addressVOList);

        }

        return "confirm_order";
    }

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") Integer projectId,
                                        @PathVariable("returnId") Integer returnId,
                                        HttpSession session) {

        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVORemote(projectId, returnId);

        String result = resultEntity.getResult();

        if (ResultEntity.SUCCESS.equals(result)) {

            OrderProjectVO orderProjectVO = resultEntity.getData();

            session.setAttribute("orderProjectVO", orderProjectVO);

        }

        return "confirm_return";

    }

}
