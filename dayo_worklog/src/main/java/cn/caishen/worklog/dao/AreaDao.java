package cn.caishen.worklog.dao;

import cn.caishen.domain.domain.po.Area;
import cn.caishen.domain.utils.LbMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AreaDao extends BaseMapper<Area> {

    @Select("SELECT AREA_NAME as name, AREA_ID as id, CREATE_TIME " +
            "FROM TB_AREA WHERE COALESCE(DELETE_FLAG, 0) = 0 ORDER BY AREA_NAME")
    List<LbMap> findArea();

    @Select("SELECT USER_NAME AS name, USER_ID AS id, CREATE_TIME " +
            "FROM TB_USER WHERE COALESCE(DELETE_FLAG, 0) = 0 ORDER BY USER_NAME")
    List<LbMap> findUser();
}
