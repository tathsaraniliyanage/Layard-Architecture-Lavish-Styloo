package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.dto.OrderDTO;
import lk.ijse.lavishStyloo.dto.OrderDetailsDTO;
import lk.ijse.lavishStyloo.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface OrderBO {

    boolean placeOrder(ArrayList<OrderDetailsDTO> list, OrderDTO orderDTO, List<ProductDTO> productDTOS) throws SQLException, ClassNotFoundException;

    List<String> findDistinctYears() throws SQLException, ClassNotFoundException;

    String next() throws SQLException, ClassNotFoundException;

    List<String> findID() throws SQLException, ClassNotFoundException;

    String getAvailableEmployee() throws SQLException, ClassNotFoundException;

    String getAvailableTime() throws SQLException, ClassNotFoundException;

    boolean cancel(String id) throws SQLException, ClassNotFoundException;
}
