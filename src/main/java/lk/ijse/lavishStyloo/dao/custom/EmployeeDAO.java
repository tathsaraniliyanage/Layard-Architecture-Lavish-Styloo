package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.CrudDAO;
import lk.ijse.lavishStyloo.entity.Employee;
import lk.ijse.lavishStyloo.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */


public interface EmployeeDAO extends CrudDAO<Employee> {

    boolean save(Employee employee, User user) throws SQLException, ClassNotFoundException;

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    boolean update(Employee employee, User user) throws SQLException, ClassNotFoundException;

    List<Employee> findAll() throws SQLException, ClassNotFoundException;

    Employee findById(String nic) throws SQLException, ClassNotFoundException;

    List<Employee> findEmployeeByLike(String searchText) throws SQLException, ClassNotFoundException;

    Employee toEntity(ResultSet result) throws SQLException;

    List<Employee> toEntities(ResultSet result) throws SQLException;

    String CountEmployee() throws SQLException, ClassNotFoundException;

    boolean updateAndUserSave(Employee employee, User user) throws SQLException, ClassNotFoundException;

    boolean updateAndRemoveUser(Employee employeeDTO) throws SQLException, ClassNotFoundException;

}
