package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Student;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @RequestMapping("/test/exception.html")
    public String testException() {
        String i = null;
        System.out.println(i.length());
        return "target";
    }

    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComposeObject(
            @RequestBody Student student, HttpServletRequest request) {
        boolean result = CrowdUtil.isAjaxType(request);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>result: " + result);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>student: " + student);
        // 将“查询”到的Student对象封装到ResultEntity中返回
        ResultEntity<Student> resultEntity = ResultEntity.successWithData(student);
        return resultEntity;
    }

    @RequestMapping("/send/array/three.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> list) {
        for (Integer num : list) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>num: " + num);
        }
        return "success";
    }

    @RequestMapping("/send/array/one.html")
    @ResponseBody
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> list) {
        for (Integer num : list) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>num: " + num);
        }
        return "success";
    }

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap, HttpServletRequest request) {
        boolean result = CrowdUtil.isAjaxType(request);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>result: " + result);
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        //测试自定义xml捕获异常
//        int i = 1 / 0;
//        String i = null;
//        System.out.println(i.length());
        return "target";
    }
}
