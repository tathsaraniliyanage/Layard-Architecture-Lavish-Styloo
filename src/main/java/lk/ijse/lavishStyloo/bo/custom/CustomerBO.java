package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface CustomerBO extends SuperBO {

    List<CustomerDTO> findAll() throws SQLException, ClassNotFoundException;

    boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    CustomerDTO findCustomerById(String customerId) throws SQLException, ClassNotFoundException;

    List<CustomerDTO> findCustomerByLike(String searchText) throws SQLException, ClassNotFoundException;

    String nextID() throws SQLException, ClassNotFoundException;

    String countCustomer() throws SQLException, ClassNotFoundException;

    List<ReportCm> getCustomerOrder() throws SQLException, ClassNotFoundException;

    boolean delete(String colId) throws SQLException, ClassNotFoundException;

    List<ReportCm> getYearlyCustomerOrder(String year) throws SQLException, ClassNotFoundException;

    List<ReportCm> getMonthlyCustomerOrder(String year, String moth) throws SQLException, ClassNotFoundException;

    List<ReportCm> getDayCustomerOrder(String year, String month) throws SQLException, ClassNotFoundException;
}
