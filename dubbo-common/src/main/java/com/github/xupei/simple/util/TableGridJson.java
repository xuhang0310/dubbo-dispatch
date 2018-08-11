package com.github.xupei.simple.util;



public class TableGridJson {
	
public static String getTableGridJson(TableGrid table){
		
		String append="{";
		if(table.getTitle()!=null&&!"".equals(table.getTitle())){
			String unit="";
			if(table.getUnit()!=null&&!"".equals(table.getUnit())){
				unit=table.getUnit();
			}
			append+=" title:'"+table.getTitle()+"<br/>"+unit+"'  ";
			
		}
		if(table.getIsPrimary()!=null&&"1".equals(table.getIsPrimary())){
			append+=", checkbox:"+true+"  ";
		}
		if(table.getField()!=null&&!"".equals(table.getField())){
			append+=" ,field:'"+table.getField().toUpperCase()+"'  ";
		}
		if(table.getAlign()!=null&&!"".equals(table.getAlign())){
			append+=", align:'"+table.getAlign()+"'  ";
		}else{
			append+=", align:'center'  ";
		}
		if(table.getWidth()!=null&&!"".equals(table.getWidth())){
			append+=", width:'"+table.getWidth()+"'  ";
		}
		if(table.getSortable()!=null&&!"".equals(table.getSortable())){
			append+=", sortable:"+table.getSortable()+"  ";
		}
		if(table.getFormatter()!=null&&!"".equals(table.getFormatter())){
			append+=", formatter:"+table.getFormatter()+"  ";
		}
		
		
		append+=" } ";
		return append;
		
	}
	
	public static void main(String args []){
		TableGrid tableGrid=new TableGrid();
		tableGrid.setAlign("center");
		tableGrid.setTitle("����վ");
		tableGrid.setField("nodename");
		tableGrid.setSortable("true");
		System.out.println(TableGridJson.getTableGridJson(tableGrid));
	}

}
