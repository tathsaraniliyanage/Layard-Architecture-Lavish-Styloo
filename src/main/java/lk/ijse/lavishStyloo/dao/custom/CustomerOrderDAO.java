package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.entity.Order;
import lk.ijse.lavishStyloo.entity.OrderDetails;
import lk.ijse.lavishStyloo.entity.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface CustomerOrderDAO extends SuperDAO {

    boolean placeOrder(List<OrderDetails> list, Order order, List<Product> products) throws SQLException, ClassNotFoundException;

    boolean save(Order order) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    String next() throws SQLException, ClassNotFoundException;

    List<String> findID() throws SQLException, ClassNotFoundException;

    String CountCustomerOrder() throws SQLException, ClassNotFoundException;

    String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException;

    List<ReportCm> getCustomerOrder() throws SQLException, ClassNotFoundException;

    List<ReportCm> getYearlyCustomerOrder(String year) throws SQLException, ClassNotFoundException;

    List<ReportCm> getMonthlyCustomerOrder(String year, String moth) throws SQLException, ClassNotFoundException;

    List<ReportCm> getDayCustomerOrder(String year, String month) throws SQLException, ClassNotFoundException;


}
