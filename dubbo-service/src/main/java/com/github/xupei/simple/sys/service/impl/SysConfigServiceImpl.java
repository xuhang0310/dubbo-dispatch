package com.github.xupei.simple.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.dubbo.api.ISysConfigService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class SysConfigServiceImpl extends GridSqlUtilTool  implements ISysConfigService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public String getSql(Map<String, String> paramMap) {
		String sql = "select id,pageid,fieldname,ishiddle,orderid,renderer,caption,unit,width,dataformat,isprimary,ischart,id note1 from "+
			"	col_config_page " +
			"where 1=1 "+
			"[and pageid={id}]"+
			"[and pageid={pageid}]"+
			"order by id ";
		return sql;
	}

	
	public Map getSysConfigList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql, paramMap);
	}

	public List checkUser(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select su.userid,su.orgid,su.password,su.displayname from sys_user su where 1=1   [  and username='{userName}']  ";
		return baseDao.findAll(sql, map);
	}

	
	
	

	@Override
	public void savePage(Map<String, String> paramMap) throws Exception {
	//	{pages=, pid=12345, filedname=ddsfsdf, ishiddle=false, ORDERID=22, 
//				RENDERER=dsf, CAPTION=ddd, UNIT=2, WIDTH=222, DATAFORMAT=33, ISPRIMARY=1, ISCHART=1}
		String sql=" insert into col_config_page(pageid,fieldname,ishiddle,orderid,renderer,caption,unit,width,dataformat,isprimary,ischart) values('"+
					paramMap.get("pid")+"','"+paramMap.get("filedname")+"','"+paramMap.get("ishiddle")+"','"+paramMap.get("ORDERID")+"','"+
					paramMap.get("RENDERER")+"','"+paramMap.get("CAPTION")+"','"+paramMap.get("UNIT")+"','"+paramMap.get("WIDTH")+"','"+
					paramMap.get("DATAFORMAT")+"','"+paramMap.get("ISPRIMARY")+"','"+paramMap.get("ISCHART")+"')";
		baseDao.addObject(sql);
	}
	
	
	public void deletePage(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from col_config_page where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	




	@Override
	public List findPage(String id) throws Exception {
		String sql="select t.id,t.pageid,t.fieldname,decode(ishiddle,'false',2,1) ishiddle,t.orderid,t.renderer,t.caption, "+
						"t.unit,t.width,t.dataformat,t.isprimary,t.ischart "+
				" from col_config_page t "+
				" where t.id='"+id+"'";
		
		return baseDao.findAll(sql);
	}

	@Override
	public void editPage(Map<String, String> paramsMap) throws Exception {
		String sql= "update col_config_page t "+
				 "set t.pageid='"+paramsMap.get("pid")+"',t.fieldname='"+paramsMap.get("filedname")+"',"+
				"t.ishiddle='"+paramsMap.get("ishiddle")+"',t.orderid='"+paramsMap.get("ORDERID")+"',"+
				 "t.renderer='"+paramsMap.get("RENDERER")+"',t.caption='"+paramsMap.get("CAPTION")+"',"+
				"t.unit='"+paramsMap.get("UNIT")+"',t.width='"+paramsMap.get("WIDTH")+"',t.dataformat='"+
				 paramsMap.get("DATAFORMAT")+"',t.isprimary='"+paramsMap.get("ISPRIMARY")+"',t.ischart='"+
				paramsMap.get("ISCHART")+"'"+
				"where t.id='"+paramsMap.get("pages")+"'";
			  baseDao.execute(sql);
	}



	@Override
	public List getMenuListForTree(Map<String, String> paramsMap)
			throws Exception {
		String sql="select t.menuid \"id\",t.parentid \"pid\",t.menuname \"title\" from sys_menu t "+
						"where t.del=1 " +
						"and t.menulevel<>3" +
						" order by t.menuid";
		return baseDao.findAll(sql);
	}




	


}

