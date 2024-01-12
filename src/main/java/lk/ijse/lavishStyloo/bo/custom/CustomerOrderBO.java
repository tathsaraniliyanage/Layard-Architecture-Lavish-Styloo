package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.OrderDTO;
import lk.ijse.lavishStyloo.dto.OrderDetailsDTO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.tm.CustomerOrderDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.CustomerOrderTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */


public interface CustomerOrderBO extends SuperBO {

    List<CustomerOrderTm> findCustomerOrders() throws SQLException, ClassNotFoundException;

    List<CustomerOrderTm> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException;

    String CountCustomerOrder() throws SQLException, ClassNotFoundException;

    String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException;

    List<CustomerOrderDetailsTm> findCustomerOrderDetailsByOrderId(String customerOrderId) throws SQLException, ClassNotFoundException;

    List<CustomerOrderTm> findCustomerOrdersByLike(String text) throws SQLException, ClassNotFoundException;

    boolean placeOrder(ArrayList<OrderDetailsDTO> list, OrderDTO orderDTO, List<ProductDTO> productDTOS) throws SQLException, ClassNotFoundException;

    List<String> findDistinctYears() throws SQLException, ClassNotFoundException;

    String next() throws SQLException, ClassNotFoundException;

    List<String> findID() throws SQLException, ClassNotFoundException;

    String getAvailableEmployee() throws SQLException, ClassNotFoundException;

    String getAvailableTime() throws SQLException, ClassNotFoundException;

    boolean cancel(String id) throws SQLException, ClassNotFoundException;

}
