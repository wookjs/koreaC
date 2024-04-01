package com.korea.mall.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.korea.mall.dao.OrderDAO;
import com.korea.mall.dto.BasketDTO;
import com.korea.mall.dto.OrderDTO;
import com.korea.mall.dto.SearchreDTO;
import com.korea.mall.dto.UserDTO;
import com.korea.mall.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final  OrderService orderService;
	
	private final OrderDAO orderDAO;
	
	
	@Autowired
	HttpSession session;
	
	@RequestMapping("/mypage")
	public String mypage(Model model) {
		UserDTO dto = (UserDTO) session.getAttribute("u_id");
		model.addAttribute("user", dto);		
		return "mypage";
	}
	
	@RequestMapping("/user_update")
	public String user_update(HttpServletRequest req) {
		
		UserDTO dto = (UserDTO) session.getAttribute("u_id");
		req.setAttribute("dto", dto);
	
		return "user_update";
	}
	
	@RequestMapping("basket_page")
	public String basket_page(Model model) {
		
		List<BasketDTO> list = orderService.selectList();
		UserDTO dto = (UserDTO) session.getAttribute("u_id");
		
		model.addAttribute("basket", list);
		model.addAttribute("user", dto);
		
		
		
		return "order/basket_page";
	}
	
	@RequestMapping("orderhistory_page")
	public String orderhistory_page(Model model) {
			UserDTO userdto = (UserDTO)session.getAttribute("u_id");
			int count = orderDAO.selectCount(userdto.getU_id());
			List<OrderDTO> order = orderService.selectOrderList(count, userdto.getU_id());
			
			model.addAttribute("order", order);
			model.addAttribute("user", userdto);
			model.addAttribute("count", count - 4);
		return "order/orderhistory_page";
	}
	

	
//	@RequestMapping("histroy_search")
//	@ResponseBody
//	public String history_search(@RequestBody HashMap<String, Object> jsonString, Model model) {
//		
//		List<OrderDTO> dto = orderService.selectSearch(jsonString);
//		model.addAttribute("order", dto);
//		int res = dto.size();
//		if(res >= 1) {
//			return "[{'param' : 'yes'}]";
//		}
//		
//		return "[{'param' : 'no'}]";
//	}

	
	@RequestMapping("history_search")
	public String history_search(String orderstatus, String startdate, String enddate, String userid, Model model) {
		int count = orderService.selectSearchCount(orderstatus, startdate, enddate, userid);
		List<OrderDTO> dto = orderService.selectSearch(orderstatus, startdate, enddate, userid, count);
		UserDTO uesrdto = (UserDTO)session.getAttribute("u_id");
		SearchreDTO serdto = new SearchreDTO();
		serdto.setEnddate(enddate);
		serdto.setOrderstatus(orderstatus);
		serdto.setStartdate(startdate);
		
		model.addAttribute("order", dto);
		model.addAttribute("user", uesrdto);
		model.addAttribute("count", count);
		model.addAttribute("serdto", serdto);
		return  "order/orderhistory_page";
	}
	

	@RequestMapping("moreClick")
	@ResponseBody
	public ModelAndView moreClick(@RequestBody HashMap<String, Object> jsonString, ModelAndView mav){ 
		Object countjs = jsonString.get("countjs");
		Object orderstatusjs = jsonString.get("orderstatusjs");
		Object startdatejs = jsonString.get("startdatejs");
		Object enddatejs = jsonString.get("enddatejs");
		
		String countString = countjs.toString();
		
		int count = Integer.parseInt(countString);
		String orderstatus = orderstatusjs.toString();
		String startdate = startdatejs.toString();
		String enddate = enddatejs.toString();
		
		UserDTO userdto = (UserDTO)session.getAttribute("u_id");
	if(orderstatus == "--전체--" && startdate == "" && enddate == "") {
			int countch = count -4;
		List<OrderDTO> order = orderService.selectOrderList(countch, userdto.getU_id());
		
		 mav.addObject("ordermore", order);
		 mav.addObject("user", userdto);
		 mav.addObject("count", countch);
		 mav.setViewName("jsonView");
		
//		model.addAttribute("order", order);
//		model.addAttribute("user", userdto);
//		model.addAttribute("count", countch);
		}
		else {
			
			int countch = count -4 ;
			UserDTO user = (UserDTO)session.getAttribute("u_id");
			String userid = user.getU_id();
			List<OrderDTO> dto = orderService.selectSearch(orderstatus, startdate, enddate, userid, countch);
			
			SearchreDTO serdto = new SearchreDTO();
			serdto.setEnddate(enddate);
			serdto.setOrderstatus(orderstatus);
			serdto.setStartdate(startdate);
			
			mav.addObject("ordermore", dto);
			 mav.addObject("user", userdto);
			 mav.addObject("count", countch);
			 mav.addObject("serdto",serdto);
			 mav.setViewName("jsonView");
			
//			model.addAttribute("order", dto);
//			model.addAttribute("user", user);
//			model.addAttribute("count", countch);
//			model.addAttribute("serdto", serdto);
		}
		return mav;
	}
	
	
	
	
	
	@RequestMapping("user_update_login")
	public String mypage_login() {
		
	return "user_update_login";
	}
	
	@RequestMapping("user_db_update")
	@ResponseBody
	public String user_db_update(UserDTO dto) {
		int res = orderService.update(dto);
		if(res >= 1) {
			return "[{'param' : 'in'}]";
		}
			return "[{'param' : 'notin'}]";
	}
	
	@RequestMapping("basket_del")
	@ResponseBody
	public String basket_del(int idx) {
		
		
		int res = orderService.delete(idx);
		if(res >= 1) {
			
			return "[{'param' : 'yes'}]";
		}
		return "[{'param' : 'no'}]";
		
	}
	
	@RequestMapping("basket_insert")
	public String basket_insert(String pronum) {
		
		orderService.basketInsert(pronum);
		
		return "redirect:basket_page";
	}
	
	
	@RequestMapping("order_page")
	public String orderpage(String type, Model model, String pronum, Integer num) {
		UserDTO dto = (UserDTO) session.getAttribute("u_id");
		model.addAttribute("user", dto);
		
		if(pronum == null) {
		List<BasketDTO> order = orderService.selectList(type);
		model.addAttribute("order", order);
		}else if(pronum != null){
			List<BasketDTO> order = orderService.selectProduct(pronum, num);
			model.addAttribute("order", order);
			
		}
		
		
		
		return "order/orderpage";
	}
	
	@RequestMapping("order_pay")
	@ResponseBody
	public String order_pay(@RequestBody HashMap<String, Object> jsonString) {
		
		
		int res = orderService.insertorder(jsonString);
		
		if (res >= 1) {
			return "[{'param' : 'yes'}]";
		}
		return "[{'param' : 'no'}]";



	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
