package tut.webdata.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.gemfire.mapping.Region;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

// {!begin gemfire}
@Region("YummyNoodleOrder")
public class OrderStatus implements Serializable {

  private UUID orderId;
  @Id
  private UUID id;
  // {!end gemfire}
  private Date statusDate;
  private String status;

  public OrderStatus(UUID orderId, UUID id, final Date date, final String status) {
    this.orderId = orderId;
    this.id = id;
    this.status = status;
    this.statusDate = date;
  }

  public OrderStatus() {

  }

  public Date getStatusDate() {
    return statusDate;
  }

  public String getStatus() {
    return status;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public UUID getId() {
    return id;
  }

  public void setOrderId(UUID orderId) {
    this.orderId = orderId;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setStatusDate(Date statusDate) {
    this.statusDate = statusDate;
  }

  public void setStatus(String status) {
    this.status = status;
  }

//  public OrderStatusDetails toStatusDetails() {
//    return new OrderStatusDetails(orderId, id, statusDate, status);
//  }
//
//  public static OrderStatus fromStatusDetails(OrderStatusDetails orderStatusDetails) {
//    return new OrderStatus(
//        orderStatusDetails.getOrderId(), orderStatusDetails.getId(),
//        orderStatusDetails.getStatusDate(), orderStatusDetails.getStatus());
//  }
  
  @Transient
  private String name;
  public String getName() {
	  return name;
  }
  public void setName(String name) {
	  this.name = name;
  }
  
  private boolean entityFound = true;
  public static OrderStatus notFound(UUID key) {
	  OrderStatus ev = new OrderStatus();
	  ev.id = key;
	  ev.entityFound=false;
	  return ev;
  }
}
