package com.atguigu.crowd.test;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.DetailReturnVO;
import com.atguigu.crowd.entity.vo.PortalProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyBatisTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    private final Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void testLoadDetailProjectVO() {

        Integer projectId = 1;

        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);

        logger.info(detailProjectVO.getProjectId() + "");
        logger.info(detailProjectVO.getProjectName());
        logger.info(detailProjectVO.getProjectDesc());
        logger.info(detailProjectVO.getFollowerCount() + "");
        logger.info(detailProjectVO.getStatus() + "");
        logger.info(detailProjectVO.getMoney() + "");
        logger.info(detailProjectVO.getSupportMoney() + "");
        logger.info(detailProjectVO.getPercentage() + "");
        logger.info(detailProjectVO.getDeployDate() + "");
        logger.info(detailProjectVO.getSupporterCount() + "");
        logger.info(detailProjectVO.getHeaderPicturePath());

        List<String> detailPicturePathList = detailProjectVO.getDetailPicturePathList();
        for (String path : detailPicturePathList) {
            logger.info("detail path="+path);
        }

        List<DetailReturnVO> detailReturnVOList = detailProjectVO.getDetailReturnVOList();
        for (DetailReturnVO detailReturnVO : detailReturnVOList) {
            logger.info(detailReturnVO.getReturnId() + "");
            logger.info(detailReturnVO.getSupportMoney() + "");
            logger.info(detailReturnVO.getSignalPurchase() + "");
            logger.info(detailReturnVO.getPurchase() + "");
            logger.info(detailReturnVO.getSupporterCount() + "");
            logger.info(detailReturnVO.getFreight() + "");
            logger.info(detailReturnVO.getReturnDate() + "");
            logger.info(detailReturnVO.getContent() + "");
            logger.info(detailReturnVO.getFreight() + "");
        }
    }

    @Test
    public void testLoadTypeData() {
        List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();

        for (PortalTypeVO portalTypeVO : portalTypeVOList) {

            String name = portalTypeVO.getName();
            String remark = portalTypeVO.getRemark();
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "name:" + name + ", remark: " + remark);

            List<PortalProjectVO> portalProjectVOList = portalTypeVO.getPortalProjectVOList();
            for (PortalProjectVO portalProjectVO : portalProjectVOList) {
                if (portalProjectVO == null) {
                    continue;
                }

                logger.info(portalProjectVO.toString());
            }
        }
    }

    @Test
    public void testMapper() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String strPassword = "123123";
        String encode = passwordEncoder.encode(strPassword);
        MemberPO memberPO = new MemberPO(null, "jack", encode, "杰克",
                "jack@test.com", 1, 1, "王杰克", "123123", 2);
        memberPOMapper.insert(memberPO);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug(">>>>>>>>>>>>>>" + connection.toString());
    }

}
