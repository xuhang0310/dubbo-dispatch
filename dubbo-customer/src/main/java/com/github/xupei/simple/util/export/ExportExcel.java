package com.github.xupei.simple.util.export;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;

/**
 * 功能描述：导出excel表格  .  <BR>
 * 开发者: Guangxing   <BR>
 * 时间：2018-5-30 下午2:18:59  <BR>
 * 首次开发时间：2018-5-30 下午2:18:59 <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
public class ExportExcel {
	/*@Autowired
	ExportExcelService exportExcelService;*/
	
	public void  exportExcel(Class serviceImpl,Class serviceExport,String FileName,List titleList,HttpServletResponse response,Map<String, String> paramsMap){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+FileName+".xls");
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		String sql = null;
		try {
			Method implMethod = serviceImpl.getMethod("getSql",Map.class);
			sql = (String) implMethod.invoke(serviceImpl.newInstance(), paramsMap);
			
			Method exportMethod = serviceExport.getMethod("getSql",String.class,List.class,String.class);
			os =  (ByteArrayOutputStreamExe) exportMethod.invoke(serviceExport.newInstance(), sql,titleList,FileName);
			
		//	os = exportExcelService.getFileStream(sql, titleList, FileName);
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}
