package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Auth;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface AuthService {
    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    List<Auth> getAll();

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
