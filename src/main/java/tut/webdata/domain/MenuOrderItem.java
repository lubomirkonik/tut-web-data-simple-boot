package tut.webdata.domain;

import java.math.BigDecimal;

public class MenuOrderItem {
	
	private String id;
	private String name;
	private BigDecimal cost;
	private int minutesToPrepare;
	private int quantity;
	private boolean chosen;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public int getMinutesToPrepare() {
		return minutesToPrepare;
	}
	public void setMinutesToPrepare(int minutesToPrepare) {
		this.minutesToPrepare = minutesToPrepare;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isChosen() {
		return chosen;
	}
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
}