package com.github.xupei.simple.weather.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;



import com.github.xupei.dubbo.api.IBasMeterService;
import com.github.xupei.dubbo.api.weather.IWeatherService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.grid.GridSqlUtilTool;






@Service
public class WeatherServiceImpl extends GridSqlUtilTool  implements IWeatherService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public String getSql(Map<String, String> paramMap) {
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("SELECT TO_CHAR(T.READDATE, 'yyyy-mm-dd') || ' ' || "); 
		 sBuffer.append(" SUBSTR(trim(T.READTIME), 1, 2) READDATE,  "); 
		 sBuffer.append(" T.TEMPVALUE "); 
		 sBuffer.append("  FROM TEMP_METRICAL T "); 
		 sBuffer.append("   WHERE 1 = 1 "); 
		 sBuffer.append("  [  and TO_CHAR(T.READDATE, 'yyyy-mm-dd') || ' ' || T.READTIME >='{kssj}' "); 
		 sBuffer.append("  [and TO_CHAR(T.READDATE, 'yyyy-mm-dd') || ' ' || T.READTIME <='{jssj}' ]"); 
		 sBuffer.append("  ORDER BY T.READDATE desc, T.READTIME desc ");
			
		return sBuffer.toString();
	}

	
	public Map getWeatherHourList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		String sql=getSql(paramMap);
		
		return baseDao.queryGridListNoPage(sql, paramMap);
	}


	

	@Override
	public List getYbList() throws Exception {
		String sql="SELECT A.FILEDATE FILEDATE, "+
			      " A.FOREDATE FOREDATE, "+
			      " A.STATUS STATUS, "+
			     "  A.WIND WIND, "+
			     "  A.POWER POWER, "+
			     "  B.IMAGEINDEX IMAGEINDEX, "+
			      " ROUND(A.LOWTEMP, 0) AS LOWTEMP, "+
			     "  ROUND(A.HIGHTEMP, 0) AS HIGHTEMP, "+
			    "   ROUND((A.LOWTEMP + A.HIGHTEMP) / 2, 2) avgtemp "+
			"  FROM TEMP_FOREWEEK A, BAS_WEATHER B "+
		/*	" WHERE FILEDATE = (SELECT MAX(FILEDATE) FROM TEMP_FOREWEEK) "+*/
			"where  to_char(A.foredate,'yyyy-mm-dd')>to_char(sysdate,'yyyy-mm-dd') "+
			"   AND A.STATUS = B.WEATHERNAME(+) "+
			" ORDER BY FOREDATE ";
		return baseDao.findAll(sql);
	}


	@Override
	public List getRealList() throws Exception {
		StringBuffer sBuffer = new StringBuffer(""); 
		 sBuffer.append("select t.tempvalue,t.readtime, "); 
		 sBuffer.append("to_char(t.readdate,'yyyy-mm-dd')readdate "); 
		 sBuffer.append("from temp_metrical t "); 
		 sBuffer.append(" where t.readdate in(select max(readdate)from temp_metrical) "); 
		 sBuffer.append("order by t.readtime desc ");
					
		return baseDao.findAll(sBuffer.toString());
	}


	@Override
	public List getParamDate() throws Exception {
		String sql="select to_char(t.startdate,'yyyy-mm-dd hh24:mi:ss')kssj,"+
						"to_char(t.enddate,'yyyy-mm-dd hh24:mi:ss')jssj "+
						"from data_param t where t.currscheduler=1 ";
		return baseDao.findAll(sql);
	}


	@Override
	public List getChart(Map<String, String> paramsMap) throws Exception {
		// TODO Auto-generated method stub
		List list=baseDao.findAll(getSql(paramsMap), paramsMap);
		return list;
	}


	@Override
	public List getWeatherConfig() throws Exception {
		String sql="select * from weather_config";
		return baseDao.findAll(sql);
	}


	@Override
	public List  getWeatherForHour(String cityCode) throws Exception {
		 ArrayList listWeather=new ArrayList();
		String xmlStr = getHttpHourMessageXml(cityCode);
		System.out.println(xmlStr);
		try {			
			  Document document = DocumentHelper.parseText(xmlStr);
			  Element root = (Element) document.getRootElement();    
			  String  ptime = String.valueOf(root.attribute("ptime").getData()).split(" ")[0];
			  List<Element> list = (List)root.elements();
			  for(int i=0;i<4;i++){
				  HashMap weathermap  = new HashMap();
				  Element e = list.get(i);
				  String hour = String.valueOf(e.attribute("h").getData());
				  String wd = String.valueOf(e.attribute("wd").getData());
				  if(wd!=null && wd!=""){
					  System.out.println(hour+"@@@@@@@@@@@"+wd);
					  weathermap.put("time", hour+":00:00");
					  weathermap.put("date", "20"+ptime);
					  weathermap.put("temp", wd);
					  listWeather.add(weathermap);
				  }

			  }
			 
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
		
		return listWeather;
	}


	private String getHttpHourMessageXml(String cityCode) {

		StringBuffer content = null;
		try {
			String url = "http://flash.weather.com.cn/sk2/"+ cityCode +".xml";
			URL connect = new URL(url.toString());
			URLConnection connection = connect.openConnection();
			connection.setDoOutput(true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			content = new StringBuffer();
			int i=0;
			char m = 'w';
			char n = 'd';
			char o = 'h';
			
	  		while ((i = reader.read()) > -1) {
	  			if((char)i==m && reader.read()==n){
	  				content.append(" "+(char) i+"d");
	  			}else if((char)i==o){
	  			content.append(" "+(char) i);
	  			}else{
	  				content.append((char) i);
	  			}
	  		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content.toString();
	}


	@Override
	public void UpdateTempMetrical(List weatherList) throws Exception {
		for(int i=0;i<weatherList.size();i++){
			HashMap weather=(HashMap) weatherList.get(i);
			String sql="";
			boolean insert = isExistHour(weather.get("date").toString(),weather.get("time").toString()); 
			if(!insert){
				sql="insert into temp_metrical(tempvalue, readtime, readdate, program, stationcode)"+
						"values(" +
						     weather.get("temp")+",'"+
						     weather.get("time")+"',"+
						     "to_date('"+weather.get("date")+"','yyyy-mm-dd'),'000','0000000000')";				
			}else{
				sql="update temp_metrical set tempvalue="+weather.get("temp")+" where readtime='"+weather.get("time")+"' and readdate=to_date('"+weather.get("date")+"','yyyy-mm-dd')";
			}
			baseDao.execute(sql);
		}
		
	}


	private boolean isExistHour(String readdate, String readtime)throws Exception {
    	String sql = "select count(*) num from temp_metrical t where to_char(t.readdate,'yyyy-mm-dd')||' '||readtime='"+readdate+" "+readtime+"'";
    	HashMap nummap=(HashMap) baseDao.findAll(sql).get(0);
    	String nums=nummap.get("num").toString();
    	if(nums.equals("0")){
    		return false;
    	}else{
		return true;
    	}
	}


	@Override
	public void UpdateTempForeWeek(List weatherListforWeek) throws Exception {
		 for(int i=0;i<weatherListforWeek.size();i++){
			 HashMap weather=(HashMap) weatherListforWeek.get(i);
			    String sql = "";
			    String sqltemp="";
			    boolean insert = isExistDay(weather.get("nowdate").toString(),weather.get("savadate_weather").toString());
			    if(!insert){
			    	sql = "insert into TEMP_FOREWEEK(FILEDATE,FOREDATE,STATUS,WIND,POWER,HIGHTEMP,LOWTEMP,PROGRAM) values(to_date('"+
			                   weather.get("nowdate")+"','yyyy-mm-dd'),to_date('"+weather.get("savadate_weather")+"','yyyy-mm-dd'),'"+weather.get("status1")+"','"+
			    			   weather.get("direction1")+"','"+weather.get("power1")+"','"+weather.get("temperature1")+"','"+weather.get("temperature2")+"','0000000000')"; 
			        
			    	if(weather.get("nowdate").equals(weather.get("savadate_weather"))){//ͬ����ݵ�TEMP_FOREVALUE
			    		   sqltemp = "insert into temp_forevalue(foredate,foretime,todaynightlowtemp,todaydaywindpower,todaydaystatus,todaydayhightemp,dayavgtemp)values(to_date('"
			    				  +weather.get("nowdate")+"','yyyy-mm-dd'),'06:00:00',"+weather.get("temperature2")+",'"+weather.get("direction1")+weather.get("power1")+"','"
			    				  +weather.get("status1")+"',"+weather.get("temperature1")+",("+weather.get("temperature1")+"+"+weather.get("temperature2")+")/2)";  
			    	}
			    }else{
			    	sql="update TEMP_FOREWEEK set STATUS='"+weather.get("status1")+"',WIND='"+weather.get("direction1")+"',POWER='"+
			       weather.get("power1")+"',HIGHTEMP="+weather.get("temperature1")+",LOWTEMP="+weather.get("temperature2")+" where " +
			    			" FILEDATE=to_date('"+weather.get("nowdate")+"','yyyy-mm-dd') and FOREDATE=to_date('"+
			       weather.get("savadate_weather")+"','yyyy-mm-dd')";
			    }
			    System.out.println(sql);
			    baseDao.execute(sql);
			    if(!sqltemp.equals("")){
			    	baseDao.execute(sqltemp);
			    }

		 }
		
	}


	private boolean isExistDay(String  nowdate, String savadate_weather)throws Exception {
		String sql = "select count(*) num from TEMP_FOREWEEK t where to_char(t.filedate,'yyyy-mm-dd')='"+nowdate+"' and to_char(t.foredate,'yyyy-mm-dd')='"+savadate_weather+"'";
    	HashMap map=(HashMap) baseDao.findAll(sql).get(0);
    	String nums=map.get("NUM").toString();
    	if(nums.equals("0")){
    		return false;
    	}else{
    		return true;
    	}
	}


	@Override
	public List getWeatherForWeek(String ip, String days, String appkey,
			String sign) throws Exception {

		
		List list  = new ArrayList();
    	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
    	String nowdate=formatter.format(new Date());
    	String xmlStr = getHttpDayMessageXml(ip,appkey,sign);
    	String windP = null;
		for(int i=0;i<=Integer.parseInt(days);i++){
			
			HashMap weather  = new HashMap();
			try {
				
				  Document document = DocumentHelper.parseText(xmlStr);
				  Element root = (Element) document.getRootElement();    
				  weather.put("nowdate", nowdate);
				  weather.put("savadate_weather",root.element("result").element("item_"+i).element("days").getText());
				  weather.put("city", root.element("result").element("item_"+i).element("citynm").getText());
				  weather.put("status1",root.element("result").element("item_"+i).element("weather").getText());
				  weather.put("direction1", root.element("result").element("item_"+i).element("wind").getText());
				  windP = root.element("result").element("item_"+i).element("winp").getText();
				  weather.put("power1", windP.substring(0,windP.length()-1));
				  weather.put("temperature1", root.element("result").element("item_"+i).element("temp_high").getText());
				  weather.put("temperature2", root.element("result").element("item_"+i).element("temp_low").getText());
				  list.add(weather);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return list;
		
	
		
	}


	private String getHttpDayMessageXml(String ip, String appkey, String sign) {
		StringBuffer content = new StringBuffer("");
		try {
		//	String url = "http://php.weather.sina.com.cn/xml.php?city="+city+"&password=DJOYnieT8234jlsK&day="+day;
			String url = "http://api.k780.com/?app=weather.future&weaid="+ip+"&appkey="+appkey+"&sign="+sign+"&format=xml";
			URL connect = new URL(url.toString());
			URLConnection connection = connect.openConnection();
			connection.setDoOutput(true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			content = new StringBuffer();
			int i=0;
	  		while ((i = reader.read()) > -1) {
	  			content.append((char) i);
	  		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content.toString();
	}


	@Override
	public List getProvince() throws Exception {
		String sql="select distinct t.province province from weather_city t";
		return baseDao.findAll(sql);
	}


	@Override
	public List getCityList() throws Exception {
		String sql="select * from weather_city t";
		return baseDao.findAll(sql);
	}


	@Override
	public List getCity() throws Exception {
		String sql="select t.*,c.province from "+
					"weather_config t,weather_city c "+
				"where t.citycode=c.citycode ";
		return baseDao.findAll(sql);
	}


	@Override
	public void updateWeatherCity(Map<String, String> paramsMap)
			throws Exception {
		String sql="update weather_config t set t.city=(select city from weather_city where citycode='"+
				paramsMap.get("city")+"'), t.citycode='"+paramsMap.get("city")+"',t.ip='"+paramsMap.get("ip")+"'";
		baseDao.execute(sql);
	}


	@Override
	public List getProjectList(Map<String, String> paramsMap) throws Exception {
		String sql="select citycode,city from weather_city where province='"+paramsMap.get("sf")+"'";
		return baseDao.findAll(sql);
	}


	@Override
	public List getRealOne() throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("  SELECT A.FILEDATE FILEDATE, ")
		.append("         A.FOREDATE FOREDATE, ")
		.append("         A.STATUS STATUS, ")
		.append("         A.WIND WIND, ")
		.append("         A.POWER POWER, ")
		.append("         B.IMAGEINDEX IMAGEINDEX, ")
		.append("         ROUND(A.LOWTEMP, 0) AS LOWTEMP, ")
		.append("         ROUND(A.HIGHTEMP, 0) AS HIGHTEMP, ")
		.append("         ROUND((A.LOWTEMP + A.HIGHTEMP) / 2, 2) avgtemp ")
		.append("    FROM TEMP_FOREWEEK A, BAS_WEATHER B ")
		.append("   where to_char(A.foredate, 'yyyy-mm-dd') = to_char(sysdate, 'yyyy-mm-dd') ")
		.append("     AND A.STATUS = B.WEATHERNAME(+) ")
		.append("   ORDER BY FOREDATE ");
		return baseDao.findAll(sqlBuf.toString());
	}













	


}

