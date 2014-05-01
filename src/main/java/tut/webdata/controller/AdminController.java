package tut.webdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tut.webdata.services.OrderService;

@Controller
public class AdminController {
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "orders")
	public String getOrders(Model model) {
		model.addAttribute("orders", orderService.requestAllOrders());
		return "admin/orders";
	}
}
