package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import module.Employee;
import module.EmployeeTaxDetails;
import services.EmployeeService;


@RestController
public class EmployeeController {
   
	@Autowired
	private EmployeeService employeeService;

  
	 @PostMapping
	    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
	        if (employee.getId() == null || employee.getFirstName() == null || employee.getLastName() == null ||
	            employee.getEmail() == null || employee.getPhoneNumber() == null || employee.getDoj() == null ||
	            employee.getSalary() == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields");
	        }
	        if (!isValidPhoneNumber(employee.getPhoneNumber())) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
	        }
	        if (employee.getSalary() <= 0) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Salary must be a positive number");
	        }
	        employeeService.saveOrUpdate(employee);
	        return ResponseEntity.ok("Employee added successfully");
	    }
   
	 
	 @PostMapping("/employees/tax-deduction")
	    public ResponseEntity<Object> calculateTaxDeduction() {
	        List<EmployeeTaxDetails> employeeTaxDetailsList = new ArrayList<>();
	       List<Employee> employees= employeeService.getAllEmployess();
	        for (Employee employee : employees) {
	            double totalSalary = calculateTotalSalary(employee.getSalary(), employee.getDoj());
	            double taxAmount = calculateTaxAmount(totalSalary);
	            double cessAmount = calculateCessAmount(totalSalary);

	            EmployeeTaxDetails employeeTaxDetails = new EmployeeTaxDetails();
	            employeeTaxDetails.setEmployeeCode(employee.getId());
	            employeeTaxDetails.setFirstName(employee.getFirstName());
	            employeeTaxDetails.setLastName(employee.getLastName());
	            employeeTaxDetails.setYearlySalary(totalSalary * 12);
	            employeeTaxDetails.setTaxAmount(taxAmount);
	            employeeTaxDetails.setCessAmount(cessAmount);

	            employeeTaxDetailsList.add(employeeTaxDetails);
	        }

	        return new ResponseEntity<>(employeeTaxDetailsList, HttpStatus.OK);
	    }

	    private double calculateTotalSalary(double salary, Date doj) {
	        int numberOfMonths = calculateNumberOfMonths(doj);
	        double totalSalary = salary * numberOfMonths;
	        return totalSalary;
	    }

	    private int calculateNumberOfMonths(Date doj) {
	        Date aprilFirst = new Date(doj.getYear(), 3, 1);
	        int numberOfMonths = 12 - (aprilFirst.getMonth() - doj.getMonth()) % 12;
	        return numberOfMonths;
	    }

	    private double calculateTaxAmount(double totalSalary) {
	        double taxAmount = 0;

	        if (totalSalary > 250000 && totalSalary <= 500000) {
	            taxAmount = (totalSalary - 250000) * 0.05;
	        } else if (totalSalary > 500000 && totalSalary <= 1000000) {
	            taxAmount = 12500 + (totalSalary - 500000) * 0.1;
	        } else if (totalSalary > 1000000) {
	            taxAmount = 112500 + (totalSalary - 1000000) * 0.2;
	        }

	        return taxAmount;
	    }

	    private double calculateCessAmount(double totalSalary) {
	        double cessAmount = 0 ;
			return cessAmount;
	        		}

	    private boolean isValidPhoneNumber(List<String> phoneNumbers) {
	        for (String phoneNumber : phoneNumbers) {
	            if (!phoneNumber.matches("\\d{10}")) {
	                return false;
	            }
	        }
	        return true;
	    }


}
