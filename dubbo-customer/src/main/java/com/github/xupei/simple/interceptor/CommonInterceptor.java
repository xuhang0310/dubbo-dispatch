package com.github.xupei.simple.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CommonInterceptor extends HandlerInterceptorAdapter {
	
	 private final Logger log = Logger.getLogger(this.getClass());
	 public static final String LAST_PAGE = "com.alibaba.lastPage";  
	   
	    /**  
	     * ��ҵ��������������֮ǰ������  
	     * �������false  
	     *     �ӵ�ǰ������������ִ��������������afterCompletion(),���˳��������� 
	     * �������true  
	     *    ִ����һ��������,ֱ�����е���������ִ�����  
	     *    ��ִ�б����ص�Controller  
	     *    Ȼ�������������,  
	     *    �����һ������������ִ�����е�postHandle()  
	     *    �����ٴ����һ������������ִ�����е�afterCompletion()  
	     */    
	    @Override    
	    public boolean preHandle(HttpServletRequest request,    
	            HttpServletResponse response, Object handler) throws Exception {    
	        if ("GET".equalsIgnoreCase(request.getMethod())) {  
	           // RequestUtil.saveRequest();  
	        }  
	        //log.info("==============ִ��˳��: 1��preHandle================"+request.getServletPath());    
	        String requestUri = request.getRequestURI();  
	        String contextPath = request.getContextPath();  
	        String url = requestUri.substring(contextPath.length());  
	        
	       /* log.info("requestUri:"+requestUri);    
	        log.info("contextPath:"+contextPath);    
	        log.info("url:"+url);    */
	          
	        String userid =  (String)request.getSession().getAttribute("userId");   
	        if(userid == null){  
	            log.info("Interceptor����ת��loginҳ�棡");  
	            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);  
	            return false;  
	        }else  
	            return true;     
	    }    
	    
	    /** 
	     * ��ҵ��������������ִ����ɺ�,������ͼ֮ǰִ�еĶ���    
	     * ����modelAndView�м������ݣ����統ǰʱ�� 
	     */  
	    @Override    
	    public void postHandle(HttpServletRequest request,    
	            HttpServletResponse response, Object handler,    
	            ModelAndView modelAndView) throws Exception {     
	       // log.info("==============ִ��˳��: 2��postHandle================");    
	        if(modelAndView != null){  //���뵱ǰʱ��    
	            modelAndView.addObject("var", "����postHandle");    
	        }    
	    }    
	    
	    /**  
	     * ��DispatcherServlet��ȫ����������󱻵���,������������Դ��   
	     *   
	     * �����������׳��쳣ʱ,��ӵ�ǰ����������ִ�����е���������afterCompletion()  
	     */    
	    @Override    
	    public void afterCompletion(HttpServletRequest request,    
	            HttpServletResponse response, Object handler, Exception ex)    
	            throws Exception {    
	      //  log.info("==============ִ��˳��: 3��afterCompletion================");    
	    }    

}
