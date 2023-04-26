package services;
import module.Employee;
import repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService  {

	@Autowired
    private EmployeeRepository employeeRepository;
   

    public List<Employee> getAllEmployess() {
        @SuppressWarnings("unused")
		List<Employee> employees = new ArrayList<>();
       return (List<Employee>) employeeRepository.findAll();
        
    }

    public Employee saveOrUpdate(Employee employee) {
    	employeeRepository.save(employee);
        return employee;
    }

   

}
