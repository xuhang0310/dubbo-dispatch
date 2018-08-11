package com.github.xupei.simple.scada.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.xupei.dubbo.api.scada.IFeedRealService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;
import com.github.xupei.simple.util.ListRender;
import com.github.xupei.simple.util.UUIDTool;


@Service
public class FeedRealServiceImpl extends GridSqlUtilTool implements IFeedRealService  {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public String getSql(Map<String, String> paramMap){
		String sql="select bf.feedid,bf.sjfh," +//bf.feedcode,
				"ss.linecode,bf.feedname,to_char(t.scadatime,'yyyy-MM-dd hh24:mi:ss') scadatimestr,t.* "+
				"  from SCADA_FEED_REAL t, scada_station ss, bas_feed bf "+
				" where 1=1 and ss.stationtype=99 and ss.stationcode=bf.feedid  and t.stationid=ss.id and  (t.stationid, t.scadatime) in "+
				"       (select stationid, max(scadatime) "+
				"          from scada_feed_real "+
				"         group by stationid) "+
				"  [and bf.feedcode={feedcode}]";
	
		return sql;
	}

	public String getChartSql(Map<String, String> paramMap){
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("   select to_char(t.scadatime,'yyyy-mm-dd hh24:mi:ss')scadatime, ")
		.append("   avg(t.supplytemp)supplytemp,avg(t.returntemp)returntemp, ")
		.append("   avg(t.supplypress)supplypress,avg(t.returnpress)returnpress, ")
		.append("   sum(t.supplyflow)supplyflow,sum(t.heatquantity)heatquantity ")
		.append("    from SCADA_FEED_REAL t, scada_station ss, bas_feed bf ")
		.append("   where ss.stationtype = 99 ")
		.append("        and ss.stationcode = bf.feedid ")
		.append("        and t.stationid = ss.id  and ")
		.append("   to_char(t.scadatime,'yyyy-mm-dd')  ")
		.append("   in(select max(to_char(t.scadatime,'yyyy-mm-dd'))from SCADA_FEED_REAL t ) ")
		.append("   [and bf.feedid={feedcode}] ")
		.append("   group by (t.scadatime) ")
		.append("   order by t.scadatime ");
		return sqlBuf.toString();
	}
	
	
	@Override
	public Map getFeedRealList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql=getSql(paramMap);
		HashMap gridMap=(HashMap) baseDao.queryGridList(sql, paramMap);
		
		List list=ListRender.renderRealList(gridMap,getFeedWarnConfig(),"FEEDID","FEEDNAME");
		gridMap.put("rows", list);
		return gridMap;
	}
	public HashMap<String,List> getFeedWarnConfig() throws Exception{
		String sql="select * from feed_warn_config order by feedid ";
		List list= baseDao.findAll(sql);
		return ListRender.listConvertToMap(list, "FEEDID");
		
	}

	@Override
	public List getRealDataList(String id) throws Exception {
	String sql="	select * "+
					"from SCADA_FEED_REAL "+
				   "where scadatime = "+
		     "  (select max(scadatime) from SCADA_FEED_REAL where feedcode ='" +
				   id+"')"+
		  " and feedcode ="+id;
		
		return baseDao.findAll(sql);
	}

	@Override
	public List getParamConfigList(String code) throws Exception {
		String table="SCADA_FEED_REAL";
		String sql = "select t.picid," +
				"t.name," +
				"t.text," +
				"to_char(t.ishiden) ishiden," +
				"t.dplay," +
				"to_char(t.x_num) x_num," +
				"to_char(t.y_num) y_num, " +
				"t.unit," +
				"t.validnum," +
				"t.bg," +
				"to_char(t.fsize) fsize," +
				"t.position " +
				" from PICTURE_SET_CONFIG t,bas_feed b " +
			//	"where picid='"+code+"'";
				"where b.feedcode="+code+
				" and b.gytid=t.picid";
		return baseDao.findAll(sql);
	}

	@Override
	public String getImgUrl(String code) throws Exception {
		//String sql="select pic_fname from picture_set where picid="+code;
		String sql=  "select t.pic_fname from "+
						"picture_set t,bas_feed b "+
						 "  where b.gytid=t.id "+
						"   and b.feedcode="+code;
		
		return baseDao.queryForString(sql);
	}

	@Override
	public List getParamConfigForUpdateArea(String code) throws Exception {
		String sql="select t.picid,"+
				        "t.name, "+
				        "t.text, "+
				        "to_char(t.ishiden) ishiden, "+
				        "t.dplay, "+
				        "to_char(t.x_num) x_num, "+
				        "to_char(t.y_num) y_num, "+
				        "t.unit, "+
				        "t.validnum, "+
				        "t.bg, "+
				        "to_char(t.fsize) fsize, "+
				        "t.position  "+
				        "from picture_set_config t,bas_feed b  "+
				        "where  ishiden = 0  "+
				        "and t.picid=b.gytid "+
				        "and b.feedcode ="+code;
		return baseDao.findAll(sql);
	}

	@Override
	public void update(String code, String name, String x, String y)
			throws Exception {
		String sql = " UPDATE picture_set_config "+
						"set X_NUM=" + x + ", "+
					"Y_NUM=" + y+
					 " where picid = " + code + " and name = '"+name+"' and type=99";
		baseDao.execute(sql);
	}

	@Override
	public void delete(String code) throws Exception {
		String sql="delete from picture_set_config where picid="+code;
		
		baseDao.execute(sql);
		
	}

	@Override
	public void updateConfig(String code, HashMap<Object, Object> configmap)
			throws Exception {
		//{position=上, text=热源代码, ishiden=0, font_size=, code=8, vaildnum=, unit=, px=0, 
		//dplay=, background=, column=FEEDCODE, y_num=369, x_num=242}

		String sql="update picture_set_config set text='"+configmap.get("text")+"', "+
				"ishiden='"+configmap.get("ishiden")+"', "+
				"dplay='"+configmap.get("dplay")+"', "+
				"x_num='"+configmap.get("x_num")+"', "+
				"y_num='"+configmap.get("y_num")+"', "+
				"unit='"+configmap.get("unit")+"', "+
				"validnum='"+configmap.get("vaildnum")+"', "+
				"bg='"+configmap.get("background")+"', "+
				"fsize='"+configmap.get("font_size")+"', "+
				"position='"+configmap.get("position")+"', "+
				"px='"+configmap.get("px")+"'"+ 
				"where picid=(select gytid from bas_feed where feedcode=" +code+")"+
				" and name='"+configmap.get("column")+"' "+
				" and type=99";
		
		baseDao.execute(sql);
		
		
	}

	@Override
	public void saveWarnConfig(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		insertWarnConfig(paramMap.get("feedcode"),paramMap.get("supplytemp").split(",")[0],paramMap.get("supplytemp").split(",")[1],paramMap.get("supplytemp").split(",")[2]);
		insertWarnConfig(paramMap.get("feedcode"),paramMap.get("returntemp").split(",")[0],paramMap.get("returntemp").split(",")[1],paramMap.get("returntemp").split(",")[2]);
		insertWarnConfig(paramMap.get("feedcode"),paramMap.get("supplypress").split(",")[0],paramMap.get("supplypress").split(",")[1],paramMap.get("supplypress").split(",")[2]);
		insertWarnConfig(paramMap.get("feedcode"),paramMap.get("returnpress").split(",")[0],paramMap.get("returnpress").split(",")[1],paramMap.get("returnpress").split(",")[2]);
		insertWarnConfig(paramMap.get("feedcode"),paramMap.get("supplyflow").split(",")[0],paramMap.get("supplyflow").split(",")[1],paramMap.get("supplyflow").split(",")[2]);
		insertWarnConfig(paramMap.get("feedcode"),paramMap.get("heatquantity").split(",")[0],paramMap.get("heatquantity").split(",")[1],paramMap.get("heatquantity").split(",")[2]);
	}
	
	public void insertWarnConfig(String feedid,String field,String max,String min) throws Exception{
		for(int i=0;i<feedid.split(",").length;i++){
			if(!feedid.split(",")[i].equals("")){
				String delsql="delete from feed_warn_config where feedid='"+feedid.split(",")[i]+"' and fieldname='"+field+"'";
				baseDao.execute(delsql);
				String sql="insert into feed_warn_config(id, feedid, fieldname, maxnum, minnum)values('"+UUIDTool.getUUID()+"', '"+feedid.split(",")[i]+"', '"+field+"', "+max+", "+min+")";
				baseDao.addObject(sql);
			}
			
		}
		
		
	}

	@Override
	public List getWarnConfig(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from feed_warn_config where 1=1 [ and feedid='{feedcode}' ]";
		return baseDao.findAll(sql, paramMap);
	}

	@Override
	public List getFeedRealChart(Map<String, String> paramsMap)
			throws Exception {
		List list= baseDao.findAll(getChartSql(paramsMap), paramsMap);
		return list;
	}
	
	public List getWarningDetailEchart(Map<String, String> paramMap) throws Exception{
		String sql="select bf.feedname,to_char(t.scadatime,'yyyy-MM-dd hh24:mi:ss') scadatimestr,t.supplytemp,fw.maxnum,fw.minnum  "+
				"  from scada_feed_real t,feed_warn_config fw,bas_feed bf ,scada_station ss "+
				" where 1=1 [ and t.stationid = '{stationid}' ] "+
				" and fw.feedid=bf.feedid "+
				" and ss.stationtype=99  "+
				" and ss.stationcode=bf.feedid "+
				" and t.stationid=ss.id "+
				" [ and fw.fieldname='{fieldname}' ]"+
				" order by scadatime desc ";
		
		return baseDao.findAll(sql,paramMap);
	}

	@Override
	public Map getSummaryMap(Map<String, String> paramsMap) throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select count(*)feednumber,nvl(round(avg(supplytemp),2),0)supplytemp,nvl(round(avg(returntemp),2),0)returntemp, ")
		.append(" nvl(round(avg(supplypress),2),0)supplypress,nvl(round(avg(returnpress),2),0)returnpress,nvl(round(avg(supplyflow),2),0)supplyflow, ")
		.append(" nvl(round(avg(heatquantity),2),0)heatquantity,nvl(round(sum(heatquantity),2),0)sjfh,nvl(sum(sjfh),0)feedsjfh from( ")
		.append(getSql(paramsMap)+" )");
		return baseDao.findMap(sqlBuf.toString(), paramsMap);
	
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
