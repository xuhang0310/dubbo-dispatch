package com.github.xupei.simple.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.IBaseService;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DelAllFile;
import com.github.xupei.simple.util.Freemarker;
import com.github.xupei.simple.util.PathUtil;



@Controller
@RequestMapping(value="base")
public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private static String nextLine="\r\n";
	
	@Autowired
	private IBaseService service;
	
	public String getTableGrid(Map<String,String> paramsMap){
		
		try {
			return service.getTableGrid(paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 图形配置文件
	 * @param paramsMap
	 * @return
	 */
	public Map getEchartConfig(Map<String,String> paramsMap){
		try {
			List list=service.getChartLengend(paramsMap);
			List newList=new ArrayList();
			boolean bool=false;
			for(int i=0;i<list.size();i++){
				HashMap map=(HashMap)list.get(i);
				if(map.get("ISCHECKED").toString().equals("1")){
					bool=true;
					newList.add(map);
				}
				if(i==list.size()-1&&!bool){
					newList.add(map);
				}
			}
			if(newList.size()==0){
				return paramsMap;
			}
			String chartField="";
			String chartTitle="";
			String chartType="";
			String chartPosition="";
			for(int i=0;i<newList.size();i++){
				HashMap map=(HashMap)newList.get(i);
				chartField+=map.get("FIELDNAME");
				chartTitle+=map.get("CAPTION")+"("+map.get("UNIT")+")";
				chartType+=map.get("ISTYPE");
				chartPosition+=map.get("ISPOSITON");
				if(i<newList.size()-1){
					chartField+=",";chartTitle+=",";chartType+=",";chartPosition+=",";
				}
			}
			paramsMap.put("chartField",chartField);
			paramsMap.put("chartTitle",chartTitle);
			paramsMap.put("chartType",chartType);
			paramsMap.put("chartPosition",chartPosition);
			return paramsMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramsMap;
	}
	
	//获取展示表格表头数据信息
	public List getTableGridTitle(Map<String,String> paramsMap){
		try {
			return service.getTableGridTitleForExcel(paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value="/codeGenLayer")
	public ModelAndView codeGenLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		 
		ModelAndView model=new ModelAndView();
		try {
			model.addObject("tableList", service.getTableList());
			model.setViewName("/jsp/code/create");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return model;
		
	}
	
	@RequestMapping(value="/getTableColumn")
	public void getTableColumn(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=service.getTableColumnList(paramsMap.get("tablename"));
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/legendChart")
	public ModelAndView legendChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/bas/chart/legendChart");
		
		try {
			List list=service.getChartLengend(paramsMap);
			model.addObject("legendList",list);
			model.addObject("paramsMap",paramsMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(model);
		return model;
	}
	
	
	
	
	@RequestMapping(value="/createCode")
	public void proCode(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception{
		
		/* ============================================================================================= */
		String packageName = paramsMap.get("packageName");  			//包名				========1
		String objectName = paramsMap.get("objectName");	   			//类名				========2
		String author = paramsMap.get("author");
		String project=paramsMap.get("project");
		String tablename=paramsMap.get("tablename");
		String key=paramsMap.get("key");
		
		DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl"); 
		
		Map<String,Object> root=this.getRootName(paramsMap);
		
		String ftlPath = "createCode";								//ftl路径
		String filePath=project+"/src/main/java/com/simple/dispatch/";
		
		String filePath1=project+"/src/main/webapp/jsp/";
		
		
		/*生成controller*/
		Freemarker.printFile("controllerTemplate.ftl", root, ""+packageName+"/controller/"+objectName+"Controller.java", filePath, ftlPath,project);
		
		/*生成service*/
		Freemarker.printFile("serviceTemplate.ftl", root, ""+packageName+"/service/"+objectName+"Service.java", filePath, ftlPath,project);
		
		/*生成serviceImpl*/
		Freemarker.printFile("serviceImplTemplate.ftl", root, ""+packageName+"/service/impl/"+objectName+"ServiceImpl.java", filePath, ftlPath,project);
		
		/*生成listjsp*/
		Freemarker.printFile("listJsp.ftl", root, ""+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_list.jsp", filePath1, ftlPath,project);
		
		/*生成addjsp*/
		Freemarker.printFile("editJsp.ftl", root, ""+packageName+"/"+objectName.toLowerCase()+"/edit"+objectName+".jsp", filePath1, ftlPath,project);
		
	}
	
	public Map<String,Object> getRootName(Map<String, String> paramsMap){
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> root = new HashMap<String,Object>();		//创建数据模型
		
		root.put("packageName", paramsMap.get("packageName").toString());						//包名
		root.put("objectName", paramsMap.get("objectName").toString());							//类名
		root.put("objectNameLower", paramsMap.get("objectName").toString().toLowerCase());		//类名(全小写)
		root.put("objectNameUpper", paramsMap.get("objectName").toString().toUpperCase());		//类名(全大写)
		root.put("nowDate", simple.format(new Date()));							//当前日期
		root.put("author",  paramsMap.get("author"));
		root.put("tablename", paramsMap.get("tablename"));
		root.put("key", paramsMap.get("key"));
		
		root.put("tableHtml", getEditHtml(paramsMap));
		root.put("insertSql",getInsertSql(paramsMap));
		root.put("deleteSql",getDeleteSql(paramsMap));
		root.put("updateSql",getUpdateSql(paramsMap));
		return  root;
	}
	
	public String getUpdateSql(Map<String ,String> paramsMap){
		String columnsArr []=paramsMap.get("columnsArr").split("@");
		String ishideArr []=paramsMap.get("ishideArr").split("@");
		String formtypeArr []=paramsMap.get("formtypeArr").split("@");
		//String textArr []=paramsMap.get("textArr").split(",");
		
		String tablename=paramsMap.get("tablename");
		String key=paramsMap.get("key");
		
		String javaCode="String sql=\"update "+tablename+" set "+key+"='\"+paramsMap.get(\""+key+"\") +\"'   \"; "+nextLine;
		for(int i=0;i<columnsArr.length;i++){
			String columns=columnsArr[i];
			String ishide=ishideArr[i];
			if(ishide.equals("1")&&!columns.equals(paramsMap.get("key").toUpperCase())){
				javaCode+="if(!StringUtils.isEmpty(paramsMap.get(\""+columns.toLowerCase()+"\"))){ sql+=\" , "+columns+"='\"+paramsMap.get(\""+columns.toLowerCase()+"\") +\"'  \";   }"+nextLine;
			}
			
		}
		javaCode+=" sql+=\" where "+key+"='\"+paramsMap.get(\""+key+"\") +\"'  \"; "+nextLine;
		javaCode+=" dao.execute(sql); ";
		return javaCode;
	}
	
	public String getDeleteSql(Map<String, String> paramsMap){
		
		
		String tablename=paramsMap.get("tablename");
		String key=paramsMap.get("key");
		
		String sql=" delete from "+tablename+" where ["+key+"='{"+key+"}']  ";
		
		return sql;
	}
	
	public String getInsertSql(Map<String, String> paramsMap){
		String columnsArr []=paramsMap.get("columnsArr").split("@");
		String ishideArr []=paramsMap.get("ishideArr").split("@");
		String formtypeArr []=paramsMap.get("formtypeArr").split("@");
		//String textArr []=paramsMap.get("textArr").split(",");
		
		String tablename=paramsMap.get("tablename");
		String key=paramsMap.get("key");
		String columnsSql="";
		String valueSql="";
		for(int i=0;i<columnsArr.length;i++){
			String columns=columnsArr[i];
			String ishide=ishideArr[i];
			String formtype=formtypeArr[i];
			if(ishide.equals("1")&&!columns.equals(paramsMap.get("key").toUpperCase())){
				columnsSql+=columns+",";
				valueSql+=" '\"+paramsMap.get(\""+columns.toLowerCase()+"\") +\"'"+",";
			}
		}
		
		String sql=" insert into "+tablename+" ("+key+","+columnsSql.substring(0, columnsSql.length()-1)+") values('\"+UUIDTool.getUUID()+\"',"+valueSql.substring(0, valueSql.length()-1)+") ";
		
		return sql;
	}
	
	public  String getEditHtml(Map<String, String> paramsMap){
		String html="";
		String columnsArr []=paramsMap.get("columnsArr").split("@");
		String ishideArr []=paramsMap.get("ishideArr").split("@");
		String formtypeArr []=paramsMap.get("formtypeArr").split("@");
		//String textArr []=paramsMap.get("textArr").split(",");
		String commentsArr []=paramsMap.get("commentsArr").split("@");
		for(int i=0;i<columnsArr.length;i++){
			int index=i+1;
			String columns=columnsArr[i];
			String ishide=ishideArr[i];
			String formtype=formtypeArr[i];
			String text=commentsArr[i];
			if(i%2==0&&ishide.equals("1")){
				html=html+"<tr>"+nextLine;
			}
			if(ishide.equals("1")&&!columns.equals(paramsMap.get("key").toUpperCase())){
				html=html+formatDivHtml(columns,formtype,text);
			
			}
			if(index%2==0&&ishide.equals("1")){
				html=html+"</tr>"+nextLine;;
			}
			
		}
		
		return html;
	}
	
	public String formatDivHtml(String columns,String formtype,String text){
		String str="<td><label class=\"label\" >"+text+"：</label>"+nextLine +
				" "+nextLine;
		if(formtype.equals("1")){
			str+=formatInputHtml(columns,text)+nextLine;
		}else{
			str+=formatSelectHtml(columns,text)+nextLine;
		}
		str+="</td>"+nextLine;
		return str;
	}
	
	public String formatInputHtml(String columns,String text){
		String str="<input type=\"text\" value=\"${obj."+columns+"}\"  id=\""+columns.toLowerCase()+"\"   name=\""+columns.toLowerCase()+"\" >";
		return str;
	}
	
	public String formatSelectHtml(String columns,String text){
		String str=" <select  name=\""+columns.toLowerCase()+"\"  ></select>";
		return str;
	}
	

}
