package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.CustomerOrderBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.CustomerOrderDAO;
import lk.ijse.lavishStyloo.dao.custom.OrderDetailsDAO;
import lk.ijse.lavishStyloo.dao.custom.ProductDAO;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.OrderDTO;
import lk.ijse.lavishStyloo.dto.OrderDetailsDTO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.projection.CustomerOrderDetailsProjection;
import lk.ijse.lavishStyloo.dto.projection.CustomerOrderProjecton;
import lk.ijse.lavishStyloo.dto.tm.CustomerOrderDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.CustomerOrderTm;
import lk.ijse.lavishStyloo.entity.OrderDetails;
import lk.ijse.lavishStyloo.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class CustomerOrderBOImpl implements CustomerOrderBO {
    CustomerOrderDAO customerOrderDAO = (CustomerOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public String CountCustomerOrder() throws SQLException, ClassNotFoundException {
        return customerOrderDAO.CountCustomerOrder();
    }

    public String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException {
        return customerOrderDAO.CountCustomerOrderByDate();
    }

    public List<CustomerOrderDetailsTm> findCustomerOrderDetailsByOrderId(String customerOrderId) throws SQLException, ClassNotFoundException {
        return toODTm(queryDAO.findCustomerAndOrderDetailsByOrderId(customerOrderId));
    }

    private List<CustomerOrderDetailsTm> toODTm(List<CustomerOrderDetailsProjection> resultSet) throws SQLException {
        List<CustomerOrderDetailsTm> list = new ArrayList<>();
        for (CustomerOrderDetailsProjection projection : resultSet) {
            list.add(projection.toTM());
        }
        return list;
    }

    public List<CustomerOrderTm> findCustomerOrdersByLike(String text) throws SQLException, ClassNotFoundException {
        return toTm(queryDAO.findCustomerAndOrdersByLike(text));
    }

    public List<CustomerOrderTm> findCustomerOrders() throws SQLException, ClassNotFoundException {
        return toTm(queryDAO.findCustomerAndOrders());
    }

    private List<CustomerOrderTm> toTm(List<CustomerOrderProjecton> resultSet) throws SQLException {
        List<CustomerOrderTm> list = new ArrayList<>();
        for (CustomerOrderProjecton projecton : resultSet) {
            list.add(projecton.toTm());
        }
        return list;
    }

    public List<CustomerOrderTm> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException {
        return toTm(queryDAO.findCustomerAndOrdersByDate(date));
    }

    /**
     *
     */
    public boolean placeOrder(ArrayList<OrderDetailsDTO> list, OrderDTO orderDTO, List<ProductDTO> productDTOS) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = customerOrderDAO.save(orderDTO.toEntity());
            if (isSaved) {
                boolean isSavedDetails = orderDetailsDAO.save(dtoList(list));
                if (isSavedDetails) {
                    boolean isUpdated = productDAO.update(dtoProductList(productDTOS));
                    if (isUpdated) {
                        return true;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private List<OrderDetails> dtoList(ArrayList<OrderDetailsDTO> list) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        for (OrderDetailsDTO dto : list) {
            orderDetails.add(dto.toEntity());
        }
        return orderDetails;
    }

    private ArrayList<Product> dtoProductList(List<ProductDTO> list) {
        ArrayList<Product> orderDetails = new ArrayList<>();
        for (ProductDTO dto : list) {
            orderDetails.add(dto.toEntity());
        }
        return orderDetails;
    }

    public List<String> findDistinctYears() throws SQLException, ClassNotFoundException {
        return queryDAO.findDistinctYears();
    }

    public String next() throws SQLException, ClassNotFoundException {
        List<String> ids = findID();
        String oldId = null;
        for (String id : ids) {
            oldId = id;
        }
        int lastIndex;
        try {
            String[] split = oldId.split("O00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "O001";
        }
        lastIndex++;
        return "O00" + lastIndex;
    }

    public List<String> findID() throws SQLException, ClassNotFoundException {
        return customerOrderDAO.findID();
    }

    public String getAvailableEmployee() throws SQLException, ClassNotFoundException {
        return queryDAO.getAvailableEmployee();
    }

    public String getAvailableTime() throws SQLException, ClassNotFoundException {
        return queryDAO.getAvailableTime();
    }

    public boolean cancel(String id) throws SQLException, ClassNotFoundException {
        return customerOrderDAO.delete(id);
    }

}
