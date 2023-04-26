package repositories;

import org.springframework.data.repository.CrudRepository;

import module.Employee;

/**
 * Created by jt on 1/10/17.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
