package tut.webdata.domain;

//import tut.webdata.events.orders.OrderDetails;

import javax.persistence.*;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
//import java.util.UUID;

@Entity(name = "NOODLE_ORDERS")
public class Order {

  @Column(name = "SUBMISSION_DATETIME")
  private Date dateTimeOfSubmission;

  @ElementCollection(fetch = FetchType.EAGER, targetClass = java.lang.Integer.class)
  @JoinTable(name="ORDER_ORDER_ITEMS", joinColumns=@JoinColumn(name="ID"))
  @MapKeyColumn(name="MENU_ID")
  @Column(name="VALUE")
  private Map<String, Integer> orderItems;

  @Transient
  private OrderStatus orderStatus;
  
  @Id
  @Column(name = "ORDER_ID")
  private String id;
  
//  CustomerInfo
  @Column(name = "CUSTOMER_NAME")
  private String name;
  @Column(name = "ADDRESS")
  private String address1;
  @Column(name = "POSTCODE")
  private String postcode;
  
//  private Long userId;
  
  public void setId(String id) {
    this.id = id;
  }

  public void setDateTimeOfSubmission(Date dateTimeOfSubmission) {
	  this.dateTimeOfSubmission = dateTimeOfSubmission;
  }

  public OrderStatus getStatus() {
    return orderStatus;
  }

  public void setStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
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

//  public OrderDetails toOrderDetails() {
//    OrderDetails details = new OrderDetails();
//
//    details.setKey(UUID.fromString(this.id));
//    details.setDateTimeOfSubmission(this.dateTimeOfSubmission);
//    details.setOrderItems(this.getOrderItems());
//    details.setName(name);
//    details.setAddress1(address1);
//    details.setPostcode(postcode);
//
//    return details;
//  }
//
//  public static Order fromOrderDetails(OrderDetails orderDetails) {
//    Order order = new Order();
//
//    order.id = orderDetails.getKey().toString();
//    order.dateTimeOfSubmission = orderDetails.getDateTimeOfSubmission();
//    order.orderItems = orderDetails.getOrderItems();
//    order.name = orderDetails.getName();
//    order.address1 = orderDetails.getAddress1();
//    order.postcode = orderDetails.getPostcode();
//
//    return order;
//  }
  
  private boolean entityFound = true;
  public static Order notFound(String key) {
	  Order ev = new Order();
	  ev.id = key;
	  ev.entityFound=false;
	  return ev;
  }
}