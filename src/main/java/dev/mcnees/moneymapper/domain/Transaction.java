package dev.mcnees.moneymapper.domain;

import java.util.Date;

public class Transaction {

	public Transaction() {

	}

	public Transaction(String id, Date date, String description, Double amount, String tag, String category) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.tag = tag;
		this.category = category;
	}

	private String id;

	private Date date;

	private String description;

	private Double amount;

	private String tag;

	private String category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
