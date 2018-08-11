package com.github.xupei.dubbo.api.util.export;

import java.util.List;
import java.util.Map;



/**
 * 功能描述：表格导出功能  .  <BR>
 * 开发者: Guangxing   <BR>
 * 时间：2018-5-29 下午3:11:54  <BR>
 * 首次开发时间：2018-5-29 下午3:11:54 <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
public interface ExportExcelService {
	public ByteArrayOutputStreamExe getFileStream(String sql,List titleList,String fileName,Map<String,String> paramMap);
}
