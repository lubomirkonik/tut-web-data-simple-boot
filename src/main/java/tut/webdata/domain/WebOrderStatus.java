package tut.webdata.domain;

import java.util.Date;

public class WebOrderStatus {
//	private String orderId;
//	private String id;
//	private Date statusDate;
	private String status;
	private boolean selected;
	
public WebOrderStatus(String status, boolean selected) {
		super();
		this.status = status;
		this.selected = selected;
	}

//	public String getOrderId() {
//		return orderId;
//	}
//	public void setOrderId(String orderId) {
//		this.orderId = orderId;
//	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public Date getStatusDate() {
//		return statusDate;
//	}
//	public void setStatusDate(Date statusDate) {
//		this.statusDate = statusDate;
//	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
