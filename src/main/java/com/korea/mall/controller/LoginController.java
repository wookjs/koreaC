	package com.korea.mall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.mall.dao.UserDAO;
import com.korea.mall.dto.UserDTO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class LoginController {

	
	final UserDAO user_dao;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	
	@RequestMapping("login")
	@ResponseBody
	public String login(String u_id, String u_pwd) {
		//id�� �ش��ϴ� ������ 1�� ��ȸ
		UserDTO dto = user_dao.selectOne(u_id);
		
		//dto�� null�� ��� id�� DB�� �������� ����
		if(dto ==null) {
			return "[{'param':'no_u_id'}]";
		}
		
		//�츮�� �Է¹��� pwd�� DB�� ����� ��й�ȣ�� ���ϱ�
		if(u_pwd.equals(dto.getU_pwd())){
			return "[{'param':'no_u_pwd'}]";
		}
		
		//������� �������� ���̵�� ��й�ȣ�� ������ ���ٴ� ��
		//���ǿ� ���ε��� �Ѵ�.
		//������ ������ �޸𸮸� ����ϱ� ������ ������ ���� ����Ҽ���
		//�������� �������� ������ �� �ʿ��� �������� ������ ����
		
		session.setAttribute("u_id",dto);
		
		//�α��ο� ������ ���
		return "[{'param':'clear'}]";
		
		
	}
	
	@RequestMapping(value={"","login_form"})
	public String login_form() {
		return "login/login_form";
	}
	
	@RequestMapping("logout")
	public String logout() {
		session.removeAttribute("u_id");
		return "redirect:user_list";
	}
	
	@RequestMapping("user_insert_form")
	public String user_insert_form() {
		return "/login/user_insert_form";
	}
	
	@RequestMapping("check_id")
	@ResponseBody
	public String check_id(String u_id) {
		UserDTO dto = user_dao.selectOne(u_id);
		
		//null�̸� �ߺ��� �����ϱ� ���� ����.
		if(dto ==null) {
			return "[{'res':'yes'}]";
		}
		return "[{'res':'no'}]";
	}
	
	@RequestMapping("user_insert")
	public String user_insert(UserDTO dto) {
		int res = user_dao.insert(dto);
		
		if(res>0) {
			return "redirect:user_list";
		}
		return null;
	}
	
	
	
	
}








