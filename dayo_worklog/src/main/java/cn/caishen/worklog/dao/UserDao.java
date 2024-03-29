package cn.caishen.worklog.dao;

import cn.caishen.domain.domain.po.User;
import cn.caishen.domain.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("SELECT TB_USER.USER_ID, TB_USER.USER_NAME, TB_USER.TEL_NO, TB_USER.EMAIL, TB_USER.PASSWORD, " +
            "TB_USER.USER_TYPE, TB_USER.CREATE_TIME, TB_USER.DELETE_FLAG, TB_USER.DELETE_DATE, TB_USER.ROW_VERSION " +
            "FROM TB_USER WHERE USER_NAME = #{username} OR TEL_NO = #{telNo}")
    List<User> checkUserInfo(@Param("telNo") String telNo, @Param("username") String username);

    @Select("SELECT TB_USER.USER_ID, TB_USER.USER_NAME, TB_USER.TEL_NO, TB_USER.EMAIL, TB_USER.PASSWORD, " +
            "TB_USER.USER_TYPE, TB_USER.CREATE_TIME, TB_USER.DELETE_FLAG, TB_USER.DELETE_DATE, TB_USER.ROW_VERSION, " +
            "CASE WHEN USER_ROLE.USER_ID IS NULL THEN '0' ELSE '1' END AS CHECKED " +
            "FROM TB_USER LEFT JOIN (SELECT USER_ID FROM TB_USER_ROLE WHERE ROLE_ID = #{roleId}) USER_ROLE ON TB_USER.USER_ID = USER_ROLE.USER_ID " +
            "WHERE COALESCE(TB_USER.DELETE_FLAG, 0) = 0 ORDER BY TB_USER.CREATE_TIME DESC")
    List<LbMap> findByRoleId(String roleId);

    @Select("SELECT TB_ROLE.ROLE_ID, TB_ROLE.ROLE_NAME, TB_ROLE.ROLE_TYPE, TB_ROLE.CREATE_TIME, TB_USER_ROLE.USER_ID FROM TB_ROLE " +
            "INNER JOIN TB_USER_ROLE ON TB_ROLE.ROLE_ID = TB_USER_ROLE.ROLE_ID " +
            "WHERE TB_USER_ROLE.USER_ID = #{userId} AND COALESCE(TB_ROLE.DELETE_FLAG, 0) = 0")
    List<LbMap> findRoleByUserId(String userId);
}
