package tut.webdata.controller;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tut.webdata.services.MenuService;
import tut.webdata.services.OrderService;
import tut.webdata.domain.MenuItem;
import tut.webdata.domain.Order;
import tut.webdata.domain.OrderStatus;
import tut.webdata.domain.WebOrder;

@Controller
public class AdminController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "orders")
	public String getOrders(Model model) {
		
		List<Order> orders = new ArrayList<>();
		orders = orderService.requestAllOrders();
		
		List<WebOrder> webOrders = new ArrayList<>();
//		Iterator<Order> it1 = orders.iterator();
//		Iterator<WebOrder> it2 = webOrders.iterator();
//		while (it1.hasNext()) {
//			BeanUtils.copyProperties(it1, it2);
//			webOrders.add(new WebOrder());
//			it2.next();
//		}
		for (int i = 0; i < orders.size(); i++) {
			webOrders.add(new WebOrder());
			BeanUtils.copyProperties(orders.get(i), webOrders.get(i));
		}
		
		List<WebOrder> ordersWithStatuses = new ArrayList<>();
		for (WebOrder order : webOrders) {
			// get current status of order
			OrderStatus orderStatus = orderService.requestOrderStatusByOrderId(order.getId());
			order.setStat(orderStatus.getStatus());
			
			//get all menu items objects of order by menu item ids as keys of Map orderItems
			Map<String, Integer> orderItems = order.getOrderItems();
			List<MenuItem> menuItems = new ArrayList<>();
			for (String itemId : orderItems.keySet()) {
				menuItems.add(menuService.requestMenuItem(itemId));
			}
			order.setMenuItems(menuItems);
			
			//get names of all menu items of order
			order.getAllMenuItemsNames(menuItems);
			
			//get cost of order
			
			ordersWithStatuses.add(order);
		}
		
		model.addAttribute("orders", ordersWithStatuses);
		return "admin/orders";
	}
}
