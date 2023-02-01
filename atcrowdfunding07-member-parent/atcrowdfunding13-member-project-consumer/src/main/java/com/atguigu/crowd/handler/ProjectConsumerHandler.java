package com.atguigu.crowd.handler;


import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.config.OSSProperties;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.vo.*;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectConsumerHandler {

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;


    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId, Model model) {

        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);

        String result = resultEntity.getResult();

        if (ResultEntity.SUCCESS.equals(result)) {
            DetailProjectVO detailProjectVO = resultEntity.getData();
            model.addAttribute("detailProjectVO", detailProjectVO);
        }

        return "project-show-detail";

    }

    @RequestMapping("/create/confirm")
    public String saveConfirm(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session, ModelMap modelMap) {

        // 1.从Session域读取之前临时存储的ProjectVO对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT);

        // 2.如果projectVO为null抛运行异常
        if (projectVO == null) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMP_PROJECT_MISSING);
        }

        // 3.将确认信息数据设置到projectVO对象中
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 4.从Session域读取当前登录的用户
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberLoginVOId = memberLoginVO.getId();

        // 5.调用远程方法保存projectVO对象
        ResultEntity<String> saveProjectResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberLoginVOId);

        // 6.判断远程的保存操作是否成功
        if (ResultEntity.FAILED.equals(saveProjectResultEntity)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveProjectResultEntity.getMessage());

            return "project-confirm";
        }

        // 7.将临时的ProjectVO对象从Session域移除
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT);

        // 8.如果远程保存成功则跳转到最终完成页面
        return "redirect:http://localhost/project/create/success";

    }

    /**
     * 接收整个回报信息数据
     *
     * @param returnVO
     * @param session
     * @return
     */
    @RequestMapping("/create/save/return.json")
    @ResponseBody
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {

        try {
            // 1.从session域中读取之前缓存的ProjectVO对象
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT);

            // 2.判断projectVO是否为null
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMP_PROJECT_MISSING);
            }

            // 3.从projectVO对象中获取存储回报信息的集合
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            // 4.判断returnVOList集合是否有效
            if (returnVOList == null || returnVOList.size() == 0) {

                // 5.创建集合对象对returnVOList进行初始化
                returnVOList = new ArrayList<ReturnVO>();

                // 6.为了让以后能够正常使用这个集合，设置到projectVO对象中
                projectVO.setReturnVOList(returnVOList);

            }

            // 7.将收集了表单数据的returnVO对象存入集合
            returnVOList.add(returnVO);

            // 8.把数据有变化的ProjectVO对象重新存入Session域，以确保新的数据最终能够存入Redis
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT, projectVO);

            // 9.所有操作成功完成返回成功
            return ResultEntity.successWithoutData();

        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 接收回报页面异步上传的图片
     *
     * @param returnPicture
     * @return
     * @throws IOException
     */
//    JavaScript代码：formData.append("returnPicture", file);
//    returnPicture是请求参数的名字
//    file是请求参数的值，也就是要上传的文件
    @RequestMapping("/create/upload/return/picture.json")
    @ResponseBody
    public ResultEntity<String> uploadReturnPicture(
            @RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {

        ResultEntity<String> uploadReturnPictureResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename()
        );

        return uploadReturnPictureResultEntity;

    }

    /**
     * 保存发起项目的基本信息
     *
     * @param projectVO
     * @param headerPicture
     * @param detailPictureList
     * @param session
     * @param modelMap
     * @return
     * @throws IOException
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(
            // 接收除了上传图片之外的其他普通数据
            ProjectVO projectVO,

            // 接收上传的头图
            MultipartFile headerPicture,

            // 接收上传的详情图片
            List<MultipartFile> detailPictureList,

            // 用来将收集了一部分数据的ProjectVO对象存入Session域
            HttpSession session,

            // 用来在当前操作失败后返回上一个表单页面时携带提示消息
            ModelMap modelMap) throws IOException {

        // 一、完成头图上传
        // 1.获取当前headerPicture对象是否为空
        boolean headerPictureEmpty = headerPicture.isEmpty();

        // 2.如果没有上传头图
        if (headerPictureEmpty) {
            // 则返回到表单页面并显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PICTURE_EMPTY);

            return "project-launch";
        }

        // 3.如果用户确实上传了有内容的文件，则执行上传
        ResultEntity<String> uploadHeaderPictureResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());

        // 4.判断头图是否上传成功
        String uploadHeaderPictureResult = uploadHeaderPictureResultEntity.getResult();

        if (ResultEntity.SUCCESS.equals(uploadHeaderPictureResult)) {

            // 5.如果成功则从返回的数据中获取图片访问路径
            String headerPicturePath = uploadHeaderPictureResultEntity.getData();

            // 6.存入ProjectVO对象中
            projectVO.setHeaderPicturePath(headerPicturePath);

        } else {

            // 7.如果上传失败则返回到表单页面并显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PICTURE_UPLOAD_FAILED);

        }

        // 二、上传详情图片
        // 1.创建一个用来存放详情图片路径的集合
        List<String> detailPicturePathList = new ArrayList<String>();

        // 2.检查detailPictureList是否有效
        if (detailPictureList == null || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PICTURE_EMPTY);

            return "project-launch";
        }

        // 3.遍历detailPictureList集合
        for (MultipartFile detailPicture : detailPictureList) {

            // 4.当前detailPicture是否为空
            if (detailPicture.isEmpty()) {

                // 5.检测到详情图片中单个文件为空也是回去显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PICTURE_EMPTY);

                return "project-launch";
            }

            // 6.执行上传
            ResultEntity<String> uploadDetailPictureResultEntity = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename()
            );

            // 7.检查上传结果
            String uploadDetailPictureResult = uploadDetailPictureResultEntity.getResult();

            if (ResultEntity.SUCCESS.equals(uploadDetailPictureResult)) {

                // 8.收集刚刚上传的图片的访问路径
                String detailPicturePath = uploadDetailPictureResultEntity.getData();

                detailPicturePathList.add(detailPicturePath);

            } else {

                // 9.如果上传失败则返回到表单页面并显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PICTURE_UPLOAD_FAILED);

                return "project-launch";
            }

        }

        // 10.将存放了详情图片访问路径的集合存入ProjectVO中
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 三、后续操作
        // 1.将ProjectVO对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMP_PROJECT, projectVO);

        // 2.以完整的访问路径前往下一个收集回报信息的页面
        return "redirect:http://localhost/project/return/info/page";
    }
}

