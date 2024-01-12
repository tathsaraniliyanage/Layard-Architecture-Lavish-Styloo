package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.EmployeeDAO;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.dao.custom.UserDAO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.UserDTO;
import lk.ijse.lavishStyloo.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public EmployeeDTO findById(String nic) throws SQLException, ClassNotFoundException {
        return employeeDAO.findById(nic).toEntity();
    }

    public List<EmployeeDTO> findEmployee() throws SQLException, ClassNotFoundException {
        return toDTOList(employeeDAO.findAll());
    }

    public List<EmployeeDTO> toDTOList(List<Employee> employeeList) throws SQLException {
        List<EmployeeDTO> list = new ArrayList<>();
        for (Employee employee : employeeList) {
            list.add(employee.toEntity());
        }
        return list;
    }

    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(nic);
    }

    public List<EmployeeDTO> findEmployeeByLike(String searchText) throws SQLException, ClassNotFoundException {
        return toDTOList(employeeDAO.findEmployeeByLike(searchText));
    }

    public boolean save(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException {
        boolean save = employeeDAO.save(employeeDTO.toEntity());
        if (save) {
            boolean saveUser = userDAO.save(userDTO.toEntity());
            return saveUser;
        } else {
            return false;
        }
    }

    public boolean save(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(employeeDTO.toEntity());
    }

    public boolean update(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(employeeDTO.toEntity());
    }

    public boolean update(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException {
        if (update(employeeDTO)) {
            return userDAO.update(userDTO.toEntity());
        }
        return false;
    }

    public String CountEmployee() throws SQLException, ClassNotFoundException {
        return employeeDAO.CountEmployee();
    }

    public String CountAvailable() throws SQLException, ClassNotFoundException {
        return queryDAO.CountAvailable();
    }

    public boolean updateAndUserSave(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException {
        if (update(employeeDTO)) {
            return userDAO.update(userDTO.toEntity());
        }
        return false;
    }

    public boolean updateAndRemoveUser(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        if (update(employeeDTO)) {
            return userDAO.delete(employeeDTO.getNic());
        }
        return false;
    }

    public UserDTO getUser(String nic) throws SQLException, ClassNotFoundException {
        return userDAO.getUser(nic);
    }

    public List<EmployeeDTO> findEmployeeAvelebel() throws SQLException, ClassNotFoundException {
        return toDTOList(queryDAO.findEmployeeAvailable());
    }

}
