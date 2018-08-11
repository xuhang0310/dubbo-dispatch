package com.github.xupei.simple.bas.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.xupei.dubbo.api.INodeService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.util.UUIDTool;

public class NodeServiceImpl implements INodeService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	public String getSql(Map<String, String> paramMap) {
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append(" select t.nodeid, t.nodename,s.orgname,f.feedname,t.sjfh,t.cnmj,gl.name glfs,cn.name cnfs,jn.name jnfs,p.pic_fname gytid,"); 
		 sBuffer.append("  t.nodeid code  "); 
		 sBuffer.append("  from bas_node t,picture_set p, bas_feed f, sys_org s,(select id,name from BAS_POWERTYPE t where t.pid=6)gl,"); 
		 sBuffer.append("  (select id,name from BAS_POWERTYPE t where t.pid=1)cn,(select id,name from BAS_POWERTYPE t where t.pid=36)jn"); 
		 sBuffer.append("   where 1 = 1"); 
		 sBuffer.append("   and t.orgid = s.orgid  and t.gytid=p.id(+)"); 
		 sBuffer.append("   and t.feedid = f.feedid"); 
		 sBuffer.append("  and t.glfs=gl.id(+)"); 
		 sBuffer.append("  and t.cnfs=cn.id(+)"); 
		 sBuffer.append("      and t.jnfs=jn.id(+)");
		 sBuffer.append(" [and t.orgid='{orgid}']");
		 sBuffer.append(" [and t.nodename like '%{nodename}%']");
		 sBuffer.append(" [and t.nodecode in ({nodecodes})]");
		
		return sBuffer.toString();
	}
	
	
	
	public Map getNodeList(Map<String, String> paramMap) throws Exception {
		String sql=getSql(paramMap);
		return baseDao.queryGridList(sql,paramMap);
		
	}
	
	/**
	 * 修改查询方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List editNode(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="select t.*,t.nodeid code,s.orgname,f.feedname  from bas_node t,bas_feed f,sys_org s where 1=1 and t.orgid=s.orgid and t.feedid=f.feedid  ";
		
		if (!id.isEmpty()) {
			sql +=" and t.nodeid = '"+id+"'";
		}
		
		return baseDao.findAll(sql);
		
	}

	

	/**
	 * 新增
	 */
	@Override
	public void saveNode(Map<String, String> paramMap) throws Exception {
		//换热站编码
		String nodeid=baseDao.findAll("select (max(to_number(nodeid))+1)nodeid from bas_node").get(0).get("NODEID").toString();
		//更新换热站信息到bas_node表
		String bas_node="insert into bas_node(nodeid,nodecode,nodename,orgid,feedid,glfs,cnfs,jnfs,sjfh,cnmj,gytid,fzr)values('"+
				nodeid+"','"+nodeid+"','"+paramMap.get("nodename")+"','"+paramMap.get("org")+"','"+paramMap.get("feedid")+"','"+
				paramMap.get("glfs")+"','"+paramMap.get("cnfs")+"','"+paramMap.get("jnfs")+"','"+paramMap.get("sjfh")+"','"+paramMap.get("cnmj")+
				"','"+paramMap.get("gytid")+"','"+paramMap.get("fzr")+"')";
		baseDao.execute(bas_node);
		//更新机组信息到scada_station
		if(!paramMap.get("jz1").equals("")){
			String sqljz1="  insert into scada_station(id,stationtype,stationcode,linecode,linename)values('"+UUIDTool.getUUID()+"',1,'"+
					nodeid+"',1,'"+paramMap.get("jz1")+"')";
			baseDao.execute(sqljz1);
		}		
		if(!paramMap.get("jz2").equals("")){
			String sqljz2="  insert into scada_station(id,stationtype,stationcode,linecode,linename)values('"+UUIDTool.getUUID()+"',1,'"+
					nodeid+"',2,'"+paramMap.get("jz2")+"')";
			baseDao.execute(sqljz2);
		}		
		if(!paramMap.get("jz3").equals("")){
			String sqljz3="  insert into scada_station(id,stationtype,stationcode,linecode,linename)values('"+UUIDTool.getUUID()+"',1,'"+
					nodeid+"',3,'"+paramMap.get("jz3")+"')";
			baseDao.execute(sqljz3);
		}		
		if(!paramMap.get("jz4").equals("")){
			String sqljz4="  insert into scada_station(id,stationtype,stationcode,linecode,linename)values('"+UUIDTool.getUUID()+"',1,'"+
					nodeid+"',4,'"+paramMap.get("jz4")+"')";
			baseDao.execute(sqljz4);
		}		
		
		//更新指标数据到bas_index_config
		if(!paramMap.get("rzb").equals("")){
			String rzb="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values('15','"+paramMap.get("rzb")+"','"+nodeid+"',1)";
			baseDao.execute(rzb);
		}
		if(!paramMap.get("szb").equals("")){
			String szb="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values('16','"+paramMap.get("szb")+"','"+nodeid+"',1)";
			baseDao.execute(szb);
		}
		
		if(!paramMap.get("dzb").equals("")){
			String dzb="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values('17','"+paramMap.get("dzb")+"','"+nodeid+"',1)";
			baseDao.execute(dzb);
		}
		
		
	}
	
	/**
	 * 修改
	 */
	@Override
	public void editNode(Map<String, String> paramMap) throws Exception {
		String nodeid=paramMap.get("nodeid");
		String bas_node="update bas_node set nodename='"+paramMap.get("nodename")+"',orgid='"+paramMap.get("org")+"',feedid='"+paramMap.get("feedid")+
				"',glfs='"+paramMap.get("glfs")+"',cnfs='"+paramMap.get("cnfs")+"',jnfs='"+paramMap.get("jnfs")+"',sjfh='"+paramMap.get("sjfh")+"',cnmj='"+
				paramMap.get("cnmj")+"',gytid='"+paramMap.get("gytid")+"',fzr='"+paramMap.get("fzr")+"'  where nodeid="+nodeid;
		baseDao.execute(bas_node);
		
		//更新机组信息到scada_station
			List jz1=baseDao.findAll("select t.id,t.stationcode,t.linecode,t.linename  from scada_station t  where t.stationcode="+nodeid+"  and t.linecode=1 ");
			List jz2=baseDao.findAll("select t.id,t.stationcode,t.linecode,t.linename  from scada_station t  where t.stationcode="+nodeid+"  and t.linecode=2 ");
			List jz3=baseDao.findAll("select t.id,t.stationcode,t.linecode,t.linename  from scada_station t  where t.stationcode="+nodeid+"  and t.linecode=3 ");
			List jz4=baseDao.findAll("select t.id,t.stationcode,t.linecode,t.linename  from scada_station t  where t.stationcode="+nodeid+"  and t.linecode=4 ");
			String sqljz1="";String sqljz2="";String sqljz3="";String sqljz4="";
			if(jz1.size()>0){
			 sqljz1="update scada_station set linename='"+paramMap.get("jz1")+"'  where stationcode="+nodeid+" and linecode=1 and stationtype=1";
			}else if(jz1.size()==0&&!paramMap.get("jz1").trim().equals("")){
			 sqljz1="insert into scada_station( id,stationtype,stationcode,linecode,linename)values('"+
							UUIDTool.getUUID()+"',1,"+nodeid+",1,'"+paramMap.get("jz1")+"')";	
			}
			if(!sqljz1.trim().equals("")){
			baseDao.execute(sqljz1);
			}
			if(jz2.size()>0){
			 sqljz2="update scada_station set linename='"+paramMap.get("jz2")+"'  where stationcode="+nodeid+" and linecode=2 and stationtype=1";
			}else if(jz2.size()==0&&!paramMap.get("jz2").trim().equals("")){
			sqljz2="insert into scada_station( id,stationtype,stationcode,linecode,linename)values('"+
					UUIDTool.getUUID()+"',1,"+nodeid+",2,'"+paramMap.get("jz2")+"')";	
			}
			if(!sqljz2.trim().equals("")){
			baseDao.execute(sqljz2);
			}
			
			if(jz3.size()>0){
			 sqljz3="update scada_station set linename='"+paramMap.get("jz3")+"'  where stationcode="+nodeid+" and linecode=3 and stationtype=1";
			}else if(jz3.size()==0&&!paramMap.get("jz3").trim().equals("")){
			sqljz3="insert into scada_station( id,stationtype,stationcode,linecode,linename)values('"+
					UUIDTool.getUUID()+"',1,"+nodeid+",3,'"+paramMap.get("jz3")+"')";	
			}
			if(!sqljz3.trim().equals("")){
			baseDao.execute(sqljz3);
			}
			
			if(jz4.size()>0){
			 sqljz4="update scada_station set linename='"+paramMap.get("jz3")+"'  where stationcode="+nodeid+" and linecode=4 and stationtype=1";
			}else if(jz4.size()==0&&!paramMap.get("jz4").trim().equals("")){
			sqljz4="insert into scada_station( id,stationtype,stationcode,linecode,linename)values('"+
					UUIDTool.getUUID()+"',1,"+nodeid+",4,'"+paramMap.get("jz4")+"')";	
			}
			if(!sqljz4.trim().equals("")){
			baseDao.execute(sqljz4);
			}
		
		//更新指标数据到bas_index_config

	//		String rzb="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values('15','"+paramMap.get("rzb")+"','"+nodeid+"',1)";	
		List rzb=baseDao.findAll(" select t.id,t.indextype,t.indexvalue,t.stationcode,t.stationtype from bas_index_config t "+
				"where t.stationtype=1 and t.stationcode="+nodeid+"  and t.indextype=15");	
		List szb=baseDao.findAll(" select t.id,t.indextype,t.indexvalue,t.stationcode,t.stationtype from bas_index_config t "+
				"where t.stationtype=1 and t.stationcode="+nodeid+"  and t.indextype=16");	
		List dzb=baseDao.findAll(" select t.id,t.indextype,t.indexvalue,t.stationcode,t.stationtype from bas_index_config t "+
				"where t.stationtype=1 and t.stationcode="+nodeid+"  and t.indextype=17");	
		String rzbsql="";String szbsql="";String dzbsql="";
		if(rzb.size()>0){
		 rzbsql="update bas_index_config set indexvalue='"+paramMap.get("rzb")+"' where stationcode="+nodeid+" and indextype=15 and stationtype=1";
		}else if(rzb.size()==0&&!paramMap.get("rzb").trim().equals("")){
		rzbsql="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values(15,'"+paramMap.get("rzb")+"',"+nodeid+",1)";	
		}
		if(szb.size()>0){
		 szbsql="update bas_index_config set indexvalue='"+paramMap.get("szb")+"' where stationcode="+nodeid+" and indextype=16 and stationtype=1";
		}else if(szb.size()==0&&!paramMap.get("szb").trim().equals("")){
	    szbsql="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values(16,'"+paramMap.get("szb")+"',"+nodeid+",1)";	
		}
		if(dzb.size()>0){
		 dzbsql="update bas_index_config set indexvalue='"+paramMap.get("dzb")+"' where stationcode="+nodeid+" and indextype=17 and stationtype=1";
		}else if(dzb.size()==0&&!paramMap.get("dzb").trim().equals("")){
	    dzbsql="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values(17,'"+paramMap.get("dzb")+"',"+nodeid+",1)";	
		}
		if(!rzbsql.trim().equals("")){
		baseDao.execute(rzbsql);
		}
		if(!szbsql.trim().equals("")){
		baseDao.execute(szbsql);
		}
		if(!dzbsql.trim().equals("")){
		baseDao.execute(dzbsql);
		}
	}
	
	/**
	 * 删除
	 */
	public void deleteNode(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from bas_node where nodeid='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	/**
	 * 页面 公司 下拉框
	 */
	public List orgList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.orgid,s.orgname  from sys_org s where 1=1 and del=1 ";
		
		return baseDao.findAll(sql);
		
	}
	/**
	 * 页面 热源 下拉框
	 */
	public List feedList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select s.feedid,s.feedname  from bas_feed s where 1=1 ";
		
		return baseDao.findAll(sql);
		
	}
	
	

	@Override
	public List queryNodeByPid(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="select nodecode \"id\",orgid \"pid\" ,nodename \"title\" from bas_node where 1=1 [ and  orgid={orgid} ]";
		return baseDao.findAll(sql, paramMap);
	}


	public List getDataDicList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from bas_powertype t  where t.isdel=1";
		return baseDao.findAll(sql);
	}

	@Override
	public List getSysOrg() throws Exception {
		String sql=" select t.orgid,t.orgname from sys_org t";
		return baseDao.findAll(sql);
	}

	@Override
	public List getJz(String nodeid,String linecode) throws Exception {
		String sql="select t.stationcode,t.linecode,t.linename  "+
				"from scada_station t where t.stationtype=1 and t.stationcode="+nodeid+" and t.linecode="+linecode;
		return baseDao.findAll(sql);
	}

	@Override
	public List getZb(String nodeid,String zb) throws Exception {
		String sql="select t.indextype,t.indexvalue,t.stationcode from bas_index_config t  "+
			"	where t.stationtype=1 and t.stationcode="+nodeid+"  and t.indextype="+zb;
		return baseDao.findAll(sql);
	}

	@Override
	public Object getNodeAllList(Map<String, String> paramsMap)
			throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from bas_node";
		return baseDao.findAll(sql);
	}

	@Override
	public void batchUpdateNode(Map<String, String> paramMap) throws Exception {
	String basnodesql="	update bas_node t set t.orgid='"+paramMap.get("org")+"',t.feedid='"+paramMap.get("feedid")+"',t.glfs='"+
			//	paramMap.get("glfs')+"',t.cnfs='"
			paramMap.get("glfs")+"',t.cnfs='"+paramMap.get("cnfs")+"',t.jnfs='"+paramMap.get("jnfs")+"',t.fzr='"+paramMap.get("fzr")+"'"+
			" where t.nodecode in("+paramMap.get("nodecode")+")";
			baseDao.execute(basnodesql);

		String[]nodeid=paramMap.get("nodecode").split(",");	//[3113, 3032, 3033, 3034]
		
		for(int i=0;i<nodeid.length;i++){
		//更新指标数据到bas_index_config
			List rzb=baseDao.findAll(" select t.id,t.indextype,t.indexvalue,t.stationcode,t.stationtype from bas_index_config t "+
					"where t.stationtype=1 and t.stationcode="+nodeid[i]+"  and t.indextype=15");	
			List szb=baseDao.findAll(" select t.id,t.indextype,t.indexvalue,t.stationcode,t.stationtype from bas_index_config t "+
					"where t.stationtype=1 and t.stationcode="+nodeid[i]+"  and t.indextype=16");	
			List dzb=baseDao.findAll(" select t.id,t.indextype,t.indexvalue,t.stationcode,t.stationtype from bas_index_config t "+
					"where t.stationtype=1 and t.stationcode="+nodeid[i]+"  and t.indextype=17");	
			String rzbsql="";String szbsql="";String dzbsql="";
			if(rzb.size()>0){
			 rzbsql="update bas_index_config set indexvalue='"+paramMap.get("rzb")+"' where stationcode="+nodeid[i]+" and indextype=15 and stationtype=1";
			}else if(rzb.size()==0&&!paramMap.get("rzb").trim().equals("")){
			rzbsql="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values(15,'"+paramMap.get("rzb")+"',"+nodeid[i]+",1)";	
			}
			if(szb.size()>0){
			 szbsql="update bas_index_config set indexvalue='"+paramMap.get("szb")+"' where stationcode="+nodeid[i]+" and indextype=16 and stationtype=1";
			}else if(szb.size()==0&&!paramMap.get("szb").trim().equals("")){
		    szbsql="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values(16,'"+paramMap.get("szb")+"',"+nodeid[i]+",1)";	
			}
			if(dzb.size()>0){
			 dzbsql="update bas_index_config set indexvalue='"+paramMap.get("dzb")+"' where stationcode="+nodeid[i]+" and indextype=17 and stationtype=1";
			}else if(dzb.size()==0&&!paramMap.get("dzb").trim().equals("")){
		    dzbsql="insert into bas_index_config(indextype,indexvalue,stationcode,stationtype)values(17,'"+paramMap.get("dzb")+"',"+nodeid[i]+",1)";	
			}
			if(!rzbsql.trim().equals("")){
			baseDao.execute(rzbsql);
			}
			if(!szbsql.trim().equals("")){
			baseDao.execute(szbsql);
			}
			if(!dzbsql.trim().equals("")){
			baseDao.execute(dzbsql);
			}
		}
		
		
	}



	@Override
	public Map getSummaryMap(Map<String, String> paramsMap) throws Exception {
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("select nvl(count(num),0)num,nvl(round(sum(zg)/count(num)*100,2),0) zg,"); 
		 sBuffer.append("nvl(round(sum(guanuan)/count(num)*100,2),0)guanuan,"); 
		 sBuffer.append("nvl(round(sum(dinuan)/count(num)*100,2),0)dinuan,"); 
		 sBuffer.append("nvl(sum(cnmj)/10000,0) cnmj from "); 
		 sBuffer.append("("); 
		 sBuffer.append("select 1 num, case when glfs='自管'then 1 else 0 end as zg,"); 
		 sBuffer.append("case when cnfs='地暖' then 1 else 0 end as dinuan,"); 
		 sBuffer.append("case when cnfs='挂暖' then 1 else 0 end as guanuan,"); 
		 sBuffer.append("t.* from "); 
		 sBuffer.append("(");
		 sBuffer.append(getSql(paramsMap));
		 sBuffer.append(")t  )");
		return baseDao.findMap(sBuffer.toString(), paramsMap);
	}
		
	

	



}
