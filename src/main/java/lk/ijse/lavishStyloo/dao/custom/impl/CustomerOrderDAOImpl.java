package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.CustomerOrderDAO;
import lk.ijse.lavishStyloo.dao.custom.OrderDetailsDAO;
import lk.ijse.lavishStyloo.dao.custom.ProductDAO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.entity.Order;
import lk.ijse.lavishStyloo.entity.OrderDetails;
import lk.ijse.lavishStyloo.entity.Product;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class CustomerOrderDAOImpl implements CustomerOrderDAO {

    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    public String CountCustomerOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(cust_oid) from customer_order");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(cust_oid) from customer_order where date=CURDATE()");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public List<ReportCm> getCustomerOrder() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT MONTH(date) AS month, SUM(total)as num FROM customer_order GROUP BY month");
        List<ReportCm> list = new ArrayList<>();
        while (result.next()) {
            ReportCm reportCm = new ReportCm();
            reportCm.setTitle(result.getString(1));
            reportCm.setValue(result.getDouble(2));
            list.add(reportCm);
        }
        return list;
    }

    public List<ReportCm> getYearlyCustomerOrder(String year) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        List<ReportCm> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM customer_order WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)", year, (i < 10 ? ("0" + i) : i));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            } else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public List<ReportCm> getMonthlyCustomerOrder(String year, String moth) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int month = 0;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(moth)) {
                month = i;
                break;
            }
        }
        int days = DateTimeUtil.getDays(Integer.parseInt(year), (month + 1));
        List<ReportCm> list = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM customer_order WHERE YEAR(date)=? and MONTH(date)= ? and DAY(date)=? GROUP BY DAY(date)", year, (month + 1), (i < 10 ? ("0" + i) : i));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(String.valueOf((i - 1)));
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            } else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(String.valueOf((i)));
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public List<ReportCm> getDayCustomerOrder(String year, String month) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int thisMonth = 0;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(month)) {
                thisMonth = i;
                break;
            }
        }
        List<ReportCm> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM customer_order WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)", year, (thisMonth + 1));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            } else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i - 1)]);
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public boolean placeOrder(List<OrderDetails> list, Order order, List<Product> products) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = save(order);
            if (isSaved) {
                boolean isSavedDetails = orderDetailsDAO.save(list);
                if (isSavedDetails) {
                    boolean isUpdated = productDAO.update(products);
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

    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO customer_order VALUES (?,?,?,?,?)",
                order.getCust_oid(),
                order.getCust_id(),
                order.getDate(),
                order.getTime(),
                order.getTotal()
        );
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  FROM  booking WHERE  booking_id=?", id);
    }

    public List<String> findID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT cust_oid from customer_order ORDER BY LENGTH(cust_oid),cust_oid");
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
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

}
