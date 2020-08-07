package cn.caishen.auth2.dao;

import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import cn.caishen.domain.utils.DayoList;
import cn.caishen.domain.utils.DayoMap;
import cn.caishen.domain.utils.StringUtil;

public class BaseDao {

	@Resource	
	protected JdbcTemplate jdbcTemplate;	
	
	public boolean isOracle() {
		return false; 
	}	
	
	protected boolean isEmpty(String sql) {
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);		
		rs.last();
		if (rs.getRow() > 0) {
			return false;
		}else {
			return true;
		}		
	}
	
	protected boolean execute(String sql) {
		int affected = jdbcTemplate.update(sql);
		if (affected > 0) {
			return true;
		} else {
			return false;
		}		
	}	
	
	public boolean existTable(String tableName) {
		String sql = String.format(
				"select Top 1 sysobjects.id from dbo.sysobjects " 
				+ "where id = object_id(N'[dbo].[%s]') "
				+ "and OBJECTPROPERTY(id, N'IsUserTable') = 1",
				tableName);
		return !isEmpty(sql);
	}	
	
	public boolean existField(String tableName, String fieldName) {
		String sql = String.format(
				"select Top 1 syscolumns.name from syscolumns "
				+ "inner join sysObjects on syscolumns.id = sysObjects.id "
				+ "where sysobjects.name = '%s' and syscolumns.name = '%s' ", 
				tableName, fieldName);
		return !isEmpty(sql);
	}		
	
	public boolean createTable(String tableName) {
		String sql = " create table %s (FID int not null identity(1, 1)) ";
		sql = String.format(sql, tableName);
		return execute(sql);
	}	
	
	public boolean addFieldInt(String tableName, String fieldName) {
		if (!existField(tableName, fieldName)) {
			String sql = " Alter Table %s Add %s int ";
			sql = String.format(sql, tableName, fieldName);	
			return execute(sql);			
		}else {
			return true;
		}
	}
	
	public boolean addFieldVarChar(String tableName, String fieldName, int fieldLen) {
		if (!existField(tableName, fieldName)) {
			String fieldLength=String.valueOf(fieldLen);
	        if(fieldLen==0){
	        	fieldLength="max";
	        }		
			String sql = " Alter Table %s Add %s varchar(%s) ";
			sql = String.format(sql, tableName, fieldName, fieldLength);	
			return execute(sql);				
		}else {
			return true;
		}
	}	
	
	public boolean addFieldDate(String tableName, String fieldName) {
		if (!existField(tableName, fieldName)) {
			String sql = " Alter Table %s Add %s datetime ";			
			sql = String.format(sql, tableName, fieldName);
			return execute(sql);
		}else {
			return true;
		}
	}
	
	public boolean addFieldDecimal(String tableName, String fieldName,
			int precision, int decimal) {
		if (!existField(tableName, fieldName)) {
			String sql = " Alter Table %s Add %s decimal(%d, %d) ";
			sql = String.format(sql, tableName, fieldName, precision, decimal);
			return execute(sql);
		}else {
			return true;
		}
	}
	
	public boolean addFieldText(String tableName, String fieldName) {
		if (!existField(tableName, fieldName)) {
			String sql = " Alter Table %s Add %s text ";	
			return execute(sql);
		}else {
			return true;
		}
	}	
	
	// 创建索引
	public boolean addIndex(String indexName, String tableName,
			String fieldNames, String indexType) {
		String sql = " select * from sysindexes where name = '%S' ";
		sql = String.format(sql, indexName);
		DayoList dList = queryForDayoList(sql);
	
		if (dList.size() <= 0) {
			sql = " create %S Index %S On %S(%S) ";
			sql = String.format(sql, indexType, indexName, tableName,
					fieldNames);
			return execute(sql);
		}else {
			return true;
		}
	}
	
	/*
	 * isClustered是否聚集索引，默认为false非聚焦索引(索引可分聚集和非聚集索引
	 */
	public boolean addPrimaryKey(String pkName, String tableName,
			String fieldName, boolean isClustered) {
		String sql = "";
		String cluster = "";
		DayoList dList = new DayoList();

		if (isClustered) {
			cluster = "CLUSTERED";
		} else {
			cluster = "NONCLUSTERED";
		}

		sql = " select * from sysobjects where "
				+ " (name = '%s' or OBJECT_NAME(parent_obj) = '%s') and xType = 'PK' ";
		sql = String.format(sql, pkName, tableName);
		dList = queryForDayoList(sql);

		if (dList.size() > 0) {
			return true;
		}

		sql = " alter table %s add constraint %s primary key %s (%s) ";
		sql = String.format(sql, tableName, pkName, cluster, fieldName);

		return execute(sql);
	}	
	
	
	public boolean addRelation(String fkName, String masterTable,
			String refTable, String masterKeys, String refKeys, int relaType) {
		String sql = " select * from dbo.sysobjects "
				+ " where id = object_id(N'%s') and OBJECTPROPERTY(id, N'IsForeignKey') = 1 ";
		sql = String.format(sql, fkName);
		if (isEmpty(sql)) {
			sql = " alter table %s with nocheck add constraint %s foreign key (%s) "
					+ " references %s(%s) NOT FOR REPLICATION";
			sql = String.format(sql, refTable, fkName, refKeys,	masterTable, masterKeys);
			if (!execute(sql)) {
				return false;
			}

			if (relaType == 0) {
				sql = " alter table %s NOCHECK constraint %s ";
				sql = String.format(sql, refTable, fkName);
				return execute(sql);
			}
		} 		
		return true;
	}

	public int getLastInc() {
		String sql = "select @@identity as FID";
		return getFldInteger(sql);
	}	
	
	public int getFldInteger(String sql) {		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		rs.first();		
		return rs.getInt(1);
	}
	
	public String getFldString(String sql) {		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		rs.first();		
		return rs.getString(1);
	}	
	
	public Long getFldLong(String sql) {		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		rs.first();		
		return rs.getLong(1);
	}		
	
	public String getAppCode(String appKey) {		
		String sql = "select FMerCode from tblMerchantCode where FMerID = '%s'";
		sql = String.format(sql, appKey);

		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		
		rs.first();		
		return rs.getString(1);
		/*
		DayoList list = rsToList(rs);
		String code = list.get(0).get("FMerCode".toLowerCase()).toString();
		System.out.print(code);
		
		return code;// rs.getString(0);		
		*/	
	}
	
	public static DayoList rsToList(SqlRowSet rs) {
		return rsToList(rs, 0, -1, true);	
	}
	
	public DayoList queryForDayoList(String sql) {
		return rsToList(jdbcTemplate.queryForRowSet(sql));
	}
	
	public static DayoList rsToList(SqlRowSet rs, int start, int number,
			boolean toLowerCase) {
		SqlRowSetMetaData rsm = rs.getMetaData(); // 获得列集
		int col = rsm.getColumnCount(); // 获得列的个数
		String colName[] = new String[col];
		// 取结果集中的表头名称, 放在colName数组中
		for (int i = 0; i < col; i++) {
			colName[i] = rsm.getColumnName(i + 1);
		}
		DayoList array = new DayoList();
		// 重置结果集的光标位置
		rs.beforeFirst();
		int row = 0;
		//rs.absolute(start);//跳转到start所在行
		while (rs.next()) {
			if (row < start) {
				row++;
				continue;
			} else {
				DayoMap rowMap = new DayoMap();
				String fname = "";
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					fname = rsm.getColumnName(i);
					if (!fname.equals("_parentId") && toLowerCase) {
						fname = fname.toLowerCase();// 转换为小写
					}
					if (rs.getObject(fname) != null) {
						// TODO: 暂未完善 - 可根据需要添加数据类型
						switch (rsm.getColumnType(i)) {
						case Types.DATE:// 91
						case Types.TIME:// 92
							rowMap.put(fname, rs.getTimestamp(fname).toString());
							break;
						case Types.TIMESTAMP:// 93
							Timestamp datetime = rs.getTimestamp(fname);
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String reTime = format.format(datetime);
							rowMap.put(fname, reTime);
							break;
						default:
							rowMap.put(fname, rs.getObject(fname));
							break;
						}
					} else {
						rowMap.put(fname, "");
					}
				}
				array.add(rowMap);
				if ((number >= 0) && (array.size() >= number)) {
					break;
				}
			}
		}
		return array;
	}
	
	public String getPageSql(String select, String from, String where, String order, 
			int pageNo, int pageSize) {
		String where1 = where;
		if (StringUtil.isEmpty(where1)) {
			where1 = "1=1";
		}
		
		int newPageSize = pageSize;
		if(newPageSize < 0) {
			newPageSize = 1;
		}else if(newPageSize > 200) {
			newPageSize = 200;
		}
		
		String where2 = String.format("%s not in (select top %d %s from %s where %s order by %s)", 
				order, (pageNo-1)*newPageSize, order, from, where1, order);		
		
		return String.format("select top %d %s from %s where %s and %s order by %s", 
				newPageSize, select, from, where1, where2, order);
	}
	


}