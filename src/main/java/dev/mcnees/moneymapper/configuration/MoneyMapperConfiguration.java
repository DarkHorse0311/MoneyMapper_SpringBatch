package dev.mcnees.moneymapper.configuration;

import dev.mcnees.moneymapper.batch.MultilineQFXReader;
import dev.mcnees.moneymapper.domain.Transaction;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MoneyMapperConfiguration {


	@Bean
	public FlatFileItemWriter<Transaction> writeFinalOutputToCSV() {

		return new FlatFileItemWriterBuilder<Transaction>()
				.name("txWriter")
				.resource(new PathResource("sample-out.txt"))
				.lineSeparator("\r\n")
				.delimited()
				.delimiter(",")
				.fieldExtractor(transaction -> new Object[] {transaction.getDescription(), transaction.getAmount(), transaction.getTag(), transaction.getCategory()})
				.build();
	}


	@Bean
	public ItemReader itemReader() {
		return new MultilineQFXReader(new ClassPathResource("quickenExport-sample.QFX"));
	}

	@Bean
	public Step stepReadAndStoreRawTransactions(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			ItemReader<Transaction> itemReader,
			ItemProcessor<Transaction, Transaction> itemProcessor,
			FlatFileItemWriter<Transaction> itemWriter) {
		return new StepBuilder("stepReadAndStoreRawTransactions", jobRepository)
				.<Transaction, Transaction>chunk(10, transactionManager)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
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
