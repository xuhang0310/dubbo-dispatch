package com.github.xupei.simple.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter{
	
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
      
        return super.onAccessDenied(request, response, mappedValue);
    }

}
