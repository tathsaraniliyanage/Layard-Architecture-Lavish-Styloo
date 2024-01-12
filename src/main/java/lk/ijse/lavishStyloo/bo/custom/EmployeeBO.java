package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.UserDTO;
import lk.ijse.lavishStyloo.entity.Employee;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface EmployeeBO extends SuperBO {
    boolean save(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    EmployeeDTO findById(String nic) throws SQLException, ClassNotFoundException;

    List<EmployeeDTO> findEmployee() throws SQLException, ClassNotFoundException;

    List<EmployeeDTO> toDTOList(List<Employee> employeeList) throws SQLException;

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    List<EmployeeDTO> findEmployeeByLike(String searchText) throws SQLException, ClassNotFoundException;

    boolean save(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException;

    boolean update(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    boolean update(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException;

    String CountEmployee() throws SQLException, ClassNotFoundException;

    String CountAvailable() throws SQLException, ClassNotFoundException;

    boolean updateAndUserSave(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException;

    boolean updateAndRemoveUser(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    UserDTO getUser(String nic) throws SQLException, ClassNotFoundException;

    List<EmployeeDTO> findEmployeeAvelebel() throws SQLException, ClassNotFoundException;
}
