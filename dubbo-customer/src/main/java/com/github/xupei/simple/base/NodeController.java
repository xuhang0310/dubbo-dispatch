package com.github.xupei.simple.base;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.INodeService;
import com.github.xupei.dubbo.api.IPictureService;
import com.github.xupei.dubbo.api.util.export.ByteArrayOutputStreamExe;
import com.github.xupei.dubbo.api.util.export.ExportExcelService;
import com.github.xupei.dubbo.api.util.export.ExportQRExcelService;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.util.DateChosenUtil;

@Controller
@RequestMapping(value = "/node")
public class NodeController extends BaseController {

	@Autowired
	INodeService nodeService;
	
	@Autowired
	IPictureService pictureService;
	@Autowired
	ExportQRExcelService exportQRExcelService;
	
	
	@Autowired
	ExportExcelService exportExcelService;
	/**
	 * 页面初始化方法
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getNodeList.do")
	public void getNodeList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		Map map = nodeService.getNodeList(paramsMap);
		JsonUtil.returnObjectJson(map, response);

	}

	@RequestMapping(value = "/nodeListLayer.do")
	public ModelAndView nodeListLayer(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		List sysorg=null;
		try {
			sysorg=nodeService.getSysOrg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/bas/node/nodeList");
		model.addObject("jsonTableGrid", super.getTableGrid(paramsMap));
		model.addObject("sysorg", sysorg);
		return model;
	}

	/**
	 * 修改查询方法
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "/updateNodeList.do")
	public ModelAndView updateNode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		System.out.println("=======updatenode========");
		String nodeid = paramsMap.get("nodeid");
		List list = null;
		List orgList = null;
		List feedList = null;
		List dicList=null;
		List picList=null;
		List jz1=null;
		List jz2=null;
		List jz3=null;
		List jz4=null;
		List rzb=null;
		List szb=null;
		List dzb=null;
		try {
			list = nodeService.editNode(nodeid);
			orgList = nodeService.orgList();
			feedList = nodeService.feedList();
			dicList= nodeService.getDataDicList();
			picList=pictureService.getPicture(null);
			jz1=nodeService.getJz(nodeid,"1");
			jz2=nodeService.getJz(nodeid,"2");
			jz3=nodeService.getJz(nodeid,"3");
			jz4=nodeService.getJz(nodeid,"4");
			rzb=nodeService.getZb(nodeid,"15");
			szb=nodeService.getZb(nodeid,"16");
			dzb=nodeService.getZb(nodeid,"17");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mad = new ModelAndView("/jsp/bas/node/editNode");
		// 将数据存入modelMap
		mad.addObject("node", list.get(0));
		mad.addObject("orgobj", orgList);
		mad.addObject("feedList", feedList);
		mad.addObject("nodeid", nodeid);
		mad.addObject("dicList",dicList);
		mad.addObject("picList",picList);
		if(jz1.size()>0){
		mad.addObject("jz1", jz1.get(0));
		}
		if(jz2.size()>0){
		mad.addObject("jz2", jz2.get(0));
		}
		if(jz3.size()>0){
		mad.addObject("jz3", jz3.get(0));
		}
		if(jz4.size()>0){
		mad.addObject("jz3", jz4.get(0));		
		}
		if(rzb.size()>0){
		mad.addObject("rzb",rzb.get(0));
		}
		if(szb.size()>0){
		mad.addObject("szb",szb.get(0));
		}
		if(dzb.size()>0){
		mad.addObject("dzb",dzb.get(0));
		}
		return mad;
	}

	/**
	 * 新增跳转方法
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "/addNodeList.do")
	public ModelAndView addNodeList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		
		String nodeid = paramsMap.get("nodeid");
		ModelAndView mad = new ModelAndView("/jsp/bas/node/editNode");
		List orgList = null;
		List feedList = null;
		try {
			orgList = nodeService.orgList();
			feedList = nodeService.feedList();
			mad.addObject("dicList", nodeService.getDataDicList());
			mad.addObject("picList", pictureService.getPicture(null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将数据存入modelMap
		mad.addObject("orgobj", orgList);
		mad.addObject("feedList", feedList);
		return mad;
	}

	/**
	 * 跳转到批量修改页面
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "/batchAddNodeList.do")
	public ModelAndView batchAddNodeList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		List orgList = null;
		List feedList = null;
		String nodecode=paramsMap.get("nodecode");
		ModelAndView mad = new ModelAndView("/jsp/bas/node/batchEditNode");
		try {
			orgList = nodeService.orgList();
			feedList = nodeService.feedList();
			mad.addObject("dicList", nodeService.getDataDicList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mad.addObject("orgobj", orgList);
		mad.addObject("feedList", feedList);
		mad.addObject("nodecode", nodecode);
		return mad;
	}
	
	/**
	 * 批量修改
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/batchUpdateNode.do")
	public void batchUpdateNode(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			nodeService.batchUpdateNode(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value = "/editNode.do")
	public void editNode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		try {

			nodeService.editNode(paramsMap);
			JsonUtil.returnnBaseJson(true, "修改成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "修改失败", response);
			e.printStackTrace();
		}

	}

	

	@RequestMapping(value="/toEditNode.do")
	public ModelAndView toEditNode(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){

	      ModelAndView ParamMap = new ModelAndView("/jsp/bas/node/editNode");
	      return ParamMap;
	  }
	
	/**
	 * 新增
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value = "/saveNode.do")
	// ,method=RequestMethod.POST
	public void saveNode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {

			nodeService.saveNode(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
			e.printStackTrace();
		}

	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value = "/deleteNode.do")
	public void deleteNode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {
			String nodeid = paramsMap.get("nodeid") + "";
			nodeService.deleteNode(nodeid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}

	}
	
	
	@RequestMapping("/queryNodeByPid.do")
	public void queryNodeByPid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=nodeService.queryNodeByPid(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 导出
	 */
	@RequestMapping(value = "/exportExcel.do",method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		String sql = nodeService.getSql(paramsMap);
		String FileName = "换热站基础信息";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=node.xls");
		
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		os = exportExcelService.getFileStream(sql, titleList, FileName,paramsMap);
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 批量导出二维码图片信息到excel
	 */
	@RequestMapping(value = "/exportQRExcel.do",method = RequestMethod.GET)
	public void exportQRExcel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		paramsMap=DateChosenUtil.returnDateType(paramsMap);
		
		List titleList = super.getTableGridTitle(paramsMap);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename="+URLEncoder.encode("换热站二维码基础信息.xls","UTF-8"));
		ByteArrayOutputStreamExe os = new ByteArrayOutputStreamExe();
		// fildName 记录需要导出基础信息中的唯一标识主键字段名  比如 热源基础信息 中把“热源名称”作为显示主键，
		//则 fildName="feedname";
		String fildName="nodename";
		os = exportQRExcelService.getQRFileStream(nodeService.getSql(paramsMap),titleList,paramsMap,fildName,"HRZXX");
		try {
			//输出文件
			response.getOutputStream().write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getNodeSummary.do")
	public void getNodeSummary(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=nodeService.getSummaryMap(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
