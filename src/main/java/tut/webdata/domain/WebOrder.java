package tut.webdata.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.UUID;



import java.util.Set;

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
  				// orderItems.put(item.getId(), 1);
  				orderItems.put(item.getId(), item.getQuantity());
  			}
  		}
  		this.orderItems = orderItems;
  	}
  
  	private List<MenuOrderItem> menuAndOrderItems;
  	public List<MenuOrderItem> getMenuAndOrderItems() {
		return menuAndOrderItems;
	}
	public void setMenuAndOrderItems(List<MenuOrderItem> items) {
		for (MenuOrderItem item: items) {
			if (!item.isChosen() && item.getQuantity() != 0) {
				item.setQuantity(0);
			}
		}
		this.menuAndOrderItems = items;
	}
	// to-do return particular object with error
	public boolean checkMenuAndOrderItems() {
		for (MenuOrderItem item : this.menuAndOrderItems) {
			if (item.isChosen() && item.getQuantity() == 0) {
				return false;
			}
		}
		return true;
	}
//	public MenuOrderItem checkMenuAndOrderItems() {
//		for (MenuOrderItem item : this.menuAndOrderItems) {
//			if (item.isChosen() && item.getQuantity() == 0) {
//				return item;
//			}
//		}
//		return null;
//	}
	public List<MenuOrderItem> initMenuAndOrderItems(List<MenuItem> menuItems) {
		Map<String, Integer> orderItems = this.orderItems;
		Set<String> orderItemsKeys = this.orderItems.keySet();
		
		List<MenuOrderItem> menuAndOrderItems = new ArrayList<>();
		
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
			for (String id : orderItemsKeys) {
				if (menuItem.getId().equals(id)) {
					menuOrderItem.setChosen(true);
					menuOrderItem.setQuantity(orderItems.get(id)); //menuItem.getId()
					break;
				}
			}
		}
		
		this.menuAndOrderItems = menuAndOrderItems;
		return menuAndOrderItems;
	}

	private List<WebOrderStatus> orderStatuses;
	public List<WebOrderStatus> getOrderStatuses() {
		return orderStatuses;
	}
	public void setOrderStatuses(List<WebOrderStatus> orderStatuses) {
		this.orderStatuses = orderStatuses;
	}

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
  
  private Map<String, BigDecimal> orderItemsTotalCosts;
  private void calculateOrderItemsTotalCosts(List<MenuItem> menuItems) {
	  List<MenuOrderItem> menuAndOrderItems = new ArrayList<>();
	  menuAndOrderItems = initMenuAndOrderItems(menuItems);
	  
	  List<MenuOrderItem> orderItems = new ArrayList<>();
	  for (MenuOrderItem item : menuAndOrderItems) {
		  if (item.isChosen()) {
			  orderItems.add(item);
		  }
	  }
	  
	  Map<String, BigDecimal> orderItemsTotalCosts = new HashMap<>();
	  
	  for (MenuOrderItem item : orderItems) {
		  BigDecimal itemTotalCost = item.getCost().multiply(BigDecimal.valueOf(item.getQuantity()));
		  orderItemsTotalCosts.put(item.getId(), itemTotalCost);
	  }
	  
	  this.orderItemsTotalCosts = orderItemsTotalCosts;
  }
  
  //total cost
  private BigDecimal cost;
  public BigDecimal getCost() {
	  return cost;
  }
  
  //get order total cost
  public void calculateTotalCost(List<MenuItem> menuItems) {
	  calculateOrderItemsTotalCosts(menuItems);
	  cost = BigDecimal.ZERO;
	  List<BigDecimal> totalItemsCosts = new ArrayList<>(this.orderItemsTotalCosts.values());
	  for (BigDecimal totalItemCost : totalItemsCosts) {
		  cost = cost.add(totalItemCost);
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