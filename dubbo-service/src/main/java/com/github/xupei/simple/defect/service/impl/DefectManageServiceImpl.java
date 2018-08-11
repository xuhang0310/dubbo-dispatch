package com.github.xupei.simple.defect.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.p;

import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.dubbo.api.defect.IDefectManageService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class DefectManageServiceImpl extends GridSqlUtilTool  implements IDefectManageService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public String getSql(Map<String, String> paramMap) {
		//{status=3, rows=20, page=1, kssj=2018-06-13, jssj=2018-06-14, qxlx=27, org=11}
		String sql =" select ID, "+
				"ID note1,"+
	            "decode(STATUS,0,'待消缺',1,'消缺中',2,'已消缺','草稿')status,"+
	            "  CANEDIT,"+
	            "   CREATEDATE,"+
	            "   DEFECTDATE,"+
	            "   NAME,"+
	            "   ORGNAME,"+
	            "   TYPENAME,"+
	            "  SUBMITUNAME,"+
	            "  ESTDATE,"+
	            " INFAREA,"+
	            "  INFHHS,"+
	            "     STARTDEALDATE,"+
	            " ENDDEALDATE,"+
	            "   DEALUNAME,"+
	            "  CHECKUNAME"+
	          /*  "    REPORT"+*/
	            "    from (select d.id,"+
	            "    d.status,"+
	            "    to_char(d.createdate, 'yyyy-mm-dd hh24:mi:ss') createdate,"+
	            "      to_char(d.defectdate, 'yyyy-mm-dd hh24:mi:ss') defectdate,"+
	            "     d.name,"+
	            "     dt.name typename,"+
	            "     su.displayname submituname,"+
	            "     to_char(d.estdate, 'yyyy-mm-dd hh24:mi:ss') estdate,"+
	            "     d.infarea,"+
	            "       d.infhhs,"+
	            "     org.orgname, "+
	            "    to_char(d.startdealdate, 'yyyy-mm-dd hh24:mi:ss') startdealdate,"+
	            "    du.displayname dealuname,"+
	            "     to_char(d.enddealdate, 'yyyy-mm-dd hh24:mi:ss') enddealdate,"+
	            "     cu.displayname checkuname,"+
	          /*  "      '报表' report,"+
	            "    '详情' detail,"+*/
	            "     case"+
	            "        when canedit = 'T' then"+
	            "         '是'"+
	            "       else "+
	            "       '否'  "+
	            "     end as canedit "+
	            "   from bas_defect d    "+
	            "    left join (select * from BAS_POWERTYPE where pid=25) dt   "+
	            "    on (d.typeid = dt.id) "+
	            "  left join sys_user su "+
	            "       on (d.submituid = su.userid)  "+
	            "      left join sys_user du "+
	            "     on (d.dealuid = du.userid) "+
	            "    left join sys_user cu "+
	            "   on (d.checkuid = cu.userid) "+
	            "   left join sys_org org "+
	            "   on (org.orgid = d.orgid) "+
	            "  where 1 = 1 "+
	      //  	+"[ and t.stationcode='{mc}']" 
	            " [and d.typeid='{qxlx}'] "+
	            " [and d.orgid='{org}'] "+
	            "[and to_char(d.defectdate,'yyyy-mm-dd')>='{kssj}']"+
	            "[and to_char(d.defectdate,'yyyy-mm-dd')<='{jssj}']"+
	            "      and d.status = '"+paramMap.get("status")+"'"+                              
	            "    order by d.defectdate desc) ";

		return sql;
	}

	
	public Map getDefectList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql=getSql(paramMap);
		
		return baseDao.queryGridList(sql, paramMap);
	}


	
	
	

	@Override
	public void saveDefect(Map<String, String> paramMap,String userid,String orgid,String filename) throws Exception {
	String sql="insert into bas_defect(name,createdate,defectdate,typeid,position,message,submituid,status,orgid,fileurl) values('"+
				paramMap.get("qxmc")+"',sysdate,to_date('"+paramMap.get("fssj")+"','yyyy-mm-dd hh24:mi:ss'),'"+paramMap.get("qxlx")+
				"','"+paramMap.get("fsdd")+"','"+paramMap.get("bz")+"','"+userid+"','"+paramMap.get("status")+"','"+orgid+"',"+"'"+filename+   "')";
		baseDao.addObject(sql);
	}
	
	
	public void deleteDefect(String id) throws Exception {
		String sql="delete from bas_defect where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	




	@Override
	public List findDefect(String id) throws Exception {
		String sql="select  id,name,to_char(createdate,'yyyy-mm-dd hh24:mi:ss')createdate," +
				"to_char(defectdate,'yyyy-mm-dd hh24:mi:ss')defectdate,typeid,position,message,submituid,status,orgid from bas_defect "+
			" where id= "+id; 
		
		return baseDao.findAll(sql);
	}

	@Override
	public void updateDefect(Map<String, String> paramsMap,String filename) throws Exception {
		String sql="";
		if(filename.equals("")){
		 sql="update bas_defect set name='"+paramsMap.get("qxmc")+
						"',defectdate=to_date('"+paramsMap.get("fssj")+"','yyyy-mm-dd hh24:mi:ss'),typeid='"+
						paramsMap.get("qxlx")+"',position='"+paramsMap.get("fsdd")+"',message='"+
						paramsMap.get("bz")+"',status='"+paramsMap.get("status")+"'  where id='"+paramsMap.get("defectId")+"'";
		}else{
			sql="update bas_defect set name='"+paramsMap.get("qxmc")+
					"',defectdate=to_date('"+paramsMap.get("fssj")+"','yyyy-mm-dd hh24:mi:ss'),typeid='"+
					paramsMap.get("qxlx")+"',position='"+paramsMap.get("fsdd")+"',message='"+
					paramsMap.get("bz")+"',fileurl='"+filename+"',status='"+paramsMap.get("status")+"' where id='"+paramsMap.get("defectId")+"'";
			
		}		
						
		baseDao.execute(sql);
		
	}

	@Override
	public List getlx(String stationtype) throws Exception {
		String sql;
		if(stationtype.equals("1")){
			sql="SELECT NODENAME as NAME, NODECODE as stationcode FROM BAS_NODE";
		}else if((stationtype.equals("99"))){
			sql="SELECT FEEDNAME as NAME, FEEDCODE as stationcode FROM BAS_FEED";
		}else{
			sql="SELECT FEEDNAME as NAME, FEEDCODE as stationcode FROM BAS_FEED "+
					"  UNION ALL"+
					"  SELECT NODENAME as NAME, NODECODE as stationcode FROM BAS_NODE ";
		}
		return baseDao.findAll(sql);
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
	public Map getDefectTypeList(Map<String, String> paramsMap)
			throws Exception {
		String defectname=paramsMap.get("defectname");
		String sql="select id ,name,decode(del,'0','未启用','1','启用','未启用') del,id note1 from BAS_DEFECTTYPE ";
		if(!defectname.equals("")){
			sql+=" where name like'%"+defectname+"%'";
		}
		sql+=" order by id";
	  return  baseDao.queryGridList(sql, paramsMap);
	}


	@Override
	public void addDefectType(Map<String, String> paramsMap) throws Exception {
		String sql=" insert into BAS_DEFECTTYPE(id,name,del) values"+
						"((select max(id)+1 from BAS_DEFECTTYPE),'"+paramsMap.get("qxmc")+"','"+paramsMap.get("del")+"')";
		baseDao.addObject(sql);
		
	}


	@Override
	public void deleteDefectType(String id) throws Exception {
		String sql="delete from BAS_DEFECTTYPE where id="+id;
		baseDao.execute(sql);
	}


	@Override
	public List selectAllDefect(Map<String, String> paramsMap) throws Exception {
		String sql="select * from BAS_DEFECTTYPE where id='"+paramsMap.get("id")+"'";
		return baseDao.findAll(sql);
	}


	@Override
	public void updateDefectType(Map<String, String> paramsMap)throws Exception {
		String sql="update bas_defecttype set name='"+paramsMap.get("qxmc")+"',del='"+
					paramsMap.get("del")+"'  where id='"+paramsMap.get("defectid")+"'";
		baseDao.execute(sql);
		
		
	}


	@Override
	public List getDefectType() throws Exception {
		String sql="select * from BAS_POWERTYPE where pid=(select id from BAS_POWERTYPE where name like'%缺陷类型%'and pid=0 )";
		
		return baseDao.findAll(sql);
	}


	@Override
	public String getOrgId(String username) throws Exception {
		String sql="select orgid from sys_user where username='"+username+"'";
		return baseDao.queryForString(sql);
	}


	@Override
	public String getUid(String username) throws Exception {
		String sql="select userid from sys_user where username='"+username+"'";
		return baseDao.queryForString(sql);
	}


	@Override
	public List findDefectDeal(String id) throws Exception {
		String sql="select  d.id,d.name,to_char(d.createdate,'yyyy-mm-dd hh24:mi:ss')createdate,  d.infarea, d.infhhs, "+
				" to_char(d.estdate, 'yyyy-mm-dd hh24:mi:ss') estdate,  to_char(d.startdealdate, 'yyyy-mm-dd hh24:mi:ss') startdealdate, "+
				" nvl(d.fileurl, '') fileurl,d.infgj,d.infmy,to_char(d.enddealdate, 'yyyy-mm-dd hh24:mi:ss') enddealdate, d.dealinfo, "+
				" to_char(d.defectdate,'yyyy-mm-dd hh24:mi:ss')defectdate,d.typeid,d.position,d.message,d.submituid, "+
			"	d.dealuid,d.checkuid, d.status,d.orgid ,org.orgname  "+

			"	from bas_defect d,sys_org org "+
			"	where d.orgid=org.orgid  and d.id="+id;
				
		return baseDao.findAll(sql);
	}


	@Override
	public List getTjr(String id) throws Exception { 
		String sql="select u.displayname from sys_user u,bas_defect b "+
						"where u.userid=b.submituid and b.id="+id;
		return baseDao.findAll(sql);
	}


	@Override
	public void dealDefect(Map<String, String> paramsMap,String userid) throws Exception {
		String sql="update bas_defect set startdealdate=to_date('"+paramsMap.get("qxkssj")+"','yyyy-mm-dd hh24:mi:ss'),estdate=to_date('"+paramsMap.get("yjwcsj")+
				"','yyyy-mm-dd hh24:mi:ss'),infarea="+paramsMap.get("yxmj")+",infhhs="+paramsMap.get("yxhs")+",infgj='"+paramsMap.get("infgj")+
				"',infmy='"+paramsMap.get("infmy")+"',status="+paramsMap.get("status");
		if(paramsMap.get("status").equals("1")){
			sql+=",dealuid="+userid;
		}			
		sql+=" where id="+paramsMap.get("defectId");
		baseDao.execute(sql);			
	}


	@Override
	public List getDealMan(String id) throws Exception {
		String sql="select u.displayname dealman from bas_defect d,sys_user u "+
				"where u.userid=d.dealuid "+
			"	and d.id="+id;
		return baseDao.findAll(sql);
	}


	@Override
	public void solveDefect(Map<String, String> paramsMap, String userid)
			throws Exception {
		String sql="update bas_defect t set t.enddealdate=to_date('"+paramsMap.get("qxjssj")+"','yyyy-mm-dd hh24:mi:ss'),"+
				"t.dealinfo='"+paramsMap.get("dealinfo")+"',t.status="+paramsMap.get("status");
		if(paramsMap.get("status").equals("2")){
			sql+=",t.checkuid="+userid+",t.enddate=sysdate";
		}
		sql+="  where id="+paramsMap.get("defectId");
		
		baseDao.execute(sql);
	}


	@Override
	public List getOrgName() throws Exception {
		String sql="select orgid,orgname from sys_org";
		return baseDao.findAll(sql);
	}




	


}

