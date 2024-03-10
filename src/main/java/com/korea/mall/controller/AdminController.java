package com.korea.mall.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.korea.mall.dao.ProductDAO;
import com.korea.mall.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	final ProductDAO product_dao;
	
	@Autowired
	HttpServletRequest request;
	
	// ��ǰ ��� �̵�
	@RequestMapping("p_add_form")
	public String p_add_form() {
		return "product/product_insert";
	}
	
	// ��ǰ ���
	@RequestMapping("p_add")
	public String p_add(ProductDTO dto) {
		photo_upload(dto);
		int res = product_dao.p_add(dto);
		
		if(res > 0) {
			return "redirect:pList_form";
		}
		return null;
	}
	
	// ��ǰ ����Ʈ ������
	@RequestMapping("pList_form")
	public String pList_form(Model model) {
		List<ProductDTO> list = product_dao.selectList();

		model.addAttribute("list",list);
		return "admin/product_list";
	}
	
	// ��ǰ ī�װ���
	@RequestMapping("product_category")
	public String view_list(Model model,@RequestParam(value = "p_id", required = true) int p_id) {
		List<ProductDTO> list = product_dao.category(p_id);
		model.addAttribute("list", list);
		
		return "admin/product_category_list";
	}
	
	
	// ��ǰ �������� ���
	@RequestMapping("upload")
	@ResponseBody
	public String upload(int p_num) {
		ProductDTO dto = product_dao.selectOne(p_num);
		
		if(dto == null) {
			return "[{'upload':'no'}]";
		}
		return "[{'upload':'yes'}]";
	}
	
	// ��ǰ ����
	@RequestMapping("delete")
	@ResponseBody
	public String delete(int p_num) {
		
		int res = product_dao.delete(p_num);
		
		String result = "no";
		
		if(res == 1) {
			result = "yes";
		}
		
		String finRes = String.format("[{'del':'%s'}]", result);
		
		return finRes;
		
	}
	
	// ��ǰ ����
	@RequestMapping("modify")
	public String modify(Model model, int p_num) {
		ProductDTO dto = product_dao.selectOne(p_num);
		model.addAttribute("dto", dto);
		return "product/product_modify";
	}
	
	// ��ǰ ���� �� ��Ͻ� ���� ���ε� 
	public ProductDTO photo_upload(ProductDTO dto) {
		String webPath = "/resources/product_img/";
		webPath = String.format("%s/%s/", webPath, dto.getP_name());
		String savePath = request.getServletContext().getRealPath(webPath);
		
		System.out.println(savePath);
		
		int picCount = dto.getPicture_count();
		
		for(int i = 0; i < dto.getPicture().length; i++) {
			MultipartFile photo = dto.getPicture()[i];
			String fileName = "no_file";
			
			if(!photo.isEmpty()) {
				fileName = dto.getP_name() + '_' + (++picCount) + ".jpeg"; // �����̸�_1.jpeg, �����̸�_2.jpeg ...
				
				File saveFile = new File(savePath, fileName);
				
				if(!saveFile.exists()) {
					saveFile.mkdirs();
				} else {
					saveFile.delete();
					saveFile = new File(savePath, fileName);
				}
				
				try {
					photo.transferTo(saveFile);
				} catch (Exception e) {
					// TODO: handle exception
				}
				dto.setP_picture(fileName);
				dto.setPicture_count(picCount);
			}
		}
		return dto;
	}
}
