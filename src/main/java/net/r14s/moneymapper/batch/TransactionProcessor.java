package net.r14s.moneymapper.batch;

import net.r14s.moneymapper.domain.Transaction;

import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {


	@Override
	public Transaction process(Transaction item) throws Exception {
		System.out.println("processing item " + item.getDescription());
		return item;
	}
}
