package edu.cscc.hibernate_test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean(name="search")
@RequestScoped
public class SearchBean {
	private String total;
    private Long id;
    private String customer;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String test) {
		this.total = test;
	}
	
	public void lookup() {
		Lookup search = new Lookup();
		customer = search.getCustomerByID(id).toString();
	}

}
