package tut.webdata.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.UUID;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class WebOrder {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	private String id;	

	private Date dateTimeOfSubmission;

	private Map<String, Integer> orderItems;
	
  	//put items ids and quantity from update form to orderItems
  	public void updateOrderItems(List<MenuOrderItem> menuAndOrderItems) {
  		Map<String, Integer> orderItems = new HashMap<String, Integer>();
  		for (MenuOrderItem item : menuAndOrderItems) {
  			if (item.isChosen()) {
  				orderItems.put(item.getId(), 1);
  			}
  		}
  		this.orderItems = orderItems;
  	}
  
  	private List<MenuOrderItem> menuAndOrderItems;
  	public List<MenuOrderItem> getMenuAndOrderItems() {
		return menuAndOrderItems;
	}
	public void setMenuAndOrderItems(List<MenuOrderItem> items) {
		this.menuAndOrderItems = items;
	}

	private List<WebOrderStatus> orderStatuses;
	public List<WebOrderStatus> getOrderStatuses() {
		return orderStatuses;
	}
	public void setOrderStatuses(List<WebOrderStatus> orderStatuses) {
		this.orderStatuses = orderStatuses;
	}
	
//  private OrderStatus orderStatus;

//current status
  private String status;
  public String getStatus() {
	  return status;
  }
  public void setStatus(String status) {
	  this.status = status;
  }

  //order menu items names
  private List<String> menuItemsNames;
  public List<String> getMenuItemsNames() {
	  return menuItemsNames;
  }
  
  public void getAllMenuItemsNames (List<MenuItem> menuItems) {
	  menuItemsNames = new ArrayList<>();
	  
	  for (MenuItem menuItem : menuItems) {
		  menuItemsNames.add(menuItem.getName());
	  }
  }
  
  //total cost
  private BigDecimal cost;
  public BigDecimal getCost() {
	  return cost;
  }
  public void setCost(BigDecimal cost) {
	  this.cost = cost;
  }
  
  //get order total cost
  public void getTotalCost(List<MenuItem> menuItems) {
	  cost = BigDecimal.ZERO;
	  for (MenuItem menuItem : menuItems) {
		  cost = cost.add(menuItem.getCost());
	  }
  }
  
//  CustomerInfo
  @NotNull(message = WebOrder.NOT_BLANK_MESSAGE)
  @NotEmpty(message = WebOrder.NOT_BLANK_MESSAGE)
  private String name;
  @NotNull(message = WebOrder.NOT_BLANK_MESSAGE)
  @NotEmpty(message = WebOrder.NOT_BLANK_MESSAGE)
  private String address1;
  @NotNull(message = WebOrder.NOT_BLANK_MESSAGE)
  @NotEmpty(message = WebOrder.NOT_BLANK_MESSAGE)
  private String postcode;
  
//  private String username;
  
  public void setId(String id) {
    this.id = id;
  }

  public void setDateTimeOfSubmission(Date dateTimeOfSubmission) {
	  this.dateTimeOfSubmission = dateTimeOfSubmission;
  }

//  public OrderStatus getStatus() {
//    return orderStatus;
//  }
//
//  public void setStatus(OrderStatus orderStatus) {
//    this.orderStatus = orderStatus;
//  }

  public Date getDateTimeOfSubmission() {
    return dateTimeOfSubmission;
  }

  public String getId() {
    return id;
  }
  
  public String getName() {
	  return name;
  }

  public void setName(String name) {
	  this.name = name;
  }

  public String getAddress1() {
	  return address1;
  }

  public void setAddress1(String address) {
	  this.address1 = address;
  }

  public String getPostcode() {
	  return postcode;
  }

  public void setPostcode(String postcode) {
	  this.postcode = postcode;
  }

//  public void setOrderItems(Map<String, Integer> orderItems) {
//    if (orderItems == null) {
//      this.orderItems = Collections.emptyMap();
//    } else {
//      this.orderItems = Collections.unmodifiableMap(orderItems);
//    }
//  }
  
  public void setOrderItems(Map<String, Integer> orderItems) {
	  if (orderItems == null) {
	      this.orderItems = Collections.emptyMap();
	    } else {
	    	this.orderItems = orderItems;
	    }
  }

  public Map<String, Integer> getOrderItems() {
    return orderItems;
  }
  
}