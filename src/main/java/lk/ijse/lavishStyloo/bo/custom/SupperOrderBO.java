package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.SupperOrderDTO;
import lk.ijse.lavishStyloo.dto.SupperOrderDetailsDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.dto.tm.SupperOrderTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public interface SupperOrderBO extends SuperBO {
    boolean placeOrder(ArrayList<SupperOrderDetailsDTO> supperOrderDetailsDTOS, List<ProductDTO> productDTOS, SupperOrderDTO supperOrderDTO) throws SQLException, ClassNotFoundException;

    List<SupperOrderTm> findAll() throws SQLException, ClassNotFoundException;

    List<SupperOrderTm> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException;

    String CountCustomerOrder() throws SQLException, ClassNotFoundException;

    String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException;

    List<ReportCm> getSupplierOrder() throws SQLException, ClassNotFoundException;

    String nextId() throws SQLException, ClassNotFoundException;

    List<ReportCm> getYearlySupplierOrder(String year) throws SQLException, ClassNotFoundException;

    List<ReportCm> getMonthlySupplierOrder(String year, String moth) throws SQLException, ClassNotFoundException;

    List<ReportCm> getDaySupplierOrder(String year, String month) throws SQLException, ClassNotFoundException;
}
