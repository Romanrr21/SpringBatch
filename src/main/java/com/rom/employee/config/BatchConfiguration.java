package com.rom.employee.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.rom.employee.model.Employee;
import com.rom.employee.notification.NotificationListener;
import com.rom.employee.processor.EmployeeItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<Employee> reader() {
		FlatFileItemReader<Employee> fileReader = new FlatFileItemReaderBuilder().name("EmployeeItemReader")
				.resource(new ClassPathResource("employee.csv")).delimited()
				.names(new String[] { "firstName", "lastName" }).fieldSetMapper(new BeanWrapperFieldSetMapper() {
					{
						setTargetType(Employee.class);
					}
				}).build();
		return fileReader;

	}

	@Bean
	public JdbcBatchItemWriter writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO employee (first_name, last_name) VALUES(:firstName, :lastName)")
				.dataSource(dataSource).build();
	}

	@Bean
	public EmployeeItemProcessor processor() {
		return new EmployeeItemProcessor();

	}
@Bean
public Job importUserJob(NotificationListener listener, Step step1) {
	return jobBuilderFactory.get("importUserJob")
			.incrementer(new RunIdIncrementer())
			.listener(listener)
			.flow(step1)
			.end()
			.build();
}
	@Bean
	public Step step1(JdbcBatchItemWriter writer) {
		Step st = stepBuilderFactory.get("step1").<Employee, Employee>chunk(2).reader(reader()).processor(processor())
				.writer(writer).build();
		System.out.println("----------two completed---------");
		return st;

	}

}
