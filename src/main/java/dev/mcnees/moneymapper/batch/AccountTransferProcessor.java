package dev.mcnees.moneymapper.batch;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import dev.mcnees.moneymapper.domain.Transaction;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AccountTransferProcessor implements ItemProcessor<Transaction, Transaction> {

	private final JdbcTemplate jdbcTemplate;

	public AccountTransferProcessor(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Transaction process(Transaction item) throws Exception {

		List<Transaction> matchingAmountTransactions = jdbcTemplate.query("select id, amount from MONEY_MAPPER where AMOUNT = ?",
				new Object[] { -item.getAmount() },
				new int[] { Types.FLOAT },
				(rs, rowNum) -> new Transaction(rs.getString("id"), null, rs.getDouble("amount"), null, null));

		// TODO: match on date as well.  transactions need to be close together to be considered a transfer
		// Can add a flag to the MONEY_MAPPER table to not report these records to the final CSV
		if(CollectionUtils.isEmpty(matchingAmountTransactions)) {
			item.setTag("Automatic Add - Likely Account Transfer");
		}

		return item;
	}
}
