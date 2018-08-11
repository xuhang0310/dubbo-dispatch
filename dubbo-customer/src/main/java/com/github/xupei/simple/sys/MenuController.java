package com.github.xupei.simple.sys;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.dubbo.api.ISysMenuService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;

/**
 * 菜单管理
 * @author wjh
 *
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

	@Autowired
	ISysMenuService menuService;

	/**
	 * 页面初始化方法
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @throws Exception
	 */
	@RequestMapping("/getMenuList.do")
	public void getNodeList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) throws Exception {
		Map map = menuService.getMenuList(paramsMap);
		System.out.println(System.getProperty("java.io.tmpdir"));
		JsonUtil.returnObjectJson(map, response);

	}
	
	/**
	 * 左侧树
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping("/getSysMenuTreeData.do")
	public void getSysMenuTreeData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=menuService.getMenuListForTree(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/menuListLayer.do")
	public ModelAndView menuListLayer(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/sys/menu/sysMenuList");

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
	@RequestMapping(value = "/updateMenuList.do")
	public ModelAndView updateMenue(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		System.out.println("=======updatenode========");
		String id = paramsMap.get("menuid");
		List list = null;
		List orgList = null;
		List feedList = null;
		List parentList = null;
		try {
			list = menuService.editMenu(id);
			orgList = menuService.orgList();
			feedList = menuService.feedList();
			parentList = menuService.parentList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mad = new ModelAndView("/jsp/sys/menu/editSysMenu");
		// 将数据存入modelMap
		mad.addObject("menu", list.get(0));
		mad.addObject("orgobj", orgList);
		mad.addObject("feedobj", feedList);
		mad.addObject("parentList", parentList);
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
	@RequestMapping(value = "/addMenuList.do")
	public ModelAndView addMenuList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {
		
		String id = paramsMap.get("menuid");
		List orgList = null;
		List feedList = null;
		List parentList = null;
		try {
			orgList = menuService.orgList();
			feedList = menuService.feedList();
			parentList = menuService.parentList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mad = new ModelAndView("/jsp/sys/menu/editSysMenu");
		// 将数据存入modelMap
		mad.addObject("orgobj", orgList);
		mad.addObject("feedobj", feedList);
		mad.addObject("parentList", parentList);
		return mad;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value = "/editMenu.do")
	public void editMenu(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {

			menuService.editMenu(paramsMap);
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
	@RequestMapping(value = "/saveMenu.do")
	// ,method=RequestMethod.POST
	public void saveMenu(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {

			menuService.saveMenu(paramsMap);
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
			System.out.println(e.getLocalizedMessage());
		}

	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value = "/deleteMenu.do")
	public void deleteNode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap) {

		try {
			String menuid = paramsMap.get("menuid") + "";
			menuService.deleteMenu(menuid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}

	}

}
