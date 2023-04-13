package net.r14s.moneymapper.configuration;

import javax.sql.DataSource;

import net.r14s.moneymapper.batch.TransactionProcessor;
import net.r14s.moneymapper.domain.RawTransaction;
import net.r14s.moneymapper.domain.Transaction;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MoneyMapperConfiguration {

	@Bean
	public FlatFileItemReader<RawTransaction> reader() {
		return new FlatFileItemReaderBuilder<RawTransaction>()
				.name("rawTransactionReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names(new String[] {"transactionId", "description", "amount"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<RawTransaction>() {{
									setTargetType(RawTransaction.class);
								}}
				).build();
	}

	@Bean
	public TransactionProcessor transactionProcessor() {
		return new TransactionProcessor();
	}

	//@Bean
	public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Transaction>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO money_transactions (transaction_id, transaction_date, transaction_user, description, amount, tag, category) " +
						"VALUES (:transaction_id, :transaction_date, :transaction_user, :description, :amount, :tag, :category)")
				.dataSource(dataSource)
				.build();
	}

	@Bean
	public FlatFileItemWriter<Transaction> writeFinalOutputToCSV() {

		return new FlatFileItemWriterBuilder<Transaction>()
				.name("txWriter")
				.resource(new PathResource("sample-out.txt"))
				.lineSeparator("\r\n")
				.delimited()
				.delimiter(",")
				.fieldExtractor(transaction -> new Object[] {transaction.getTransactionId(), transaction.getTransactionDate(),
						transaction.getDescription(), transaction.getAmount(), transaction.getTag() })
				.build();
	}


	@Bean
	public Step stepReadAndStoreRawTransactions(JobRepository jobRepository, PlatformTransactionManager transactionManager, FlatFileItemWriter<Transaction> writeFinalOutputToCSV) {
		return new StepBuilder("stepReadAndStoreRawTransactions", jobRepository)
				.<RawTransaction, Transaction>chunk(10, transactionManager)
				.reader(reader())
				.processor(transactionProcessor())
				.writer(writeFinalOutputToCSV)
				.build();
	}

	@Bean
	public Job moneyMapperJob(JobRepository jobRepository, Step stepReadAndStoreRawTransactions) {
		return new JobBuilder("moneyMapperJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.flow(stepReadAndStoreRawTransactions)
				.end()
				.build();
	}
}
