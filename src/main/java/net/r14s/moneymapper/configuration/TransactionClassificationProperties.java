package net.r14s.moneymapper.configuration;

import java.util.List;

import net.r14s.moneymapper.domain.TransactionClassification;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "transactionclassification")
public class TransactionClassificationProperties {
	List<TransactionClassification> configurations;

	public List<TransactionClassification> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<TransactionClassification> configurations) {
		this.configurations = configurations;
	}
}
