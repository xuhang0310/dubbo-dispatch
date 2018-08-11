package com.github.xupei.simple.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.xupei.dubbo.api.IBaseService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.util.TableGrid;
import com.github.xupei.simple.util.TableGridJson;


@Service
public class BaseServiceImpl implements IBaseService{
		
	@Resource(name="baseDao")
	private BaseDao baseDao;


	@Cacheable(value="pageConfigCache")
	public String getTableGrid(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return getTableGrid( map.get("pageid"));
	}
	//@Cacheable(value="pageConfigCache")
	public List getTableGridTitle(Map<String, String> map) throws Exception {
		String sql="select  fieldname field , caption title  from col_config_page where 1=1 and ishiddle='false'  and pageid='"+map.get("pageid").toString()+"'  order by orderid ";
		List list=baseDao.findAll(sql);
		return list;
	}
	
	public List getTableGridTitleForExcel(Map<String, String> map) throws Exception {
		String sql="select  fieldname field , caption title  from col_config_page where 1=1 and ishiddle='false'   and renderer is null   and pageid='"+map.get("pageid").toString()+"'  order by orderid ";
		List list=baseDao.findAll(sql);
		return list;
	}
	
	public String getTableGrid(String pageid) throws Exception{
		String sql="select  fieldname field , caption title, table_align align, table_sort sortable, ISPRIMARY ," +
				" renderer formatter,unit,width  from col_config_page where 1=1 and ishiddle='false'  [ and pageid='{pageid}' ] order by orderid ";
		Map map=new HashMap();
		map.put("pageid", pageid);
		List list=baseDao.findAllObject(sql,map,TableGrid.class);
		
		String json="[[";
		json+="{title : '序号', align: 'center', width: 40, " +
				        " formatter: function (value, row, index) { "+
						" 		var pageSize=$('#table').bootstrapTable('getOptions').pageSize;  " +
						" 		var pageNumber=$('#table').bootstrapTable('getOptions').pageNumber;"+
                        " 		return pageSize * (pageNumber - 1) + index + 1; " +
                        "       } " +
                        " },";
		for(int i=0;i<list.size();i++){
			TableGrid tableGird=(TableGrid)list.get(i);
			json+=TableGridJson.getTableGridJson(tableGird)+" ";
			if(i<list.size()-1){
				json+=",";
			}
		
		}
		json+="]]";
		
		return json.toString();
	}

	@Override
	//@Cacheable(value="cacheTool",key="#key")
	public List getTableList() throws Exception {
		// TODO Auto-generated method stub
		String sql="select table_name from user_tables";
		return baseDao.findAll(sql);
	}

	@Override
	//@Cacheable(value="cacheTool",key="#key")
	public List getTableColumnList(String key) throws Exception {
		// TODO Auto-generated method stub
		String sql="select a.TABLE_NAME ,a.COLUMN_NAME,a.DATA_TYPE,b.comments   from user_tab_columns a,user_col_comments b where a.TABLE_NAME=b.table_name and a.COLUMN_NAME=b.column_name  and a.table_name ='"+key+"'  ";
		return baseDao.findAll(sql);
	}

	@Override
	public List getChartLengend(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from col_config_page where 1=1 and ischart=1   [ and pageid='{pageid}' ]";
		return baseDao.findAll(sql, map);
	}

	
	
	
}
