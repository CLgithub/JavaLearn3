package day81activity.day2;

import java.io.Serializable;
import java.util.Date;

public class AppayBillBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer cost;
	private String appayPerson;
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getAppayPerson() {
		return appayPerson;
	}

	public void setAppayPerson(String appayPerson) {
		this.appayPerson = appayPerson;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "AppayBillBean [id=" + id + ", cost=" + cost + ", appayPerson=" + appayPerson + ", date=" + date + "]";
	}

}
