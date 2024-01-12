package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.SupperOrderDAO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.entity.SupperOrder;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SupperOrderDAOImpl implements SupperOrderDAO {

    public boolean save(SupperOrder supperOrder) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO supplier_order VALUES (?,?,?,?,?)";
        return CrudUtil.crudUtil(sql,
                supperOrder.getSup_oid(),
                supperOrder.getSupplier_id(),
                supperOrder.getDate(),
                supperOrder.getTime(),
                supperOrder.getTotal()
        );
    }

    /*public  boolean placeOrder(ArrayList<SupperOrderDetails> supperOrderDetails, ArrayList<Product> products, SupperOrderDTO supperOrderDTO) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = save(supperOrderDTO);
            if (isSaved) {
                boolean isSavedDetails = supperOrderDetailsDAO.save(supperOrderDetails);
                if (isSavedDetails) {
                    boolean isUpdated = ProductDAOImpl.update(products);
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
    }*/

    public String CountCustomerOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM supplier_order");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM supplier_order WHERE date = CURDATE()");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public List<ReportCm> getYearlySupplierOrder(String year) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        List<ReportCm> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM supplier_order WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)", year, (i < 10 ? ("0" + i) : i));
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

    public List<ReportCm> getMonthlySupplierOrder(String year, String moth) throws SQLException, ClassNotFoundException {
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
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM supplier_order WHERE YEAR(date)=? and MONTH(date)= ? and DAY(date)=? GROUP BY DAY(date)", year, (month + 1), (i < 10 ? ("0" + i) : i));
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

    public List<ReportCm> getDaySupplierOrder(String year, String month) throws SQLException, ClassNotFoundException {
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
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM supplier_order WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)", year, (thisMonth + 1));
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

   /* public  List<SupperOrderTm> toTms(ResultSet resultSet) throws SQLException {
        List<SupperOrderTm> list = new ArrayList<>();

        while (resultSet.next()) {
            SupperOrderTm tm = new SupperOrderTm();
            tm.setSupperOrderId(resultSet.getString(1));
            tm.setSupperId(resultSet.getString(2));
            tm.setDear(resultSet.getString(3));
            tm.setDate(resultSet.getString(4));
            tm.setTime(resultSet.getString(5));
            tm.setTotal(resultSet.getString(6));
            tm.setCount(resultSet.getString(7));
            list.add(tm);

        }
        return list;
    }


    public static List<SupperOrderTm> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT s.sup_oid,s2.supplier_id,s2.supplier_name,s.date,s.time,s.total,COUNT(sod.sup_oid) FROM supplier_order s INNER JOIN supplier s2 on s.supplier_id = s2.supplier_id inner join supplier_order_details sod on s.sup_oid = sod.sup_oid GROUP BY s.sup_oid";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        return toTms(resultSet);
    }

    public  List<SupperOrderTm> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT s.sup_oid,s2.supplier_id,s2.supplier_name,s.date,s.time,s.total,COUNT(sod.sup_oid) FROM supplier_order s INNER JOIN supplier s2 on s.supplier_id = s2.supplier_id inner join supplier_order_details sod on s.sup_oid = sod.sup_oid where s.date=? GROUP BY s.sup_oid", date);
        return toTms(resultSet);

    }*/

    public List<ReportCm> getSupplierOrder() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT MONTH(date) AS month, SUM(total)as num FROM supplier_order GROUP BY month");
        List<ReportCm> list = new ArrayList<>();
        while (result.next()) {
            ReportCm reportCm = new ReportCm();
            reportCm.setTitle(result.getString(1));
            reportCm.setValue(result.getDouble(2));
            list.add(reportCm);
        }
        return list;
    }

    public String nextId() throws SQLException, ClassNotFoundException {
      /*  List<SupperOrderTm> all = findAll();
        String oldId = null;
        for (SupperOrderTm tm : all) {
            oldId = tm.getSupperOrderId();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("S00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "S001";
        }
        lastIndex++;
        return "S00" + lastIndex;*/
        return null;
    }
}
