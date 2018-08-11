package com.github.xupei.simple.util.export;

import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.util.SqlParser;

/**
 * 功能描述：表格导出功能 . <BR>
 * 开发者: Guangxing <BR>
 * 时间：2018-5-29 下午3:11:54 <BR>
 * 首次开发时间：2018-5-29 下午3:11:54 <BR>
 * 版本：V1.0
 */
public class ExportExcelServiceImpl extends BaseDao implements ExportExcelService{

	@Resource(name ="baseDao")
	private BaseDao baseDao;
	/**
	 * 实现说明： 参数fileName是指excel表格工作表名称！！！ . <BR>
	 * @see com.github.xupei.simple.util.export.ExportQRExcelServiceImpl
	 * @Author: Guangxing <BR>
	 */
	public ByteArrayOutputStreamExe getFileStream(String sql,List titleList,String fileName,Map<String,String> paramMap) {
			int columnCount = 0;
			OutputStream os = null;
			ByteArrayOutputStreamExe baos = new ByteArrayOutputStreamExe();
			try {
				paramMap = SqlParser.escape4select(paramMap);
				sql = SqlParser.parse(sql, paramMap);
				
				ResultSet resultSet = getResultSet(sql);
				// 获取结果集表头
				ResultSetMetaData md = resultSet.getMetaData();
				columnCount = md.getColumnCount();
				JSONArray columnName = new JSONArray();
				for (int i = 1; i <= columnCount; i++) {
				    JSONObject object = new JSONObject();
				    object.put("hahah",md.getColumnName(i));
				    columnName.add(object);
				}
				int columnNameSize = columnName.size();
				// 读取工作薄
				Workbook wb = new SXSSFWorkbook(500);
				// 记录总数
				int i = 0;
				Sheet sheet = null;
				
				while (resultSet.next()) {
				    // 记录号
				    i++;
				    int index = i;
				    // logger.info("index:"+index);

				    if ( index == 1){
				        // 创建工作表
				        //sheet = wb.createSheet("sheet"+fileName);//表格文件名称
				    	 sheet = wb.createSheet(fileName);//表格文件名称
				        // 写表头
				        setCellValue(sheet,titleList);
				    }
				    // 创建行
				    Row row;
				    if ( index != 0) {
				        row = sheet.createRow(index);
				    } else {
				        row = sheet.createRow(2);
				    }
				    // 依据列名获取各列值
				    for (int j = 0; j < titleList.size(); j++) {
				    	Map map = new HashMap<String,String>();
				    	map = (Map) titleList.get(j);
				    	 String value = resultSet.getString(map.get("FIELD").toString());
				       //String value = resultSet.getString(j + 1);
				        //创建列
				        Cell cell = row.createCell((short) j);
				        cell.setCellValue(value);
				    }
				}
				wb.write(baos);
				return baos;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("空空…………………………………………………………");
			return null;
	}
	//设置导出excel表格表头信息
	public void setCellValue(Sheet sheet,List titleList){
		Row row = sheet.createRow(0);
		
		for(int i=0; i<titleList.size();i++){
			Map map = new HashMap<String,String>();
			map = (Map) titleList.get(i);
			//Cell cell = row.createCell(i);
			//cell.setCellValue(map.get("TITLE").toString());
			row.createCell(i).setCellValue(map.get("TITLE").toString());
		}
	}
	//获取结果集
	public ResultSet getResultSet(String sql){
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			return super.jdbcTemplate
						  .getDataSource()
						  .getConnection()
						  .prepareStatement(sql)
						  .executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
