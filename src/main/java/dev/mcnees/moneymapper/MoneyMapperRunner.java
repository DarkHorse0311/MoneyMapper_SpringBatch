package dev.mcnees.moneymapper;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MoneyMapperRunner implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(MoneyMapperRunner.class);

	private final JobLauncher jobLauncher;

	private final Job moneyMapperJob;

	private final JdbcTemplate jdbcTemplate;

	public MoneyMapperRunner(JobLauncher jobLauncher, Job moneyMapperJob, DataSource dataSource) {
		this.jobLauncher = jobLauncher;
		this.moneyMapperJob = moneyMapperJob;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		clearProcessingData();

		List<File> allQuickenFiles = getAllQuickenFiles(new File("qfx_files"));

		JobParameters jobParameters;
		for (File quickenFile : allQuickenFiles) {
			jobParameters = new JobParametersBuilder()
					.addJobParameter("file", new JobParameter<>(quickenFile, File.class))
					.addJobParameter("datetime", new JobParameter<>(Instant.now().toEpochMilli(), Long.class))
					.toJobParameters();

			jobLauncher.run(moneyMapperJob, jobParameters);
		}
	}

	private void clearProcessingData() {
		jdbcTemplate.execute("delete from MONEY_MAPPER");
	}

	private List<File> getAllQuickenFiles(File folder) {
		List<File> fileList = new ArrayList<>();

		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile() && file.getName().endsWith(".QFX")) {
				log.info("Parser::getAllQuickenFiles -> found Quicken file: " + file.getName());
				fileList.add(file);
			}
		}
		return fileList;
	}

}