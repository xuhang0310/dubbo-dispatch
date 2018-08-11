package com.github.xupei.simple.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.IScadaStationService;
import com.github.xupei.simple.json.JsonUtil;

@Controller
@RequestMapping(value = "/scadaStation")
public class ScadaStationController extends BaseController {

	@Autowired
	IScadaStationService scadaStationService;

	/**
	 * 页面初始化方法
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getScadaStationList.do")
	public void getScadaStationList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		Map map = scadaStationService.getScadaStationList(paramsMap);
		JsonUtil.returnObjectJson(map, response);

	}

	@RequestMapping(value = "/scadaStationListLayer.do")
	public ModelAndView scadaStationListLayer(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/bas/node/nodeList");

		model.addObject("jsonTableGrid", super.getTableGrid(paramsMap));

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
	@RequestMapping(value = "/updateScadaStationList.do")
	public ModelAndView updateScadaStation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		System.out.println("=======updateScadaStation========");
		String id = paramsMap.get("id");
		List list = null;
		List orgList = null;
		List feednodeList = null;
		List feedList = null;
		List nodeList = null;
		try {
			list = scadaStationService.editScadaStation(id);
			orgList = scadaStationService.orgList();
			feedList = scadaStationService.feedList();
			nodeList = scadaStationService.nodeList();
			feednodeList = scadaStationService.feednodeList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mad = new ModelAndView("/jsp/bas/station/editScadaStation");
		// 将数据存入modelMap
		mad.addObject("station", list.get(0));
		mad.addObject("orgobj", orgList);
		mad.addObject("feednodeobj", feednodeList);//换热站
		mad.addObject("nodeobj", nodeList);//换热站
		mad.addObject("feedobj", feedList);//热源
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
	@RequestMapping(value = "/addScadaStationList.do")
	public ModelAndView addScadaStationList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		System.out.println("=======updatenode========");
		String id = paramsMap.get("id");
		List orgList = null;
		List feednodeList = null;
		List feedList = null;
		List nodeList = null;
		try {
			orgList = scadaStationService.orgList();
			feedList = scadaStationService.feedList();
			nodeList = scadaStationService.nodeList();
			feednodeList = scadaStationService.feednodeList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mad = new ModelAndView("/jsp/bas/station/editScadaStation");
		// 将数据存入modelMap
		mad.addObject("orgobj", orgList);
		mad.addObject("feednodeobj", feednodeList);
		mad.addObject("nodeobj", nodeList);//换热站
		mad.addObject("feedobj", feedList);//热源
		return mad;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value = "/editScadaStation.do")
	public void editScadaStation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {

			scadaStationService.editScadaStation(paramsMap);
			JsonUtil.returnnBaseJson(true, "修改成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "修改失败", response);
			e.printStackTrace();
		}

	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value = "/saveScadaStation.do")
	// ,method=RequestMethod.POST
	public void saveScadaStation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {

			scadaStationService.saveScadaStation(paramsMap);
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
	@RequestMapping(value = "/deleteScadaStation.do")
	public void deleteScadaStation(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {
			String id = paramsMap.get("id") + "";
			scadaStationService.deleteScadaStation(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}

	}

}
