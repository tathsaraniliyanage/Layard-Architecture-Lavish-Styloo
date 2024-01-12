package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.CustomerDAO;
import lk.ijse.lavishStyloo.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class CustomerDAOImpl implements CustomerDAO {

    public boolean save(Customer customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?)";
        return CrudUtil.crudUtil(sql,
                customerDTO.getCustomer_id(),
                customerDTO.getFirst_name(),
                customerDTO.getLast_name(),
                customerDTO.getEmail(),
                customerDTO.getCity(),
                customerDTO.getLane(),
                customerDTO.getStreet(),
                customerDTO.getContact()
        );
    }

    public boolean update(Customer customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET first_name=?,last_name=?,email=?,city=?,lane=?,street=?,contact=? WHERE cust_id=?";
        return CrudUtil.crudUtil(sql,
                customerDTO.getFirst_name(),
                customerDTO.getLast_name(),
                customerDTO.getEmail(),
                customerDTO.getCity(),
                customerDTO.getLane(),
                customerDTO.getStreet(),
                customerDTO.getContact(),
                customerDTO.getCustomer_id()
        );
    }

    public boolean delete(String colId) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  from customer where cust_id=?", colId);
    }

    public List<Customer> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM customer");
        return toEntities(resultSet);
    }

    public Customer findCustomerById(String customerId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT * FROM customer WHERE cust_id=?", customerId);
        Customer customerDTO = null;
        if (result.next()) {
            customerDTO = new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8)
            );
        }
        return customerDTO;
    }

    public List<Customer> findCustomerByLike(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE cust_id LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR city LIKE ? OR street LIKE ? OR lane LIKE ? OR contact LIKE ? OR email LIKE ?";
        String args = searchText + "%";
        ResultSet resultSet = CrudUtil.crudUtil(sql,
                args, args, args, args, args, args, args, args
        );
        return toEntities(resultSet);
    }

    public String countCustomer() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT COUNT(cust_id) FROM customer");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public List<Customer> toEntities(ResultSet resultSet) throws SQLException {
        List<Customer> list = new ArrayList<>();
        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
            list.add(customer);
        }
        return list;
    }

    public String nextID() throws SQLException, ClassNotFoundException {
        List<Customer> ids = findAll();
        String oldId = null;
        for (Customer customerDTO : ids) {
            oldId = customerDTO.getCustomer_id();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("C00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "C001";
        }
        lastIndex++;
        return "C00" + lastIndex;
    }

}
