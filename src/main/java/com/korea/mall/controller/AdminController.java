package com.korea.mall.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.mall.dao.ProductDAO;
import com.korea.mall.dto.ProductDTO;
import com.korea.mall.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	final ProductDAO product_dao;
	final ProductService productService;
	
	@Autowired
	HttpServletRequest request;
	
	// 상품 등록 이동
	@RequestMapping("p_add_form")
	public String p_add_form() {
		return "product/product_insert";
	}
	
	// 상품 등록
	@RequestMapping("p_add")
	public String p_add(ProductDTO dto) {
		productService.photo_upload(dto);
		int res = product_dao.p_add(dto);
		
		if(res > 0) {
			return "redirect:pList_form";
		}
		return null;
	}
	
	// 상품 리스트 페이지
	@RequestMapping("pList_form")
	public String pList_form(Model model) {
		List<ProductDTO> list = product_dao.selectList();

		model.addAttribute("list",list);
		return "admin/product_list";
	}
	
	// 상품 카테고리별
	@RequestMapping("product_category")
	public String view_list(Model model,@RequestParam(value = "p_id", required = true) int p_id) {
		List<ProductDTO> list = product_dao.category(p_id);
		model.addAttribute("list", list);
		
		return "admin/product_category_list";
	}
	
	// 상품 삭제
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
	
	// 상품 수정 페이지로 이동
	@RequestMapping("modify")
	public String modify(Model model, int p_num) {
		ProductDTO dto = product_dao.selectOne(p_num);
		model.addAttribute("dto", dto);
		return "product/product_modify";
	}
	
	// 상품 수정
	@RequestMapping("product_modify")
	public String product_modify(ProductDTO dto) {
		productService.photo_upload(dto);
		int res = product_dao.update(dto);
		
		if(res > 0) {
			return "redirect:pList_form";
		}
		return null;
	}
}
