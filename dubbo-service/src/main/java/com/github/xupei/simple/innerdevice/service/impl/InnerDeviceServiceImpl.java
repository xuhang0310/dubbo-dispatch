package com.github.xupei.simple.innerdevice.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.dubbo.api.innerDevice.IInnerDeviceService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class InnerDeviceServiceImpl extends GridSqlUtilTool  implements IInnerDeviceService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public String getSql(Map<String, String> paramMap) {
		// gldw=13, sbmc=的发送到}
		 StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("  select t.id,t.code,t.id note1,"); 
		 sBuffer.append("   b.NODENAME mc,"); 
		 sBuffer.append("   t.name,"); 
		 sBuffer.append("   t.ggxh,"); 
		 sBuffer.append("   t.sl,"); 
		 sBuffer.append("   t.dw,"); 
		 sBuffer.append("   org.orgname gldw,"); 
		 sBuffer.append("   (select name from bas_powertype"); 
		 sBuffer.append("     where id = t.sslx) as sslx,"); 
		 sBuffer.append("   t.zzcj,"); 
		 sBuffer.append("   t.cfdd,"); 
		 sBuffer.append("   to_char(t.rjtsj, 'YYYY-MM-DD') rjtsj,"); 
		 sBuffer.append("   to_char(t.rcsj, 'YYYY-MM-DD') rcsj,"); 
		 sBuffer.append("   t.sbyz,"); 
		 sBuffer.append("   t.shry"); 
		 sBuffer.append("   from NODE_ATTACH t, BAS_NODE b,sys_org org"); 
		 sBuffer.append("   where t.code = b.NODECODE and t.gldw=org.orgid"); 
		 sBuffer.append(" [and org.orgid='{gldw}' ]");
		 sBuffer.append(" [and t.name like '%{sbmc}%' ]");
		 sBuffer.append(" [and t.code in ({codes}) ]");
		// sBuffer.append("   and t.type = 1 order by t.id desc ");
		 sBuffer.append(" and t.type ='"+paramMap.get("type")+"'  order by t.id desc");
		return sBuffer.toString();
	}

	
	public Map getInnerDeviceList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql, paramMap);
	}

	
	
	
	

	@Override
	public void saveDevice(Map<String, String> paramMap) throws Exception {
		String sql="insert into node_attach(gldw,code,name,ggxh,zzcj,cfdd,rjtsj,rcsj,sbyz,sslx,shry,JYGS,YTLX,SBCS,CCSJ,CCBH,SL,dw,type)values('"+
				paramMap.get("orgid")+"','"+paramMap.get("hrz")+"','"+paramMap.get("sbmc")+"','"+paramMap.get("ggxh")+"','"+paramMap.get("zzcj")+
				"','"+paramMap.get("cfdd")+"',"+"to_date('"+paramMap.get("azsj")+"','yyyy-mm-dd'),"+"to_date('"+paramMap.get("rcsj")+"','yyyy-mm-dd'),'"+
				paramMap.get("sbyz")+"','"+paramMap.get("sblx")+"','"+paramMap.get("shry")+"','"+paramMap.get("jygs")+"','"+paramMap.get("ytlx")+"','"+
				paramMap.get("sbcs")+"',to_date('"+paramMap.get("ccrq")+"','yyyy-mm-dd'),'"+paramMap.get("ccbh")+"','"+paramMap.get("sl")+"','"+
				paramMap.get("dw")+"','"+paramMap.get("type")+"')";
		baseDao.addObject(sql);
	}
	
	
	public void deleteDevice(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from node_attach where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	



	@Override
	public List findDevice(String id) throws Exception {
		String sql="select id,gldw,code,name,ggxh,zzcj,cfdd,to_char(rjtsj,'yyyy-mm-dd')azsj,to_char(rcsj,'yyyy-mm-dd')rcsj, "+
				"sbyz,sslx,shry,JYGS,YTLX,SBCS,to_char(CCSJ,'yyyy-mm-dd')ccsj,CCBH,SL,dw,type from node_attach "+
			" where id= "+id; 
		
		return baseDao.findAll(sql);
	}

	@Override
	public void updateDevice(Map<String, String> paramsMap) throws Exception {
		String sql="update node_attach set gldw='"+paramsMap.get("orgid")+"',code='"+paramsMap.get("hrz")+"',name='"+paramsMap.get("sbmc")+
				"',ggxh='"+paramsMap.get("ggxh")+"',zzcj='"+paramsMap.get("zzcj")+"',cfdd='"+paramsMap.get("cfdd")+"',rjtsj=to_date('"+paramsMap.get("azsj")+
				"','yyyy-mm-dd'),rcsj=to_date('"+paramsMap.get("rcsj")+"','yyyy-mm-dd'),sbyz='"+paramsMap.get("sbyz")+"',sslx='"+paramsMap.get("sblx")+
				"',shry='"+paramsMap.get("shry")+"',jygs='"+paramsMap.get("jygs")+"',YTLX='"+paramsMap.get("ytlx")+"',SBCS='"+paramsMap.get("sbcs")+
				"',CCSJ=to_date('"+paramsMap.get("ccrq")+"','yyyy-mm-dd'),CCBH='"+paramsMap.get("ccbh")+"',sl='"+paramsMap.get("sl")+"',dw='"+
				paramsMap.get("dw")+"',type='"+paramsMap.get("type")+"'"+
				" where id='"+paramsMap.get("deviceid")+"'";
						
	baseDao.execute(sql);
		
	}

	

	@Override
	public List selectAll() throws Exception {
		String sql = "SELECT FEEDNAME as NAME FROM BAS_FEED  UNION ALL SELECT NODENAME as NAME FROM BAS_NODE ";

		return baseDao.findAll(sql);
	}

	@Override
	public List getProjectList(String id) throws Exception {
		String sql = "";
		if(id!=null&&id.trim().length()>0){
			switch (id) {
			case "1":
				sql = "SELECT T.NODECODE AS ID,T.NODENAME AS NAME FROM BAS_NODE T  where 1=1 ";
				break;
			case "99":
				sql = "SELECT T.FEEDCODE AS ID,T.FEEDNAME AS NAME FROM BAS_FEED T   where 1=1 ";
				break;
			default:
				break;
			}
		}
		return baseDao.findAll(sql);
	}


	@Override
	public List getOrg() throws Exception {
		String sql="select orgid,orgname from sys_org";
		return baseDao.findAll(sql);
	}


	@Override
	public List getSblx() throws Exception {
		String sql="select id,name from bas_powertype t where t.pid=46 order by id";
		return baseDao.findAll(sql);
	}


	@Override
	public List getNode(String type) throws Exception {
		StringBuilder sBuilder=new StringBuilder();
		if(type.equals("1")){
			sBuilder.append("select nodecode code,nodename name from bas_node");
		}else{
			sBuilder.append("select feedcode code,feedname name from bas_feed");
			
		}
		return baseDao.findAll(sBuilder.toString());
	}




	


}

