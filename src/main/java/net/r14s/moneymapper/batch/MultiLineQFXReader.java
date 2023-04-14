package net.r14s.moneymapper.batch;

import net.r14s.moneymapper.domain.Transaction;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class MultiLineQFXReader extends FlatFileItemReader<Transaction> {

	public MultiLineQFXReader() {
		setResource(new ClassPathResource("quickenExport-snippet.QFX"));
		//setLineMapper(new QfxLineMapper());
		DefaultLineMapper lineMapper = new DefaultLineMapper();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames("description", "amount");

		BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<Transaction>();
		fieldSetMapper.setTargetType(Transaction.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		setLineMapper(lineMapper);

	}

	@Override
	public void afterPropertiesSet() throws Exception {

		setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());

		super.afterPropertiesSet();
	}
}

class QfxLineMapper implements LineMapper<Transaction> {

	@Override
	public Transaction mapLine(String line, int lineNumber) throws Exception {
		System.out.println("Mapping line: " + line);
		return null;
	}
}

class QfxFieldMapper<Transaction> extends BeanWrapperFieldSetMapper<Transaction> {

}
class BlankLineRecordSeparatorPolicy extends SimpleRecordSeparatorPolicy {

	@Override
	public boolean isEndOfRecord(final String line) {
		return line.trim().length() != 0 && super.isEndOfRecord(line);
	}

	@Override
	public String postProcess(final String record) {
		if (record == null || record.trim().length() == 0) {
			return null;
		} return super.postProcess(record);
	}

}