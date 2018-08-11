package testQRcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

/**
 * 功能描述：图片导入到excel测试  .  <BR>
 * 开发者: Guangxing   <BR>
 * 时间：2018-7-24 下午4:31:49  <BR>
 * 首次开发时间：2018-7-24 下午4:31:49 <BR>
 * 版本：V1.0
 */
public class ExcelImageTest {
    public static void main(String[] args) {
         FileOutputStream fileOut = null;   
         BufferedImage bufferImg = null;   
         BufferedImage bufferImg1 = null;   
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray  
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();   
            ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();   
            bufferImg = ImageIO.read(new File("G:/qrcode/qrcode.png"));   
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            bufferImg1 = ImageIO.read(new File("G:/qrcode/东北郊.png"));   
            ImageIO.write(bufferImg1, "jpg", byteArrayOut1);
            
            HSSFWorkbook wb = new HSSFWorkbook();   
            HSSFSheet sheet1 = wb.createSheet("test picture");
            //字体样式设置
            HSSFFont font = wb.createFont(); 
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 14);// 设置字体大小
            
            //设置表头样式
            Row row = sheet1.createRow((short) 0);
            row.setHeight((short) (2*256));//这是行的高度
            sheet1.setColumnWidth(0, 20*256);//设置列的宽度
            sheet1.setColumnWidth(1, 30*256);//设置列的宽度
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
            
    		
    		//画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet1.createDrawingPatriarch(); 
            HSSFClientAnchor anchor = null;
           
    		//第一张图片
            //设置存放数据的单元格高度或者宽度
            sheet1.createRow(1).setHeightInPoints((short) (8*20));//这是行的高度
            sheet1.setColumnWidth(1, 30*256);//设置列的宽度
            //anchor主要用于设置图片的属性
            anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 1, 1, (short) 2, 2);   
            anchor.setAnchorType(3);   
            //插入图片  
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
            
            //第二章图片
            //设置存放数据的单元格高度或者宽度
            sheet1.createRow(2).setHeightInPoints((short) (8*20));//这是行的高度
            anchor = new HSSFClientAnchor(1, 1, 0, 0,(short) 1, 2, (short) 2, 3);   
            //插入图片  
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut1.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
            
            
            
            
            
            // 写入excel文件   
            fileOut = new FileOutputStream("G:/测试Excel.xls");   
             wb.write(fileOut);   
             System.out.println("----Excle文件已生成------");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fileOut != null){
                 try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}