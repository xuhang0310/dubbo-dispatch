package com.github.xupei.simple.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ListRender {
	
	public static HashMap<String,List> listConvertToMap(List list,String key){
		HashSet<String> set=new HashSet();
		for(int i=0;i<list.size();i++){
			HashMap mapConfig=(HashMap)list.get(i);
			set.add(mapConfig.get(key.toUpperCase())+"");
		}
		HashMap<String,List> mapConfigNew=new HashMap();
		for (String feedid : set) {
			List listConfigNew=new ArrayList();
			for(int i=0;i<list.size();i++){
				HashMap mapConfig=(HashMap)list.get(i);
				String feedidConfig=mapConfig.get(key.toUpperCase())+"";
				
				if(feedid.equals(feedidConfig)){
					listConfigNew.add(mapConfig);
				}
			}
			mapConfigNew.put(feedid, listConfigNew);
		}
		return mapConfigNew;
	}
	
	public static List renderRealList(HashMap gridMap,HashMap mapWarnConfig,String key,String value){
		List listReal=(List) gridMap.get("rows");
		
		for(int i=0;i<listReal.size();i++){
			HashMap mapGrid=(HashMap)listReal.get(i);
			String feedIdGrid=mapGrid.get(key)+"";
			List listWarnConfig=(List) mapWarnConfig.get(feedIdGrid);
			if(listWarnConfig!=null){
				for(int j=0;j<listWarnConfig.size();j++){
					HashMap mapParam=(HashMap)listWarnConfig.get(j);
					String fieldname=mapParam.get("FIELDNAME")+"";
					String stationid=mapGrid.get("STATIONID")+"";
					String feedname=mapGrid.get(value)+"";
					Object obj=mapGrid.get(fieldname.toUpperCase())+"";
					if(obj!=null){
						Double realNum=Double.parseDouble(obj+"");
						Double maxnum=Double.parseDouble(mapParam.get("MAXNUM")+"");
						Double minnum=Double.parseDouble(mapParam.get("MINNUM")+"");
						if(realNum>maxnum){
							mapGrid.put(fieldname.toUpperCase(), "<span onclick=\"getWarningDetail('"+stationid+"','"+fieldname+"','"+feedname+"')\" style=\"color:#dd514c\">"+realNum+"</span>");
						}
						if(realNum<minnum){
							mapGrid.put(fieldname.toUpperCase(), "<span onclick=\"getWarningDetail('"+stationid+"','"+fieldname+"','"+feedname+"')\" style=\"color:#5eb95e\">"+realNum+"</span>");
						}
					}
					
				}
			}
		}
		
		return listReal;
	}

}
