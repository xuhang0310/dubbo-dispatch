package testQRcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



/**
 * 功能描述：测试java 后台生成二维码图片  .  <BR>
 * 开发者: Guangxing   <BR>
 * 时间：2018-7-24 下午3:34:34  <BR>
 * 首次开发时间：2018-7-24 下午3:34:34 <BR>
 * 版本：V1.0
 */
public class TestQRCode {
//	public static void main(String[] args) {
//		   Qrcode qrcode = new Qrcode();
//		   qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）
//		   qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符
//		   qrcode.setQrcodeVersion(7);//版本
//		   //生成二维码中要存储的信息
//		   String qrData = "测试数据测试数据";
//		   //设置一下二维码的像素
//		   int width = 300;
//		   int height = 300;
//		   BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		   //绘图
//		   Graphics2D gs = bufferedImage.createGraphics();
//		   gs.setBackground(Color.WHITE);
//		   gs.setColor(Color.BLACK);
//		   gs.clearRect(0, 0, width, height);//清除下画板内容
//
//		   //设置下偏移量,如果不加偏移量，有时会导致出错。
//		   int pixoff = 2;
//		   byte[] d = null;
//		   try {
//		   d = qrData.getBytes("gb2312");
//		   } catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		   if(d.length > 0 && d.length <120){
//			   boolean[][] s = qrcode.calQrcode(d);
//			   for(int i=0;i<s.length;i++){
//				   for(int j=0;j<s.length;j++){
//					   if(s[j][i]){
//						   gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
//					   }
//				   }
//			   }
//		   }
//		   gs.dispose();
//		   bufferedImage.flush();
//		   try {
//			ImageIO.write(bufferedImage, "png", new File("G:/qrcode/qrcode.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
