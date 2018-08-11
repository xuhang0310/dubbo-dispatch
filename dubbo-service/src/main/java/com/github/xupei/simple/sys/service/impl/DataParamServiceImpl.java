package com.github.xupei.simple.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IDataParamService;
import com.github.xupei.simple.dao.BaseDao;






@Service
public class DataParamServiceImpl implements IDataParamService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public Map getDataParamList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String cnq=paramMap.get("cnq");
	   String sql="select ID,SCHEDULER," +
			"   to_char(STARTDATE,'yyyy-mm-dd') STARTDATE,"+
		     "  to_char(ENDDATE,'yyyy-mm-dd') ENDDATE, "+
	   		"INDESIGNTEMP,OUTLOWTEMP,"+
			   			"THEORYHEATINDEX, GNJSTEMP, " +
			   			"decode(CURRSCHEDULER,1,'是','否') CURRSCHEDULER," +
			   			"REMARK,id note1"+
			   			" from data_param ";
		if(!cnq.isEmpty()){
			sql+="where SCHEDULER like'%"+cnq+"%'";
		}	          
		paramMap=new HashMap<String, String>();
		paramMap.put("page","1");
		paramMap.put("rows","20");
		return baseDao.queryGridList(sql,paramMap);
	}


	
	
	

	@Override
	public void saveParam(Map<String, String> paramMap) throws Exception {
	String sql="insert into data_param(scheduler,startdate,enddate,theoryheatindex,indesigntemp,outlowtemp,currscheduler,gnjstemp,remark)values('"+
			paramMap.get("cnqname")+"',to_date('"+paramMap.get("datemin")+"','yyyy-mm-dd'),"+
			"to_date('"+paramMap.get("datemax")+"','yyyy-mm-dd'),'"+paramMap.get("llrzb")+"','"+
			paramMap.get("snwd")+"','"+paramMap.get("swwd")+"','"+paramMap.get("cnq")+"','"+paramMap.get("jswd")+"','"+paramMap.get("bz")+"')";
	
		   baseDao.addObject(sql);
	}
	
	
	public void deleteParam(String id) throws Exception {
		String sql="delete from data_param where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	



	@Override
	public List findParam(String id) throws Exception {
		String sql="select t.id,t.scheduler,to_char(t.startdate,'yyyy-mm-dd') kssj,to_char(t.enddate,'yyyy-mm-dd') jssj, "+
			"	t.theoryheatindex,t.indesigntemp,t.outlowtemp,t.currscheduler,t.gnjstemp,t.remark  "+
			"	from data_param t  where id= "+id; 
		
		return baseDao.findAll(sql);
	}

	@Override
	public void editParam(Map<String, String> paramsMap) throws Exception {
		String sql="update data_param t set t.scheduler='"+paramsMap.get("cnqname")+"',t.startdate=to_date('"+paramsMap.get("datemin")+
				"','yyyy-mm-dd'),t.enddate=to_date('"+paramsMap.get("datemax")+"','yyyy-mm-dd'),t.theoryheatindex='"+paramsMap.get("llrzb")+
				"',t.indesigntemp='"+paramsMap.get("snwd")+"',t.outlowtemp='"+paramsMap.get("swwd")+"',t.currscheduler="+paramsMap.get("cnq")+
				",t.remark='"+paramsMap.get("bz")+"'  where t.id="+paramsMap.get("id");
		baseDao.execute(sql);
		
	}

	@Override
	public List getlx() throws Exception {
		List list=new ArrayList<>();
		Map map1=new HashMap<>();
		Map map2=new HashMap<>();
		map1.put("lxm", 1);
		map1.put("nd", "换热站");
		map2.put("lxm", 2);
		map2.put("nd", "热源");
		list.add(map1);
		list.add(map2);
		return list;
	}



	


}

