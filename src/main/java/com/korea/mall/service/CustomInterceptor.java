package com.korea.mall.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.korea.mall.dto.UserDTO;

@Component
public class CustomInterceptor implements HandlerInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
			String mainurl = "";

			HttpSession session = request.getSession();
			UserDTO u_id = (UserDTO)session.getAttribute("u_id");
			//System.out.println(u_id);
		
			//mall���� ���
			String url= "http://localhost:9090/mall/";
			
			//webapp���������� ���
			String resourceSrc = request.getServletContext().getRealPath("");
			
			//pageURL��� �߰�
			resourceSrc += "resources/pageURL/pageURL";
			
			//System.out.println(resourceSrc);
			
			//System.out.println(request.getParameter("type"));
		
			
	        // ���Ͽ��� URL�� �о�� ó��
	        try (BufferedReader br = new BufferedReader(new FileReader(resourceSrc))) {
	        	
	            String line;
	            while ((line = br.readLine()) != null) {
	            	
	                //�⺻ URL���� ���Ͽ� �ִ� �ּҰ� ���ؼ� ���� 
	                if (request.getRequestURL().toString().equals(url+line)) {
	                	if(u_id == null) {
	                		if(request.getParameter("type")!=null) {
	                			session.setAttribute("line", line+"?type="+request.getParameter("type"));
	                			 response.sendRedirect(request.getContextPath() + "/login_form");
	 		                    return false;
	                		}
		                	session.setAttribute("line",line);
		                    response.sendRedirect(request.getContextPath() + "/login_form");
		                    return false;
	                	}
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
