package entity;
import util.CustomerType;
public class Customer {
	CustomerType customerType;
	String customerName;
	int id;
	public Customer(int id, CustomerType customerType, String customerName) {
		super();
		this.id = id;
		this.customerType = customerType;
		this.customerName = customerName;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
