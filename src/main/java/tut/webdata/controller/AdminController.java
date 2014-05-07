package tut.webdata.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
		
		//init!
		orderService.createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM4", "YM1"), "Mark Brown", "Greenwitch St. 402/C, London", "4802 35"));
		orderService.createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM2", "YM1", "YM3"), "Valter Longo", "Greenwitch St. 156/D, Paris", "569 21"));
		
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
			order.setStatus(orderStatus.getStatus());
			
			//get all menu items objects of order by menu item ids as keys of Map orderItems
			Map<String, Integer> orderItems = order.getOrderItems();
			List<MenuItem> menuItems = new ArrayList<>();
			for (String itemId : orderItems.keySet()) {
				menuItems.add(menuService.requestMenuItem(itemId));
			}
			//order.setMenuItems(menuItems);
			
			//get names of all menu items of order
			order.getAllMenuItemsNames(menuItems);
			
			//get cost of order
			order.getTotalCost(menuItems);
			
			ordersWithStatuses.add(order);
		}
		
		model.addAttribute("orders", ordersWithStatuses);
		return "admin/orders";
	}
	
	private static Map<String, Integer> createOrderItems(String... orderIds) {
		Map<String, Integer> orderItems = new HashMap<String, Integer>();
		for (String orderId : orderIds) {
			orderItems.put(orderId, 1);
		}
		return orderItems;
	}
	
	private static Order createOrder(String id, Date dateTimeOfSubmission, Map<String, Integer> orderItems, String name, String address, String postcode) {
		Order order = new Order();
		order.setId(id);
		order.setDateTimeOfSubmission(dateTimeOfSubmission);
		order.setOrderItems(orderItems);
		order.setName(name);
		order.setAddress1(address);
		order.setPostcode(postcode);
		return order;
	}
}
