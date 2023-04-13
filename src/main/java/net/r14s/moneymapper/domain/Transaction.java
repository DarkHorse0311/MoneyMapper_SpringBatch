package net.r14s.moneymapper.domain;

import java.util.Date;

public class Transaction {

	private Integer transactionId;
	private Date transactionDate;
	private String transactionUser;
	private String description;
	private Double amount;
	private String tag;
	private String category;

	public Transaction() {
	}

	public Transaction(Integer transactionId, Date transactionDate, String transactionUser, String description, Double amount, String tag, String category) {
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.transactionUser = transactionUser;
		this.description = description;
		this.amount = amount;
		this.tag = tag;
		this.category = category;
	}

	public Transaction(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionUser() {
		return transactionUser;
	}

	public void setTransactionUser(String transactionUser) {
		this.transactionUser = transactionUser;
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
