package com.github.xupei.simple.user;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*import com.github.xupei.dubbo.api.IFeedService;*/
import com.github.xupei.dubbo.api.IUserService;
import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.json.JsonUtil;
import com.github.xupei.simple.shiro.ShiroUser;
import com.github.xupei.simple.shiro.realm.UserRealm;
import com.github.xupei.simple.util.echart.EchartUtil;



@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	@Autowired
	 private UserRealm shiroDbRealm;
	
	@Autowired
	IUserService userService;
	
/*	@Autowired
	private IFeedService feedService;*/
	
	@RequestMapping("/getUserList.do")
	public void getUserList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=userService.getUserList(paramsMap);
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	@RequestMapping(value = "/login")
	public String showLoginForm(HttpServletRequest req,Model model) {
		
		
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error = null;
        
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名不存在！";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "密码错误！";
        } else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
        	error = "登录失败次数过多，请稍后再试！";
        } else if("jCaptcha.error".equals(exceptionClassName)) {
        	error = "验证码错误！";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
       
        model.addAttribute("msg", error);
        System.out.println("error信息是"+error);
        if(req.getParameter("kickout") != null){
        	model.addAttribute("msg", "您的帐号在另一个地点登录，您已被踢出！");
        }
        if(req.getParameter("forceLogout") != null) {
        	model.addAttribute("msg", "您已经被管理员强制退出，请重新登录！");
        }
      
       
        return "/jsp/login"; 
    }
	@RequestMapping("/loginSucess.do")
	public ModelAndView loginSucess(){
		ModelAndView model=new ModelAndView();
				model.setViewName("/jsp/menu");
	    ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
	    try {
		 String templateSkin=userService.getUserSkinTemplate(user.getId());
		 model.addObject("skin", templateSkin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
		
	}
	
	
	@RequestMapping("/loginNoShiro.do")
	@ResponseBody
	public Map<String, String> login(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, String> paramsMap){
		Map<String, String> messageMap = new HashMap();
		Map<String,Object> sessionMap = new HashMap<String,Object>();
		try {
			List list=userService.checkUser(paramsMap);
			if(list.size()==0){
				messageMap.put("oper", "f");
				messageMap.put("msg", "没有该用户");
				return messageMap;
			}else{
				Map map=(Map) list.get(0);
				String pwd=map.get("PASSWORD").toString();
				if(pwd.equals(paramsMap.get("password").toString())){
					messageMap.put("oper", "s");
					HttpSession session = request.getSession();
					session.setAttribute("userId", map.get("USERID").toString());
					session.setAttribute("displayName", map.get("DISPLAYNAME").toString());
					session.setAttribute("orgId", map.get("ORGID").toString());
					return messageMap;
				}else{
					messageMap.put("oper", "f");
					messageMap.put("msg", "密码错误");
					return messageMap;
				}
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.error("-------------------");
		}
		
		return null;
		
	}
	
	@RequestMapping("/changeUserSkin.do")
	public void changeUserSkin(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, String> paramsMap){
		 ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		 Session currentSession = SecurityUtils.getSubject().getSession();
		 String skin=paramsMap.get("skin");
		 if(skin.equals("#222A2D")){
			 skin="";
		 }else{
			 skin=".white";
		 }
		 try {
			userService.changeUserSkinTemplate(skin, user.getId());
			
		    JsonUtil.returnnBaseJson(true, "切换成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 JsonUtil.returnnBaseJson(false, "切换失败", response);
		}
	}
	
	/**
	 * 密码变更
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping("/clickUserPW.do")
	public void clickUserPW(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, String> paramsMap){
		 ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		 Session currentSession = SecurityUtils.getSubject().getSession();
		 
		 try {
			userService.clickUserPW(user.getId());
			
		    JsonUtil.returnnBaseJson(true, "变更成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 JsonUtil.returnnBaseJson(false, "变更失败", response);
		}
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		 ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		Subject subject=SecurityUtils.getSubject();
		shiroDbRealm.removeUserCache(user);
		if(subject.isAuthenticated()){
			subject.logout();
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/loginHome.do",method=RequestMethod.POST)
	public void getLoginHome(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		 ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		
		try {
			List list=userService.getUserRightList(user.getId());
			JsonUtil.returnListJson(list,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/addUser.do")
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		
		List orgList = null;
		List roleList = null;
		try {
			orgList=userService.getOrgList();
			roleList=userService.getRoleList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setViewName("/jsp/user/eidtUser");
		
		model.addObject("orgList",orgList );
		model.addObject("roleList",roleList );
		
		return model;
	}
	
	/**
	 * 修改跳转方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="/updUser.do")
	public ModelAndView updUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		String userid = paramsMap.get("userid");
		
		List userList = null;
		List orgList = null;
		List roleList = null;
		try {
			userList=userService.getUserList(userid);
			orgList=userService.getOrgList();
			roleList=userService.getRoleList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setViewName("/jsp/user/eidtUser");
		
		model.addObject("users",userList.get(0) );
		model.addObject("orgList",orgList );
		model.addObject("roleList",roleList );
		
		return model;
	}
	
	/**
	 * 新增方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/saveUser.do",method=RequestMethod.POST)
	public void saveUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			userService.saveUser(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "保存成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "保存失败", response);
			e.getLocalizedMessage();
		}
		
	}
	
	/**
	 * 修改方法
	 * @param request
	 * @param response
	 * @param paramsMap
	 */
	@RequestMapping(value="/updateUserById.do",method=RequestMethod.POST)
	public void updateUserById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			userService.updateUserById(paramsMap);
			
			JsonUtil.returnnBaseJson(true, "修改成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "修改失败", response);
			e.getLocalizedMessage();
		}
		
	}
	
	
	@RequestMapping(value="/userListLayer.do")
	public ModelAndView userListLayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/user/userList");
		
		model.addObject("jsonTableGrid",super.getTableGrid(paramsMap) );
		
		
		return model;
	}
	
	@RequestMapping(value="/deleteUser.do")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
		try {
			String userid=paramsMap.get("userid");
			userService.deleteUser(userid);
			JsonUtil.returnnBaseJson(true, "删除成功", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.returnnBaseJson(false, "删除失败", response);
			e.getLocalizedMessage();
		}
		
	}
	
	/**
     * 验证用户是否已存在
     */
	@RequestMapping(value="/doValidUsername.do")
    public void doValidUsername(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		
			String uname=paramsMap.get("uname");
			paramsMap.put("uname", uname);
			
			int count=0;
			try {
				count = userService.doValidUsername(paramsMap);
				if (count>0) {
					
					JsonUtil.returnnBaseJson(true, "用户名已存在!请重新输入...", response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JsonUtil.returnnBaseJson(false, e.getLocalizedMessage(), response);
				e.getLocalizedMessage();
			}
			
    	
    }
	@RequestMapping(value="/toMap.do")
	public ModelAndView toMap(){
		
		/*ModelAndView model=new ModelAndView();
		model.setViewName("/jsp/map");
		//加载热源列表
		try {
			model.addObject("feedList",feedService.getFeedAllList(null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return null;
	}
	

}
