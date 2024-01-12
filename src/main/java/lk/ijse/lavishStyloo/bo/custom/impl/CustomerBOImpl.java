package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.CustomerDAO;
import lk.ijse.lavishStyloo.dao.custom.CustomerOrderDAO;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    CustomerOrderDAO customerOrderDAO = (CustomerOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_ORDER);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);


    public List<CustomerDTO> findAll() throws SQLException, ClassNotFoundException {
        return toDTO(customerDAO.findAll());
    }

    public boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(customerDTO.toEntity());
    }

    public boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(customerDTO.toEntity());
    }

    public CustomerDTO findCustomerById(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.findCustomerById(customerId).toDTO();
    }

    public List<CustomerDTO> findCustomerByLike(String searchText) throws SQLException, ClassNotFoundException {
        return toDTO(customerDAO.findCustomerByLike(searchText));
    }

    private List<CustomerDTO> toDTO(List<Customer> customerList) throws SQLException {
        List<CustomerDTO> list = new ArrayList<>();
        for (Customer customer : customerList) {
            list.add(customer.toDTO());
        }
        return list;
    }

    public String nextID() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> ids = findAll();
        String oldId = null;
        for (CustomerDTO customerDTO : ids) {
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

    public String countCustomer() throws SQLException, ClassNotFoundException {
        return customerDAO.countCustomer();
    }

    public List<ReportCm> getCustomerOrder() throws SQLException, ClassNotFoundException {
        return customerOrderDAO.getCustomerOrder();
    }

    public boolean delete(String colId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(colId);
    }

    public List<ReportCm> getYearlyCustomerOrder(String year) throws SQLException, ClassNotFoundException {
        return customerOrderDAO.getYearlyCustomerOrder(year);
    }

    public List<ReportCm> getMonthlyCustomerOrder(String year, String moth) throws SQLException, ClassNotFoundException {
        return customerOrderDAO.getMonthlyCustomerOrder(year, moth);
    }

    public List<ReportCm> getDayCustomerOrder(String year, String month) throws SQLException, ClassNotFoundException {
        return customerOrderDAO.getDayCustomerOrder(year, month);
    }

}
