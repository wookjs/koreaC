package com.korea.mall.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.korea.mall.dao.ProductDAO;
import com.korea.mall.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	@Autowired
	final ProductDAO product_dao;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping(value = {"/","main"})
	public String main(Model model) {
		List<ProductDTO> list = product_dao.selectList();

		model.addAttribute("list",list);
		return "main/main";
	}
	
//	// ī�װ����� ����
//	@RequestMapping("category")
//	public String view_list(Model model, int p_id) {
//		List<ProductDTO> list = product_dao.selectList(p_id);
//		
//		model.addAttribute("list", list);
//		
//		return "main/main";
//	}
	
	// ��ǰ ��� ������
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
			return "redirect:main/main";
		}
		return null;
	}
	
	
	// ��ǰ �󼼺���
	@RequestMapping("detail")
	public String detail(Model model, int p_num) {
		ProductDTO dto = product_dao.selectOne(p_num);
		model.addAttribute("dto", dto);
		
		return "main/p_detail";
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
				fileName = dto.getP_name() + '_' + (++picCount) + ".jpeg";
				
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
