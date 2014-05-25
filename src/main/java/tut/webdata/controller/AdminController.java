package tut.webdata.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import tut.webdata.repository.OrderStatusRepository;
//import tut.webdata.repository.OrdersRepository;
import tut.webdata.services.MenuService;
import tut.webdata.services.OrderService;
import tut.webdata.support.web.AjaxUtils;
import tut.webdata.support.web.MessageHelper;
import tut.webdata.domain.MenuItem;
import tut.webdata.domain.Order;
import tut.webdata.domain.MenuOrderItem;
import tut.webdata.domain.OrderStatus;
import tut.webdata.domain.WebOrder;
import tut.webdata.domain.WebOrderStatus;

@Controller
public class AdminController {
	
//	private OrdersRepository orderRepository;
	
//	private OrderStatusRepository orderStatusRepository;
	
//	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MenuService menuService;
	
//	@Autowired
//	public AdminController(OrdersRepository orderRepository, OrderStatusRepository orderStatusRepository) {
//		this.orderRepository = orderRepository;
//		this.orderStatusRepository = orderStatusRepository;
//		init();
//	}
	
	@Autowired
	public AdminController(OrderService orderService) {
		this.orderService = orderService;
		init();
	}
	
//	private void init() {
//		createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM4", "YM1"), "Mark Brown", "Greenwitch St. 402/C, London", "4802 35"));
//		createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM2", "YM1", "YM3"), "Valter Longo", "Le Marais St. 156/D, Paris", "569 21"));
//	}
	
	private void init() {
		orderService.createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM4", "YM1"), "Mark Brown", "Greenwitch St. 402/C, London", "4802 35"));
		orderService.createOrder(createOrder(UUID.randomUUID().toString(), new Date(), createOrderItems("YM2", "YM1", "YM3"), "Valter Longo", "Le Marais St. 156/D, Paris", "569 21"));
	}

	@RequestMapping(value = "orders", method = RequestMethod.GET)
	public String getOrders(Model model) {		
		List<Order> orders = new ArrayList<>();
		orders = orderService.requestAllOrders();
		
		List<WebOrder> webOrders = new ArrayList<>();
//		Iterator<Order> it1 = orders.iterator();
//		Iterator<WebOrder> it2 = webOrders.iterator();
//		while (it1.hasNext()) {
//			webOrders.add(new WebOrder());
//			it2.next();
//			BeanUtils.copyProperties(it1, it2);
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
	
	private Order createOrder(String id, Date dateTimeOfSubmission, Map<String, Integer> orderItems, String name, String address, String postcode) {
		Order order = new Order();
		order.setId(id);
		order.setDateTimeOfSubmission(dateTimeOfSubmission);
		order.setOrderItems(orderItems);
		order.setName(name);
		order.setAddress1(address);
		order.setPostcode(postcode);
		return order;
	}
	
//	private void createOrder(Order order) {
//		orderRepository.save(order);
//		orderStatusRepository.save(new OrderStatus(order.getId(), (UUID.randomUUID()).toString(), new Date(), "Order Received"));
//	}
	
//																@ModelAttribute("updateOrderForm") Order order
	@RequestMapping(value = "orders/{orderId}/edit", method = RequestMethod.GET)
	public String initUpdateOrderForm(@PathVariable("orderId") String orderId, Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		if (AjaxUtils.isAjaxRequest(requestedWith)) {
            return "admin/updateOrder".concat(" :: updateOrderForm");
        }
		Order order = orderService.requestOrder(UUID.fromString(orderId));
		
		WebOrder webOrder = new WebOrder();
		BeanUtils.copyProperties(order, webOrder);
		
//		get all menu items, put it into orderItms and check every item whether is present in order according to orderItems
		List<MenuOrderItem> menuAndOrderItems = new ArrayList<>();
		
		List<MenuItem> menuItems = menuService.requestAllMenuItems();
		
		Map<String, Integer> orderOrderItems = webOrder.getOrderItems();
		Set<String> orderOrderItemsKeys = orderOrderItems.keySet();
		
		for (int i = 0; i < menuItems.size(); i++) {
			MenuItem menuItem = menuItems.get(i);
			
			menuAndOrderItems.add(new MenuOrderItem());
			MenuOrderItem menuOrderItem = menuAndOrderItems.get(i);
			
			menuOrderItem.setId(menuItem.getId());
			menuOrderItem.setName(menuItem.getName());
			menuOrderItem.setCost(menuItem.getCost());
			menuOrderItem.setMinutesToPrepare(menuItem.getMinutesToPrepare());
			menuOrderItem.setQuantity(0);
			menuOrderItem.setChosen(false);
			
			//id aktualneho menuItem v iteracii je porovnane s id vsetkych poloziek nasej objednavky - orderOrderItems,
			//ak najdene, nastavi na menuOrderItem, ze chosen je true a nastavi pocet kusov
			for (String id : orderOrderItemsKeys) {
				if (menuItem.getId().equals(id)) {
					menuOrderItem.setChosen(true);
					menuOrderItem.setQuantity(orderOrderItems.get(menuItem.getId())); //mozno konvertovat na int
					break;
				}
			}
		}
		
		webOrder.setMenuAndOrderItems(menuAndOrderItems);
		
		//get current status and other statuses
		WebOrderStatus currentStatus = new WebOrderStatus(orderService.requestOrderStatusByOrderId(order.getId()).getStatus(),true);
		//BeanUtils.copyProperties(orderService.requestOrderStatusByOrderId(order.getId()), currentStatus);
		//currentStatus.setSelected(true);
		List<WebOrderStatus> statuses = getAllStatuses();
		List<WebOrderStatus> orderStatuses = new ArrayList<>();
		orderStatuses.add(currentStatus);
		for (WebOrderStatus status : statuses) {
			if (!status.getStatus().equals(currentStatus.getStatus())) {
				orderStatuses.add(status);
			}
		}
		
		webOrder.setOrderStatuses(orderStatuses);
		
		model.addAttribute("updateOrderForm", webOrder);
        return "admin/updateOrder";
	}
	
	private List<WebOrderStatus> getAllStatuses() {
		List<WebOrderStatus> statuses = new ArrayList<>();
		statuses.add(new WebOrderStatus("Order Received", false));
		statuses.add(new WebOrderStatus("Order Processing", false));
		statuses.add(new WebOrderStatus("Order Processed", false));
		statuses.add(new WebOrderStatus("Order Completed", false));
		statuses.add(new WebOrderStatus("Order Cancelled", false));
		return statuses;
	}
	
	@RequestMapping(value = "orders/{orderId}/update", method = RequestMethod.POST)
	public String processUpdateOrderForm(@PathVariable("orderId") String orderId, @Valid @ModelAttribute("updateOrderForm") WebOrder webOrder, Errors errors, RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			return "admin/updateOrder";
		}
		//needed property for date/time of order update - updated details except status
		
		webOrder.updateOrderItems(webOrder.getMenuAndOrderItems());
		
		Order order = orderService.requestOrder(UUID.fromString(orderId));
		order.setOrderItems(webOrder.getOrderItems());
		order.setName(webOrder.getName());
		order.setAddress1(webOrder.getAddress1());
		order.setPostcode(webOrder.getPostcode());
		
		orderService.setOrder(order);
		
		//if order status has been modified, update order status
		OrderStatus status = orderService.requestOrderStatusByOrderId(orderId);
		if (!status.getStatus().equals(webOrder.getStatus())) {
			status.setStatusDate(new Date());
			status.setStatus(webOrder.getStatus());
			orderService.setOrderStatus(status);
		}
		
		return "redirect:/orders";
	}
}
