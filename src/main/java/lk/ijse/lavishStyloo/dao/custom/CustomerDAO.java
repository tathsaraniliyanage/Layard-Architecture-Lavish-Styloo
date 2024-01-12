package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.CrudDAO;
import lk.ijse.lavishStyloo.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface CustomerDAO extends CrudDAO<lk.ijse.lavishStyloo.entity.Customer> {

    boolean save(Customer customerDTO) throws SQLException, ClassNotFoundException;

    boolean update(Customer customerDTO) throws SQLException, ClassNotFoundException;

    boolean delete(String colId) throws SQLException, ClassNotFoundException;

    List<Customer> findAll() throws SQLException, ClassNotFoundException;

    Customer findCustomerById(String customerId) throws SQLException, ClassNotFoundException;

    List<Customer> findCustomerByLike(String searchText) throws SQLException, ClassNotFoundException;

    String countCustomer() throws SQLException, ClassNotFoundException;

    List<Customer> toEntities(ResultSet resultSet) throws SQLException;

    String nextID() throws SQLException, ClassNotFoundException;
}
