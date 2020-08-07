package cn.caishen.auth2.dao;

import org.springframework.stereotype.Repository;

import cn.caishen.domain.utils.DayoList;

@Repository
public class UserDao extends BaseDao {
	
	public DayoList getUser(String name) {		
		String sql = "SELECT User_Name, Tel_No, PASSWORD FROM tb_User where User_Name = '%s'";
		sql = String.format(sql, name);	
		DayoList user = rsToList(jdbcTemplate.queryForRowSet(sql));   
    	return user;		
	}		

}
