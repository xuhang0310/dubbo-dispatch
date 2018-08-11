package com.github.xupei.simple.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.xupei.dubbo.api.ISysOrgService;
import com.github.xupei.simple.json.JsonUtil;

@Controller
@RequestMapping(value="/org")
public class OrgController {
	
	
	@Autowired
	ISysOrgService sysOrgService;
	
	@RequestMapping("/getOrgList.do")
	public void getUserList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap
			) throws Exception{ 
		Map map=sysOrgService.getOrgList(paramsMap);
		paramsMap=new HashMap<String, String>();
		paramsMap.put("page","1");
		paramsMap.put("rows","20");
		JsonUtil.returnObjectJson(map, response);
		
	}
	
	@RequestMapping("/getSysOrgTreeData.do")
	public void getSysOrgTreeData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		try {
			List list=sysOrgService.getOrgListForTree(paramsMap);
			JsonUtil.returnListJson(list, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
