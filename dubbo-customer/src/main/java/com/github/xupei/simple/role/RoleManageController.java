package com.github.xupei.simple.role;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.github.xupei.dubbo.api.IRoleManageService;
import com.github.xupei.dubbo.api.IRoleManageService;
import com.github.xupei.dubbo.bean.SysRole;
import com.github.xupei.dubbo.bean.SysUser;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonResProcessor;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;


@Controller
@RequestMapping(value="/rolemanage")
public class RoleManageController extends BaseController {
	
	@Autowired
	IRoleManageService iRoleManageService;
	public SysRole sysrole = new SysRole();
	
	public String permissionTreeData;
	public String getPermissionTreeData() {
		return permissionTreeData;
	}
	public void setPermissionTreeData(String permissionTreeData) {
		this.permissionTreeData = permissionTreeData;
	}
	@ResponseBody
	@RequestMapping(value="/selectAll.do")
	public ModelAndView selectAll(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      List list = null;
	      try {
	      list = iRoleManageService.selectAll();

	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }	     	 
	      ModelAndView ParamMap = new ModelAndView("/jsp/role/addRole");
	      //将数据存入modelMap
	      ParamMap.addObject("role", list);
	
	      return ParamMap;
	  }
	@RequestMapping("/getRoleManageList.do")
	public void getRoleManageList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=iRoleManageService.getRoleManageList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	

	@ResponseBody
	@RequestMapping(value="/saveRole.do",method=RequestMethod.POST)
	public void saveRole(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "bz", required = false)String bz,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			iRoleManageService.saveRole(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/saveRoleById.do",method=RequestMethod.POST)
	public void saveRoleById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			iRoleManageService.saveRoleById(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
		}
		
	}
	
	
	@RequestMapping(value="/deleteRole.do")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String id=paramsMap.get("id");
			iRoleManageService.deleteRole(id);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	@ResponseBody
	@RequestMapping(value="/updateRole.do")
	public ModelAndView updateRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      String roleid = paramsMap.get("roleid");
	      List list = null;
	      List org=null;
	      try {
	    	  list = iRoleManageService.editRole(roleid);
	    	  org=iRoleManageService.selectAll();
	    	  System.out.println(list.size());
	      } catch (Exception e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
		
	      ModelAndView rolemap = new ModelAndView("/jsp/role/editRole");
	      //将数据存入modelMap
	      rolemap.addObject("role", list.get(0));
	      rolemap.addObject("org",org);
	      return rolemap;
	  }
	
	
	@ResponseBody
	@RequestMapping(value="/initPermissions.do")
	public ModelAndView initPermissions(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
	      String roleid = paramsMap.get("roleid");
	      List list = null;
	      Map map=new HashMap<>();
	      try {
	    	  String rolepermission=iRoleManageService.getPermission(roleid);
	    	  Map rolemap=iRoleManageService.StringToMap(rolepermission);
	    	// 组织菜单树根目录名称
	  		String programName="实时监控系统";
	  	// 组织菜单树结构
			StringBuffer result = new StringBuffer("[");
			StringBuffer result2;
			result.append(",{'menuid':0, 'parentid':'', 'text':'"+programName+"', 'type':'root', 'ischecked':false, 'isexpand':true, 'children':[");
			result2=iRoleManageService.queryMenuList("0", result,rolemap);
			result2.append("]");
			String str = result2.toString();
			str = str.replaceAll("\\[,\\{", "\\[\\{");
			/*sysrole.setRoleid(roleid);
			sysrole.setPermissions(rolepermission);
			sysrole.setPermissionTreeData(str);*/
			map.put("roleid", roleid);
			map.put("rolepermission", rolepermission);
		    map.put("permissionTreeData", str);
	      } catch (Exception e) {
	    	
	    	  e.printStackTrace();
	      }
	      ModelAndView role = new ModelAndView("/jsp/role/role_permission");
	      //将数据存入modelMap
	   //   rolemap.addObject("rolepermission", list.get(0));
	      role.addObject("role", map);
	      
	      return role;
	  }
	    
	@RequestMapping(value="/updateRolePermission.do")
	public void updateRolePermission(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		//{roleid=10, permissions=1,0}

		try {
			String roleid=paramsMap.get("roleid");
			String permissions=paramsMap.get("permissions");
			iRoleManageService.updateRolePermission(roleid,permissions);
			JsonUtil.returnnBaseJson(true, "保存成功！", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
		}
		
	}
	
	
	
	

}
