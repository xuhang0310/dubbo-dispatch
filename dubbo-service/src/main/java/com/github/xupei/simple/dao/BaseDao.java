package com.github.xupei.simple.dao;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.xupei.simple.util.SqlParser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.github.xupei.simple.util.SqlParser;

@Repository
public class BaseDao {
	
	@Resource
	protected JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> findAll(String sql) throws Exception{
		
		
		
		return findAll( sql,new HashMap());
		
	}
	
	public List<Map<String,Object>> findAll(String sql,Map<String,String> paramsMap) throws Exception{
		paramsMap= SqlParser.escape4select(paramsMap);
		sql = SqlParser.parse(sql, paramsMap);
		return this.jdbcTemplate.queryForList(sql);
		
	}
	
	public Map<String,Object> findMap(String sql) throws Exception{
		
		return findMap( sql,new HashMap());
		
	}
	
	public Map<String,Object> findMap(String sql,Map<String,String> paramsMap) throws Exception{
		paramsMap=SqlParser.escape4select(paramsMap);
		sql = SqlParser.parse(sql, paramsMap);
		return this.jdbcTemplate.queryForMap(sql);
	}
	
	public Map queryGridList(List list){
		Map gridMap=new HashMap();
		gridMap.put("total", list.size());
		gridMap.put("rows",  list); 
		return gridMap;
	}
	

	public Map queryGridList(String sql, Map paramsMap) throws Exception {
		paramsMap = SqlParser.escape4select(paramsMap);
		sql = SqlParser.parse(sql, paramsMap);
		String coutSql="select count(1)  from ("+ sql + ") temp";
		String gridSql=mysqlGetSql(sql, Integer.parseInt(paramsMap.get("page").toString()), Integer.parseInt(paramsMap.get("rows").toString())); 
		int count=	this.queryForInt( coutSql); 
		Map gridMap=new HashMap();
		gridMap.put("total", count);
		gridMap.put("rows",  jdbcTemplate.queryForList(gridSql)); 
		return gridMap;
	}
	
	public Map queryGridListNoPage(String sql, Map paramsMap) throws Exception {
		paramsMap = SqlParser.escape4select(paramsMap);
		sql = SqlParser.parse(sql, paramsMap);
		String coutSql="select count(1)  from ("+ sql + ")   temp";
		String gridSql=sql;
		int count=	this.queryForInt( coutSql); 
		Map gridMap=new HashMap();
		gridMap.put("total", count);
		gridMap.put("rows",  jdbcTemplate.queryForList(gridSql)); 
		return gridMap;
	}
	
	public String mysqlGetSql(String oldSqlStr, int pageNo, int pageSize){
		int low =  (pageNo - 1) * pageSize + 1;
		int up = pageNo * pageSize;
		String sql=oldSqlStr+" limit "+low+" , "+up;
		return sql;
	}
	
	public String oracleGetSql(String oldSqlStr, int pageNo, int pageSize){
		int low =  (pageNo - 1) * pageSize + 1;
		int up = pageNo * pageSize;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM(SELECT A.*, rownum as rid FROM( ");
		sb.append(oldSqlStr);
		sb.append((new StringBuilder()).append(") A WHERE rownum<=").append(up).append(") WHERE rid >=").append(low).toString());
		return sb.toString();
	}
	
	
	
	
	public int queryForInt(String sql, Map paramsMap) throws Exception {
		if(paramsMap!=null){
			paramsMap = SqlParser.escape4select(paramsMap);
			sql = SqlParser.parse(sql, paramsMap);
		}
		sql = SqlParser.parse(sql, paramsMap);
		return this.queryForInt(sql) ;
	}
	
	public int queryForInt(String sql)throws Exception{
		return jdbcTemplate.queryForObject(sql, Integer.class) ;
	}
	
	public String queryForString(String sql){
		return jdbcTemplate.queryForObject(sql, String.class);
	}
	
	public Object findOneObject(String sql,Class clz) throws Exception{
		List list=this.findAllObject(sql, clz);
		Object obj=null;
		if(list.size()==1){
			 obj=list.get(0);
		}
		return obj;
	}
	public Object findOneObject(String sql,Map paramsMap ,Class clz) throws Exception{
		List list=this.findAllObject(sql,paramsMap,clz);
		Object obj=null;
		if(list.size()==1){
			 obj=list.get(0);
		}
		return obj;
	}
	
	public List findAllObject(String sql,Class clz)throws Exception{
		return this.findAllObject(sql, new HashMap(), clz);
		//ThreadPoolExecutor
	}
	
	 public String getDataBaseType() throws SQLException{
	    	DatabaseMetaData md = this.jdbcTemplate.getDataSource().getConnection().getMetaData();  
	        System.out.println(md.getDatabaseProductName());  
	        return md.getDatabaseProductName();
		}
	
	public List findAllObject(String sql,Map paramsMap ,Class classObj) throws Exception {
		// TODO Auto-generated method stub
		if(paramsMap!=null){
			paramsMap = SqlParser.escape4select(paramsMap);
			sql = SqlParser.parse(sql, paramsMap);
		}
		sql = SqlParser.parse(sql, paramsMap);
		List list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(classObj));
		return list;
		
	}
	
	public void addObject(String sql) {
		jdbcTemplate.execute(sql);
	}
	
	public String addObjectReturn(String sql){
		String mgs="";
		try {
			jdbcTemplate.execute(sql);
			mgs= "保存成功";
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			mgs=e.getMessage();
		}
		return mgs;
	}
	
	public void execute(String sql) throws Exception{
		jdbcTemplate.execute(sql);
	}
	
	public void execute(String sql,Map paramsMap) throws Exception{
		if(paramsMap!=null){
			paramsMap = SqlParser.escape4select(paramsMap);
			sql = SqlParser.parse(sql, paramsMap);
		}
		sql = SqlParser.parse(sql, paramsMap);
		jdbcTemplate.execute(sql);
	}
	public void deleteObject(String sql,Map paramsMap) throws Exception{
		if(paramsMap!=null){
			paramsMap = SqlParser.escape4select(paramsMap);
			sql = SqlParser.parse(sql, paramsMap);
		}
		jdbcTemplate.execute(sql);
	}
	
	public void deleteObject(String sql){
		jdbcTemplate.execute(sql);
	}

	public void executeProcedure(String param){
		//jdbcTemplate.execute("call test1('4','5','6')");
		jdbcTemplate.execute(param);
	}
	
	
}
