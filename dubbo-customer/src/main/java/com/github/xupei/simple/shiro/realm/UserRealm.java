package com.github.xupei.simple.shiro.realm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


import com.github.xupei.dubbo.api.IRoleService;
import com.github.xupei.dubbo.api.IUserService;
import com.github.xupei.dubbo.bean.SysUser;
import com.github.xupei.simple.shiro.ShiroUser;



public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/* String username = (String)principals.getPrimaryPrincipal();
		// TODO Auto-generated method stub
		 SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		 HashMap<String,String> paramsMap=new HashMap<String,String>();
		 paramsMap.put("userName", username);
		 try {
		        SysUser user=userService.checkUserBean(paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(shiroUser.getRoles());
        info.addStringPermissions(shiroUser.getUrlSet());
        
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("UserRealm-----------"+new Date());
		 String username = (String)token.getPrincipal();
		 SysUser user = null;
	      
		try {
			 HashMap<String,String> paramsMap=new HashMap<String,String>();
			 paramsMap.put("userName", username);
			
			// System.out.println("userService:"+userService+"___________________________________________");
			user = this.userService.checkUserBean(paramsMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(user == null) {
            throw new UnknownAccountException();//娌℃壘鍒板笎鍙�
        }


        
        Map<String, Set<String>>  resourceMap=new HashMap();
        ShiroUser shiroUser=null ;
		try {
			resourceMap = roleService.selectRoleResoureByUserId(user.getUserid());
			Set<String> urls = resourceMap.get("urls");
	        Set<String> roles = resourceMap.get("roles");
	        shiroUser = new ShiroUser(user.getUserid(), user.getUsername(), user.getDisplayname(),user.getTemplatestyle()==null?"":user.getTemplatestyle(), urls);
	        shiroUser.setRoles(roles);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        Session currentSession = SecurityUtils.getSubject().getSession();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo( shiroUser ,
        		user.getPassword(),
                getName()  //realm name
        );
        currentSession.setAttribute("user", shiroUser);
	        return authenticationInfo;
		
	}
	
	//系统登出后 会自动清理授权和认证缓存
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
    
    /**
     * 清除用户缓存
     * @param shiroUser
     */
    public void removeUserCache(ShiroUser shiroUser){
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(shiroUser.getLoginName(), super.getName());
        this.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户缓存
     * @param loginName
     */
    public void removeUserCache(String loginName){
        removeUserCache(new ShiroUser(loginName));
    }
    
   

    

}
