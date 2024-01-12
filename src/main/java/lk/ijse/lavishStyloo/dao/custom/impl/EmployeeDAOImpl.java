package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.EmployeeDAO;
import lk.ijse.lavishStyloo.dao.custom.UserDAO;
import lk.ijse.lavishStyloo.entity.Employee;
import lk.ijse.lavishStyloo.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class EmployeeDAOImpl implements EmployeeDAO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    public boolean save(Employee employee, User user) throws SQLException, ClassNotFoundException {
        boolean save = save(employee);
        if (save) {
            boolean saveUser = userDAO.save(user);
            return saveUser;
        } else {
            return false;
        }
    }

    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        return CrudUtil.crudUtil(sql,
                employee.getNic(),
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getEmail(),
                employee.getCity(),
                employee.getLane(),
                employee.getStreet(),
                employee.getContact(),
                employee.getDateOfBirth(),
                employee.getGender(),
                employee.getRole()
        );
    }

    public boolean update(Employee employee, User user) throws SQLException, ClassNotFoundException {
        if (update(employee)) {
            return userDAO.update(user);
        }
        return false;
    }

    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employee SET first_name=?, last_name=?, email=?, city=?, lane=?, street=?, contact=?, dateOfBirth=?, gender=?, `role`=? WHERE nic = ?";
        return CrudUtil.crudUtil(sql,
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getEmail(),
                employee.getCity(),
                employee.getLane(),
                employee.getStreet(),
                employee.getContact(),
                employee.getDateOfBirth(),
                employee.getGender(),
                employee.getRole(),
                employee.getNic()
        );
    }

    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE from employee where nic=?", nic);
    }

    public List<Employee> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee";
        ResultSet result = CrudUtil.crudUtil(sql);
        return toEntities(result);
    }

    public Employee findById(String nic) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee WHERE nic =?";
        ResultSet result = CrudUtil.crudUtil(sql, nic);
        return toEntity(result);

    }

    public List<Employee> findEmployeeByLike(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee where nic LIKE ? or first_name LIKE ? or last_name LIKE ? or contact LIKE ? or city LIKE ? or gender LIKE ?";
        String arg = searchText + "%";
        ResultSet result = CrudUtil.crudUtil(sql, arg, arg, arg, arg, arg, arg);
        return toEntities(result);
    }

    public String CountEmployee() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM  employee";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public boolean updateAndRemoveUser(Employee employee) throws SQLException, ClassNotFoundException {
        if (update(employee)) {
            return userDAO.delete(employee.getNic());
        }
        return false;
    }

    public boolean updateAndUserSave(Employee employee, User user) throws SQLException, ClassNotFoundException {
        if (update(employee)) {
            return userDAO.save(user);
        }
        return false;
    }

    public List<Employee> toEntities(ResultSet result) throws SQLException {
        List<Employee> list = new ArrayList<>();

        while (result.next()) {
            Employee employee = new Employee();
            employee.setNic(result.getString(1));
            employee.setFirst_name(result.getString(2));
            employee.setLast_name(result.getString(3));
            employee.setEmail(result.getString(4));
            employee.setCity(result.getString(5));
            employee.setLane(result.getString(6));
            employee.setStreet(result.getString(7));
            employee.setContact(result.getString(8));
            employee.setDateOfBirth(LocalDate.parse(result.getString(9)));
            employee.setGender(result.getString(10));
            list.add(employee);
        }
        return list;
    }

    public Employee toEntity(ResultSet result) throws SQLException {
        Employee employeeDTO = new Employee();
        if (result.next()) {
            employeeDTO.setNic(result.getString(1));
            employeeDTO.setFirst_name(result.getString(2));
            employeeDTO.setLast_name(result.getString(3));
            employeeDTO.setEmail(result.getString(4));
            employeeDTO.setCity(result.getString(5));
            employeeDTO.setLane(result.getString(6));
            employeeDTO.setStreet(result.getString(7));
            employeeDTO.setContact(result.getString(8));
            employeeDTO.setDateOfBirth(LocalDate.parse(result.getString(9)));
            employeeDTO.setGender(result.getString(10));
            employeeDTO.setRole(result.getString(11));
        }
        return employeeDTO;
    }

}
