package net.r14s.moneymapper.batch;

import java.util.List;

import net.r14s.moneymapper.configuration.TransactionClassificationProperties;
import net.r14s.moneymapper.domain.Transaction;
import net.r14s.moneymapper.domain.TransactionClassification;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {

	private final List<TransactionClassification> transactionClassificationList;

	public TransactionProcessor(TransactionClassificationProperties transactionClassificationProperties) {
		this.transactionClassificationList = transactionClassificationProperties.getConfigurations();
	}

	@Override
	public Transaction process(Transaction item) throws Exception {

		for(TransactionClassification transactionClassification : transactionClassificationList) {

			if(item.getDescription().toUpperCase().contains(transactionClassification.description().toUpperCase())) {
				item.setTag(transactionClassification.tag());
				item.setCategory(transactionClassification.category());
				return item;
			}
		}
		return item;
	}
}
