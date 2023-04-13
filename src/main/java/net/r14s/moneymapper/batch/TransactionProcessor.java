package net.r14s.moneymapper.batch;

import java.util.Date;

import net.r14s.moneymapper.domain.RawTransaction;
import net.r14s.moneymapper.domain.Transaction;

import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<RawTransaction, Transaction> {


	@Override
	public Transaction process(RawTransaction item) throws Exception {
		System.out.println("processing item " + item.getDescription());
		return new Transaction(item.getTransactionId(), new Date(), "user", item.getDescription(), item.getAmount(), "tag", "category");
	}
}
