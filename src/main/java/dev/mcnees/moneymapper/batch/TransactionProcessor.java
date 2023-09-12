package dev.mcnees.moneymapper.batch;

import java.util.List;

import dev.mcnees.moneymapper.configuration.MoneyMapperConstants;
import dev.mcnees.moneymapper.configuration.TransactionClassificationProperties;
import dev.mcnees.moneymapper.domain.Transaction;
import dev.mcnees.moneymapper.domain.TransactionClassification;

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

		for (TransactionClassification transactionClassification : transactionClassificationList) {

			if (item.getDescription().toUpperCase().contains(transactionClassification.description().toUpperCase())) {
				item.setTag(transactionClassification.tag());
				item.setCategory(transactionClassification.category());
				return item;
			}
		}

		if (item.getTag() != null && item.getDescription().equals(MoneyMapperConstants.AUTOMATIC_ACCOUNT_TRANSFER)) {
			return item;
		}

		item.setTag("General");
		return item;
	}
}
