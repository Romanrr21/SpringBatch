package com.rom.employee.processor;

import com.rom.employee.model.Employee;

public class EmployeeItemProcessor implements org.springframework.batch.item.ItemProcessor<Employee, Employee>{

	@Override
	public Employee process(Employee emp) throws Exception {
		final String firstName= emp.getFirstName().toUpperCase();
		final String lastName=emp.getLastName().toUpperCase();
		
		 final Employee processedEmployee= new Employee(firstName, lastName);
		 System.out.println("Processed Employee:" +processedEmployee);
		return processedEmployee;
	}

}
