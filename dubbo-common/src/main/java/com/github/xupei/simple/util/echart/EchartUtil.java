package com.github.xupei.simple.util.echart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.github.xupei.simple.util.ListRender;


import net.sf.json.JSONObject;


/**
 * 时间：2018-5-28 上午9:32:16  <BR>
 * 首次开发时间：2018-5-28 上午9:32:16 <BR>
 * 版本：V1.0
 */
public class EchartUtil {
	
	
	
	
	public static JSONObject createLineColumnEchartData(EchartVo vo){
		if(vo.getY2Data().size()<1){
			return createLineColumnEchartData(vo.getXaisData(),vo.getY1Data(),vo.getLegendField() ,vo.getLegendType() ,vo.getTargetField());
		}
			return createLineColumnEchartData(vo.getXaisData(),vo.getY1Data(),vo.getY2Data(),vo.getLegendField() ,vo.getLegendType() ,vo.getTargetField());
	}
	public static JSONObject createLineColumnEchartData(List xaisData,List y1Data,String [] legendField,String [] legendType,String targetField){
		List newList=new ArrayList();
		List listXais=new ArrayList();
		for(int i=0;i<xaisData.size();i++){
			HashMap mapData=(HashMap)xaisData.get(i);
			HashMap map=new HashMap();
			for(int n=0;n<y1Data.size();n++){
				HashMap y1Map=(HashMap)xaisData.get(n);
				if(mapData.get(targetField)==y1Map.get(targetField)){
					
					break;
				}
			}
			mapData.get(targetField);
			
			/*for(String field:legendField){
				map.put(field, mapData.get(field.toUpperCase())==null?"0":mapData.get(field.toUpperCase()));
				map.put(targetField, mapData.get(targetField.toUpperCase())==null?"0":mapData.get(targetField.toUpperCase()));
			}*/
			listXais.add(mapData.get(targetField.toUpperCase()));
			newList.add(map);
		}
		List<String> columnKeyList = returnNewColumn(null,legendField);
	//	List<String> legendNameKeyList = returnNewName(null,legendName);
		List<String> legendTypeKeyList = returnNewType(null,legendType);
		
		
		HashMap mapJson=new HashMap();
		mapJson.put("x", listXais);
		mapJson.put("data",  newList); 
		mapJson.put("legendField", columnKeyList);
		//mapJson.put("legendName", legendNameKeyList);
		mapJson.put("legendType", legendTypeKeyList);
		JSONObject jsonObject = JSONObject.fromObject(mapJson);
		
		return jsonObject;
	}
	
	public static JSONObject createLineColumnEchartData(List xaisData,List y1Data,List y2Data,String [] legendField,String [] legendType,String targetField){
		List newList=new ArrayList();
		List listXais=new ArrayList();
		//List keyList=returnKeyList(dataList,key);
		LinkedHashSet setTime=new LinkedHashSet();
		
		for(int i=0;i<xaisData.size();i++){
			HashMap mapData=(HashMap)xaisData.get(i);
			setTime.add(mapData.get(targetField.toUpperCase()));
			
		}
		listXais.addAll(setTime); 
		
		HashMap<String,Map> mapFinal=new HashMap();
		for(int i=0;i<listXais.size();i++){
			String targetValue=listXais.get(i)+"";
//			for(int k=0;k<keyList.size();k++){
//				String keyAppend=keyList.get(k)+"";
//				for(int q=0;q<dataList.size();q++){
//					HashMap mapData=(HashMap)dataList.get(q);
//					String keyData="";
//					for(String field:key){
//						keyData+=mapData.get(field)+",";
//					}
//					if(keyData.endsWith(",")){
//						keyData=keyData.substring(0, keyData.length()-1);
//					}
//					String targetAppend=(mapData.get(targetField.toUpperCase())==null?"0":mapData.get(targetField.toUpperCase())).toString();
//					if(keyAppend.equals(keyData)&&targetAppend.equals(targetValue)){
//						mapFinal.put(targetAppend+keyAppend, mapData);
//					}
//				}
//			}
		}
		for(int i=0;i<listXais.size();i++){
			String targetValue=listXais.get(i)+"";
			HashMap map=new HashMap();
			//for(int k=0;k<keyList.size();k++){
			//	String keyAppend=keyList.get(k)+"";
				//Map mapData=mapFinal.get(targetValue+keyAppend);
			/*	for(String field:column){
					map.put(field+"_"+k, mapData.get(field.toUpperCase())==null?"0":mapData.get(field.toUpperCase()));
					map.put(targetField, mapData.get(targetField.toUpperCase())==null?"0":mapData.get(targetField.toUpperCase()));
				}
				mapData.remove(targetValue+keyAppend);*/
			///}
			newList.add(map);
		}	
		
		
		List<String> columnKeyList =null; //returnNewColumn(keyList,column);
		List<String> legendNameKeyList = null;// returnNewName(keyList,legendName);
		List<String> legendTypeKeyList =null; //returnNewType(keyList,legendType);
		
		HashMap mapJson=new HashMap();
		mapJson.put("x", listXais);
		mapJson.put("legendField", columnKeyList);
		mapJson.put("legendName", legendNameKeyList);
		mapJson.put("legendType", legendTypeKeyList);
		mapJson.put("data",  newList); 
		 
		JSONObject jsonObject = JSONObject.fromObject(mapJson);
		
		return jsonObject;
	}
	public static List returnNewColumn(List keyList ,String [] str){
		List newList=new ArrayList();
		if(keyList!=null){
			for(int i=0;i<keyList.size();i++){
				for(String field:str){
					newList.add(field+"_"+i);
				}
			}
		}else{
			newList= Arrays.asList(str);  
		}
		
		
		return newList;
	}
	public static List returnNewName(List keyList ,String [] str){
		List newList=new ArrayList();
		if(keyList!=null){
			for(int i=0;i<keyList.size();i++){
				String keyAppend=keyList.get(i)+"";
				for(String field:str){
					newList.add(keyAppend+field);
				}
			}
		}else{
			newList= Arrays.asList(str);  
		}
		

		return newList;
	}
	public static List returnNewType(List keyList ,String [] str){
		List newList=new ArrayList();
		if(keyList!=null){
			for(int i=0;i<keyList.size();i++){
				String keyAppend=keyList.get(i)+"";
				for(String field:str){
					newList.add(field);
				}
			}
		}else{
			newList= Arrays.asList(str);  
		}
		

		return newList;
	}
	
	public static List returnKeyList(List dataList,String [] key){
		Set set=new HashSet();
		for(int i=0;i<dataList.size();i++){
			String keyAppend="";
			HashMap mapData=(HashMap)dataList.get(i);
			for(String field:key){
				keyAppend+=mapData.get(field)+",";
			}
			if(keyAppend.endsWith(",")){
				keyAppend=keyAppend.substring(0, keyAppend.length()-1);
			}
			set.add(keyAppend);
		}
		List<String> keyList = new ArrayList<String> ();  
		keyList.addAll(set);  
		return keyList;
	}
	
	public static JSONObject createLineColumnEchartData(List dataList,Map<String, String> paramMap,String targetField,String [] primaryField){
		String chartTitle=paramMap.get("chartTitle")+"";
		String chartField=paramMap.get("chartField")+"";
		String chartType=paramMap.get("chartType")+"";
		String chartPostion=paramMap.get("chartPosition");
		String [] column=chartField.split(",");
		String [] title=chartTitle.split(",");
		String [] type=chartType.split(",");
		String [] postion=chartPostion.split(",");
		if(primaryField!=null){
			return createComplexLineEchartData( dataList,column,title,type,postion, targetField,primaryField);
		}
		return createLineColumnEchartData( dataList,column,title,type,postion, targetField);
	}
	
	public static String getPrimaryKey(HashMap mapData,String [] primaryField){
		String key="";
		if(primaryField!=null){
			for(String field:primaryField){
				key+=mapData.get(field.toUpperCase())==null?"":mapData.get(field.toUpperCase());
			}
			return key;
		}
		
		return null;
	}
	public static String [] getEchartLengend(TreeSet keySet,String [] column,String [] primaryField,String targetField){
		List keyList=new ArrayList<>(keySet);
		int size=keyList.size()==0?1:keyList.size();
		List listColumn=new ArrayList();
		if(size>0){
			for(int i=0;i<keyList.size();i++){
				for(int k=0;k<column.length;k++){
					
						listColumn.add(keyList.get(i)+column[k]);
					
				}
			}
		}else{
			return column;
		}
		String [] newColumn=new String[listColumn.size()];
		newColumn=(String[]) listColumn.toArray(newColumn);
		
		return newColumn;
		
	}
	
	
	public static String [] getEchartType(TreeSet keySet,String [] type){
		List keyList=new ArrayList<>(keySet);
		int size=keyList.size()==0?1:keyList.size();
		List listColumn=new ArrayList();
		if(size>0){
			for(int i=0;i<keyList.size();i++){
				for(int k=0;k<type.length;k++){
					listColumn.add(type[k]);
				}
			}
		}else{
			return type;
		}
		String [] newColumn=new String[listColumn.size()];
		return (String[]) listColumn.toArray(newColumn);
	}
	
	public static ArrayList  getConvertList(List dataList,List targetFieldList,List keyList,String targetField,String []  primaryFiled ,String [] column,String title []){
		ArrayList<HashMap> rowLine = new ArrayList<HashMap>();
		HashMap dataMap=ListRender.listConvertToMap(dataList, targetField);
		for(int k=0;k<targetFieldList.size();k++){
			String temp=targetFieldList.get(k)+"";
			List dataListByTime=(List) dataMap.get(temp);
			
			HashMap mapData=new HashMap();
			for(int p=0;p<keyList.size();p++){
				String keyName=(String) keyList.get(p);
			
				for(String field:column){
					for(int i=0;i<dataListByTime.size();i++){
						HashMap map=(HashMap)dataListByTime.get(i);
						mapData.put(targetField, map.get(targetField.toUpperCase())==null?"":map.get(targetField.toUpperCase()));
						String key=getPrimaryKey(map,primaryFiled)==null?"":getPrimaryKey(map,primaryFiled);
						if(mapData.get(keyName+field)==null){
							mapData.put(keyName+field, "");
						}
						if(key.equals(keyName)){
							mapData.put(keyName+field, map.get(field.toUpperCase())==null?"":map.get(field.toUpperCase()));
						}
					}
					
				}
				
			}
			rowLine.add(mapData);
			
			
		}
		
		return rowLine;
	}
	
	public static void main(String args []){
		
	}
	/**
	 * 
	 * @param dataList
	 * @param column
	 * @param title
	 * @param type
	 * @param position
	 * @param targetField
	 * @return
	 */
	public static JSONObject createComplexLineEchartData(List dataList,String [] column,String title [],String [] type,String [] position ,String targetField,String [] primaryField){
		List newList=new ArrayList();
		TreeSet setXais=new TreeSet();
		TreeSet keySet=new TreeSet();
		long startTime = System.currentTimeMillis();
		for(int i=0;i<dataList.size();i++){
			HashMap mapData=(HashMap)dataList.get(i);
			String key=getPrimaryKey(mapData,primaryField)==null?"":getPrimaryKey(mapData,primaryField);
			keySet.add(key);
			setXais.add(mapData.get(targetField.toUpperCase()));
		}
		long end = System.currentTimeMillis();
		List listData=getConvertList( dataList, new ArrayList<>(setXais),new ArrayList<>(keySet), targetField, primaryField ,column,title );
		long end2 = System.currentTimeMillis();
		System.out.println(end2-startTime);
		for(int i=0;i<listData.size();i++){
			System.out.println(listData.get(i));
		}
		column=getEchartLengend(keySet,column,primaryField,null);
		title=getEchartLengend(keySet,title,primaryField,null);
		type=getEchartType(keySet, type);
		position=getEchartType(keySet, position);
		HashMap mapJson=new HashMap();
		mapJson.put("x", new ArrayList<>(setXais));
		mapJson.put("data",  listData); 
		mapJson.put("title", title);
		mapJson.put("column",  column);
		mapJson.put("type", type);
		mapJson.put("targetField", targetField);
		mapJson.put("postion",  position);
		JSONObject jsonObject = JSONObject.fromObject(mapJson);
		
		return jsonObject;
	}
	
	/**
	 * 
	 * @param dataList
	 * @param column
	 * @param title
	 * @param targetField
	 * @return
	 */
	public static JSONObject createLineColumnEchartData(List dataList,String [] column,String title [],String [] type,String [] position ,String targetField){
		List newList=new ArrayList();
		TreeSet setXais=new TreeSet();
		for(int i=0;i<dataList.size();i++){
			HashMap mapData=(HashMap)dataList.get(i);
			HashMap map=new HashMap();
			for(String field:column){
				map.put(field, mapData.get(field.toUpperCase())==null?"0":mapData.get(field.toUpperCase()));
			}
			map.put(targetField, mapData.get(targetField.toUpperCase())==null?"":mapData.get(targetField.toUpperCase()));
			setXais.add(mapData.get(targetField.toUpperCase()));
			newList.add(map);
		}
		
		
		HashMap mapJson=new HashMap();
		mapJson.put("x", new ArrayList<>(setXais));
		mapJson.put("data",  newList); 
		mapJson.put("title", title);
		mapJson.put("column",  column);
		mapJson.put("type", type);
		mapJson.put("targetField", targetField);
		mapJson.put("postion",  position);
		JSONObject jsonObject = JSONObject.fromObject(mapJson);
		
		return jsonObject;
	}
	
	
	public static JSONObject createLineColumnEchartData(List dataList,String [] column,String targetField){
		List newList=new ArrayList();
		List listXais=new ArrayList();
		for(int i=0;i<dataList.size();i++){
			HashMap mapData=(HashMap)dataList.get(i);
			HashMap map=new HashMap();
			for(String field:column){
				map.put(field, mapData.get(field.toUpperCase())==null?"0":mapData.get(field.toUpperCase()));
			}
			listXais.add(mapData.get(targetField.toUpperCase()));
			newList.add(map);
		}
		
		
		HashMap mapJson=new HashMap();
		mapJson.put("x", listXais);
		mapJson.put("data",  newList); 
		JSONObject jsonObject = JSONObject.fromObject(mapJson);
		
		return jsonObject;
	}
	public static JSONObject createPieEchartData(List dataList,String [] column,String targetField){
		
		List newList=new ArrayList();
		List listXais=new ArrayList();
		for(int i=0;i<dataList.size();i++){
			HashMap mapData=(HashMap)dataList.get(i);
			HashMap map=new HashMap();
			for(String field:column){
				map.put(field, mapData.get(field.toUpperCase())==null?"0":mapData.get(field.toUpperCase()));
			}
			listXais.add(mapData.get(targetField.toUpperCase()));
			newList.add(map);
		}
		
		
		HashMap mapJson=new HashMap();
		mapJson.put("legend", listXais);
		mapJson.put("data",  newList); 
		JSONObject jsonObject = JSONObject.fromObject(mapJson);
		
		return jsonObject;
		
		
	}
	
	public static EchartVo getEchartConfig(Map<String ,String> map,List listChart){
		EchartVo vo = new EchartVo();
		
		List chartSelectedList=new ArrayList();
		for(int i=0;i<listChart.size();i++){
			HashMap mapChartConfig=(HashMap)listChart.get(i);
			if("1".equals(mapChartConfig.get("CHART_SELECTED")+"")){
				chartSelectedList.add(mapChartConfig);
			}
		}
		if("".equals(map.get("chartField")+"")){
			String [] column=new String[chartSelectedList.size()];
			String [] name=new String[chartSelectedList.size()];
			String [] type=new String[chartSelectedList.size()];
			for(int i=0;i<chartSelectedList.size();i++){
				HashMap mapChartConfig=(HashMap)chartSelectedList.get(i);
				column[i]=(mapChartConfig.get("FIELD_NAME")+"").toUpperCase();
				name[i]=mapChartConfig.get("FIELD_TITLE")+"";
				type[i]=mapChartConfig.get("CHART_TYPE")+"";
				
			};
			//vo.setColumn(column);vo.setLegendName(name);vo.setLegendType(type);
		}else{
			String [] column=(map.get("chartField").toString().toUpperCase()).split(",");
			String [] name=(map.get("chartTitle").toString().toUpperCase()).split(",");
			String [] type=new String[name.length];
			for(int i=0;i<listChart.size();i++){
				HashMap mapChartConfig=(HashMap)listChart.get(i);
				for(int k=0;k<column.length;k++){
					String FIELD_NAME=mapChartConfig.get("FIELD_NAME").toString();
					if(FIELD_NAME.toUpperCase().equals(column[k])){
						type[k]=mapChartConfig.get("CHART_TYPE").toString();
					}
				}
			}
			//vo.setColumn(column);vo.setLegendName(name);vo.setLegendType(type);
		};
		
		return vo;
	}
}
