package com.korea.mall.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.mall.common.Common;
import com.korea.mall.dao.ProductDAO;
import com.korea.mall.dto.ProductDTO;
import com.korea.mall.service.ProductService;
import com.korea.mall.util.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	final ProductDAO product_dao;
	final ProductService productService;
	
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
		productService.photo_upload(dto);
		int res = product_dao.p_add(dto);
		
		if(res > 0) {
			return "redirect:pList_form";
		}
		return null;
	}
	
	// ��ǰ ����Ʈ ������
	@RequestMapping("pList_form")
	public String pList_form(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
		int start = (page - 1) * Common.pList.BLOCKLIST + 1;
		int end = start + Common.pList.BLOCKLIST - 1;
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		
		List<ProductDTO> list = product_dao.selectList(map);
		
		// ��ü ��ǰ �� ��ȸ
		int rowTotal = product_dao.getRowTotal();
		
		// ������ �޴� ����
		String pageMenu = Paging.getPaging("pList_form",
											page,
											rowTotal, 
											Common.Main.BLOCKLIST, 
											Common.Main.BLOCKPAGE);
		
		request.getSession().removeAttribute("show");
		
		model.addAttribute("list",list);
		model.addAttribute("pageMenu", pageMenu);
		
		return "admin/product_list.jsp?page="+page;
	}
	
	// ��ǰ ī�װ���
	@RequestMapping("product_category")
	public String view_list(Model model,@RequestParam(value = "p_id", required = true) int p_id) {
		List<ProductDTO> list = product_dao.category(p_id);
		model.addAttribute("list", list);
		
		return "admin/product_category_list";
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
	
	// ��ǰ ���� �������� �̵�
	@RequestMapping("modify")
	public String modify(Model model, int p_num) {
		ProductDTO dto = product_dao.selectOne(p_num);
		model.addAttribute("dto", dto);
		return "product/product_modify";
	}
	
	// ��ǰ ����
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
