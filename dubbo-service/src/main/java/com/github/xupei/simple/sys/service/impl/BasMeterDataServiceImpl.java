package com.github.xupei.simple.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IBasMeterDataService;
import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class BasMeterDataServiceImpl extends GridSqlUtilTool  implements IBasMeterDataService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public String getSql(Map<String, String> paramMap) {
		/*   and b.nodename like zm
           and to_char(p.scadatime,'yyyy-mm-dd')>=to_char(kssj,'yyyy-mm-dd')
           and to_char(p.scadatime,'yyyy-mm-dd')<=to_char(jssj,'yyyy-mm-dd')
           and t.stationtype=lx
           [ and t.stationtype='{zlx}']
           */
		String sql="select ID, METERNAME, PTIME, DATANUM, NODENAME, SULX,id note1"+
						" from (select to_char(p.scadatime, 'yyyy-MM-dd') as PTIME,"+
			           " p.*,t.metername,b.nodename,"+
			            "   decode(t.stationtype, 1, '换热站', 99, '热源厂') as sulx "+
			       "   from BAS_METER_DATA p, "+
			         "      BAS_METER t, "+
			           " (select nodecode, nodename, 1 stationtype "+
			              "    from bas_node "+
			              "  union all "+
			             "   select feedcode, feedname, 99 stationtype "+
			             "     from bas_feed) b "+
			        "where 1 = 1 "+
			        "   and b.stationtype = t.stationtype "+
			         "  and t.stationcode = b.nodecode "+
			       "    and p.meter_id = t.id " +
			        " [and b.nodename like'%{zm}%'] "+
			       " [and  to_char(p.scadatime,'yyyy-mm-dd')>='{kssj}'] "+
			       " [and  to_char(p.scadatime,'yyyy-mm-dd')<='{jssj}'] "+
			       " [and t.stationtype='{lx}'] "+
			       "order by p.scadatime desc"+
			       " )";
		return sql;
	}


	
	public Map getBasMeterList(Map<String, String> paramMap) throws Exception {
		String sql=getSql(paramMap);
		return baseDao.queryGridList(sql, paramMap);
	}


	
	
	

	@Override
	public void saveMeterNum(Map<String, String> paramMap) throws Exception {
		//{sj=2018-05-21, 2018-05-21=}
		String sql="update bas_meter_data set datanum='"+
					paramMap.get("DATANUM")+"' where id='"+paramMap.get("NOTE1")+"'";
		baseDao.execute(sql);
	}
	
	
	public void deleteMeter(String id) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from bas_meter where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	




	@Override
	public List findMeter(String id) throws Exception {
		String sql="select * from bas_meter"+
			" where id= "+id; 
		
		return baseDao.findAll(sql);
	}

	@Override
	public void editMeter(Map<String, String> paramsMap) throws Exception {
		String sql="update bas_meter set metername='"+paramsMap.get("sbmc")+"',stationcode='"+paramsMap.get("mc")+
						"',stationtype='"+paramsMap.get("stationtype")+"',meter_type='"+paramsMap.get("yblx")+"',bl='"+
						paramsMap.get("bl")+"' where id="+paramsMap.get("id");
						
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
	public List getBasmers() throws Exception {
		String sql="select distinct id from bas_meter order by id";
	
		return baseDao.findAll(sql);
	}



	@Override
	public int getCount(String sj, String ids) throws Exception {
		String sql="select count(*) from bas_meter_data where meter_id='"
	+ids.trim()+"' and to_char(scadatime,'yyyy-mm-dd')='"+sj+"'";
		int a=baseDao.queryForInt(sql);
		return a;
	}



	@Override
	public void insertNum(String sj, String id) throws Exception {
		String sql="insert into bas_meter_data"+
           "  (meter_id, scadatime, datanum) values('"+
          id+"',to_date('"+sj.trim()+"','yyyy-mm-dd'),0)";
		baseDao.addObject(sql);
		
	}




	


}

