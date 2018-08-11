package com.github.xupei.simple.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;



public class ForceLogoutFilter extends AccessControlFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
		Session session = getSubject(request, response).getSession(false);
        if(session == null) {
            return true;
        }
        return session.getAttribute("session.force.logout") == null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		 try {
	            getSubject(request, response).logout();//寮哄埗閫�嚭
	        } catch (Exception e) {/*ignore exception*/}

	        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
	        WebUtils.issueRedirect(request, response, loginUrl);
	        return false;
	}

}
