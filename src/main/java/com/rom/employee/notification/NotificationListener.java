package com.rom.employee.notification;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.rom.employee.model.Employee;
@Component
public class NotificationListener extends JobExecutionListenerSupport {
	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public void afterJob(JobExecution jobExecution) {
		RowMapper rowMapper = (rs, rowNum) -> {
			Employee e = new Employee();
			e.setFirstName(rs.getString(1));
			e.setLastName(rs.getString(2));
			return e;

		};
		List<Employee> empList= jdbcTemplate.query("SELECT first_name, last_name FROM employee", rowMapper);
		System.out.println("Size of List " + empList.size());
		
		empList.forEach(x->System.out.println("Found: "+ x));
		
		
	}
}
