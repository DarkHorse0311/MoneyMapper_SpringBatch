package net.r14s.moneymapper.domain;

import java.util.Date;

public class RawTransaction {
	private Integer transactionId;
	private String description;
	private Double amount;

	public RawTransaction() {
	}

	public RawTransaction(Integer transactionId, String description, Double amount) {
		this.transactionId = transactionId;
		this.description = description;
		this.amount = amount;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
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
}
