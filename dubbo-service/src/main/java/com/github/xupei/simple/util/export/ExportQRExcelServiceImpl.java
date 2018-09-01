package com.github.xupei.simple.util.export;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportQRExcelService;
import com.github.xupei.simple.dao.BaseDao;
import com.github.xupei.simple.util.SqlParser;
/*import com.swetake.util.Qrcode;*/

/**
 * 功能描述：支持将二维码图片信息导出到excel  .  <BR>
 * 开发者: Guangxing   <BR>
 * 时间：2018-7-24 下午1:22:48  <BR>
 * 首次开发时间：2018-7-24 下午1:22:48 <BR>
 * 版本：V1.0
 */
public class ExportQRExcelServiceImpl extends BaseDao implements ExportQRExcelService{

	@Resource(name ="baseDao")
	private BaseDao baseDao;
	 public BufferedImage bufferImg = null; 
	 int width;//记录二维码宽度
	 int height;//记录二维码高度
	
	/**
	 * 实现说明： 参数fileName是指excel表格工作表名称！！！ . <BR>
	 * @see com.github.xupei.simple.util.export.ExportQRExcelServiceImpl
	 * @Author: Guangxing <BR>
	 * @Datetime：2018-7-24 下午2:15:25 <BR>
	 */
	public ByteArrayOutputStreamExe getQRFileStream(String sql,List titleList,Map<String,String> paramMap,String fildName,String flag) {
			int columnCount = 0;
			OutputStream os = null;
			ByteArrayOutputStreamExe baos = new ByteArrayOutputStreamExe();
			
			//根据flag参数值来设置不同的二维码像素
			switch(flag){
			      case "NBSB": width = 175; height = 175; break;
			      case "REXX": width = 118; height = 118; break;
			      case "HRZXX": width = 148; height = 148; break;
			      default : width = 175; height = 175; break;
			}
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
				HSSFWorkbook wb = new HSSFWorkbook();
				// 记录总数
				int i = 0;
				 // 创建工作表
				HSSFSheet sheet = wb.createSheet("sheet");//表格文件名称
				// 写表头
		        setCellValue(sheet,titleList,wb);
				//画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
	            HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
	            HSSFClientAnchor anchor = null;
	            int a= resultSet.getRow();
				while (resultSet.next()) {
					
				    // 记录号
				    i++;
				    // 创建行
				    Row row;
				    if ( i != 0) {
				        row = sheet.createRow(i);
				    } else {
				        row = sheet.createRow(2);
				    }
				    // 依据列名获取各列值
				    //titleList:[{FIELD=feedname, TITLE=热源名称}, {FIELD=orgname, TITLE=所属公司}, {FIELD=sjfh, TITLE=设计负荷}, {FIELD=cnmj, TITLE=采暖面积}, {FIELD=szwz, TITLE=所在位置}]
				    StringBuffer qrData = new StringBuffer();
				    String dataName=null;//用来存储基础信息中的唯一标识符
				    for (int j = 0; j < titleList.size(); j++) {
				    	Map map = new HashMap<String,String>();
				    	map = (Map) titleList.get(j);
				    	qrData.append(map.get("TITLE").toString()+":");
				    	qrData.append(resultSet.getString(map.get("FIELD").toString())+";");
				    	if(map.get("FIELD").toString().equals(fildName)){
				    		dataName = resultSet.getString(fildName);
				    	}
				    }
				    setDataValue(i,sheet,patriarch,qrData,dataName,anchor,wb);
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
	//设置填入具体表信息（涉及基础信息名称以及信息二维码）
	public void setDataValue(int n,HSSFSheet sheet,HSSFPatriarch patriarch,StringBuffer qrData,String dataName,HSSFClientAnchor anchor,HSSFWorkbook wb){
		 //创建列（一共两列）
		Row row = sheet.createRow(n);
        Cell cell = row.createCell((short) 0);
        cell.setCellValue(dataName);
        //第一张图片
        //设置存放数据的单元格高度或者宽度
        row.setHeightInPoints((short) (8*20));//这是行的高度
        sheet.setColumnWidth(1, 30*256);//设置列的宽度
        //anchor主要用于设置图片的属性
        anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 1, n, (short) 2, n+1);   
        anchor.setAnchorType(3);   
        //插入图片  
        patriarch.createPicture(anchor, wb.addPicture(initQrcode(qrData).toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
	}
	
	
	//设置导出excel表格表头信息
	public void setCellValue(Sheet sheet,List titleList,HSSFWorkbook wb){
		 //字体样式设置
        HSSFFont font = wb.createFont(); 
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);// 设置字体大小
        //设置表头样式
		Row row = sheet.createRow(0);
        row.setHeight((short) (2*256));//这是行的高度
        sheet.setColumnWidth(0, 20*256);//设置列的宽度
        sheet.setColumnWidth(1, 30*256);//设置列的宽度
        CellStyle style = wb.createCellStyle();//具体样式变量
        style = wb.createCellStyle();
        style.setFont(font);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());//单元格背景颜色
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Cell cell1 = row.createCell((short) 0);
		Cell cell2 = row.createCell((short) 1);
		cell1.setCellValue("基础信息名称");
		cell1.setCellStyle(style);
		cell2.setCellValue("二维码");
		cell2.setCellStyle(style);
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
	//生成二维码图片信息
	public ByteArrayOutputStream initQrcode(StringBuffer qrData){
		return null;
		  /* ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();   
		   Qrcode qrcode = new Qrcode();
		   qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）
		   qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符
		   qrcode.setQrcodeVersion(0);//版本
		   //生成二维码中要存储的信息
		  // String qrData = "测试数据测试数据";
		   BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		   //绘图
		   Graphics2D gs = bufferedImage.createGraphics();
		   gs.setBackground(Color.WHITE);
		   gs.setColor(Color.BLACK);
		   gs.clearRect(0, 0, width, height);//清除下画板内容
		   
		   //设置下偏移量,如果不加偏移量，有时会导致出错。
		   int pixoff = 2;
		   byte[] d = null;
		   try {
		//	   System.out.println(qrData.toString());
		   d = qrData.toString().getBytes("gb2312");
			  // String aaa = "换热站名称:福锦园换热站;所属公司:港益公司;所属热源:南疆电厂;设计负荷:100;采暖面积:1000;管理方式:厂管;采暖方式:地暖;节能方式:null";
			 //  d= aaa.getBytes("gb2312");
		   } catch (IOException e) {
				e.printStackTrace();
		}
		   if(d.length > 0){
			   boolean[][] s = qrcode.calQrcode(d);
			   for(int i=0;i<s.length;i++){
				   for(int j=0;j<s.length;j++){
					   if(s[j][i]){
						   gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
					   }
				   }
			   }
		   }
		   gs.dispose();
		   bufferedImage.flush();
		   try {
			ImageIO.write(bufferedImage, "png", byteArrayOut);
			return byteArrayOut;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}*/
	}
}
