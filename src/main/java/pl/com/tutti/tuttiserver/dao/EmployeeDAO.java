
package pl.com.tutti.tuttiserver.dao;

import java.util.List;

import pl.com.tutti.tuttiserver.entity.Employee;

public interface EmployeeDAO {

    public List<Employee> findAll();

}
