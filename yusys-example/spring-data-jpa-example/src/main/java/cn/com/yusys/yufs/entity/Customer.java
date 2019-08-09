package cn.com.yusys.yufs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_customer")
public class Customer {
	
	@Id
    @Column(name = "customer_id")
	private String customerId;
	
    @Column(name = "customer_name")
	private String customerName;
	
	private String gender;
	
	public Customer() { }

	public Customer(String customerId, String customerName, String gender) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.gender = gender;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
