package com.github.xupei.simple.sys.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import com.github.xupei.dubbo.api.IRoleService;
import com.github.xupei.simple.dao.BaseDao;

@Service
public class RoleServiceImpl implements IRoleService {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	@Override
	public Map<String, Set<String>> selectRoleResoureByUserId(String userid) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
        List roleIdList = this.getRoleByUserId(userid);
        Set<String> urlSet = new HashSet<String>();
        Set<String> roles = new HashSet<String>();
        HashMap map=(HashMap)roleIdList.get(0);
       String permissions= map.get("PERMISSIONS")+"";
       List<Map<String, String>> resourceList = this.getRoleResourceByPermissions(permissions);
       roles.add(map.get("ROLENAME")+"");
       if (resourceList != null && !resourceList.isEmpty()) {
           for (Map<String, String> mapResource : resourceList) {
               if (mapResource != null && null!=(mapResource.get("MENUURL"))) {
                   urlSet.add(mapResource.get("MENUURL"));
               }
           }
       }
       
       
        resourceMap.put("urls", urlSet);
        resourceMap.put("roles", roles);
        return resourceMap;
	}
	
	public List getRoleByUserId(String userid) throws Exception{
		
		String sql="select sr.roleid,sr.rolename,sr.permissions from sys_user su,sys_role sr where su.userid='"+userid+"' ";
		
		return baseDao.findAll(sql);
		
	}
	
    public List getRoleResourceByPermissions(String permissions) throws Exception{
		
		String sql="select * from sys_menu t where del=1 and menuid in ("+permissions+") ";
		
		return baseDao.findAll(sql);
		
	}

}
