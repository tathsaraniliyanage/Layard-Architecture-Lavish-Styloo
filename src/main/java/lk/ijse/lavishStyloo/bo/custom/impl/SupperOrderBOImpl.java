package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.SupperOrderBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.ProductDAO;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.dao.custom.SupperOrderDAO;
import lk.ijse.lavishStyloo.dao.custom.SupperOrderDetailsDAO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.SupperOrderDTO;
import lk.ijse.lavishStyloo.dto.SupperOrderDetailsDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.dto.projection.SupperOrderProjection;
import lk.ijse.lavishStyloo.dto.tm.SupperOrderTm;
import lk.ijse.lavishStyloo.entity.Product;
import lk.ijse.lavishStyloo.entity.SupperOrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SupperOrderBOImpl implements SupperOrderBO {

    SupperOrderDetailsDAO supperOrderDetailsDAO = (SupperOrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_ORDER_DETAIL);
    SupperOrderDAO supperOrderDAO = (SupperOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_ORDER);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public boolean placeOrder(ArrayList<SupperOrderDetailsDTO> supperOrderDetailsDTOS, List<ProductDTO> productDTOS, SupperOrderDTO supperOrderDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = supperOrderDAO.save(supperOrderDTO.toEntity());
            if (isSaved) {
                boolean isSavedDetails = supperOrderDetailsDAO.save(toSupperOrderDetailsEntity(supperOrderDetailsDTOS));
                if (isSavedDetails) {
                    boolean isUpdated = productDAO.update(toProductEntity(productDTOS));
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

    public List<SupperOrderTm> findAll() throws SQLException, ClassNotFoundException {
        return toTms(queryDAO.findAll());
    }

    public List<SupperOrderTm> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException {
        return toTms(queryDAO.findCustomerOrdersByDate(date));
    }

    public String CountCustomerOrder() throws SQLException, ClassNotFoundException {
        return supperOrderDAO.CountCustomerOrder();
    }

    public String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException {
        return supperOrderDAO.CountCustomerOrderByDate();
    }

    public List<ReportCm> getSupplierOrder() throws SQLException, ClassNotFoundException {
        return supperOrderDAO.getSupplierOrder();
    }

    public String nextId() throws SQLException, ClassNotFoundException {
        List<SupperOrderTm> all = findAll();
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
        return "S00" + lastIndex;
    }

    public List<ReportCm> getYearlySupplierOrder(String year) throws SQLException, ClassNotFoundException {
        return supperOrderDAO.getYearlySupplierOrder(year);
    }

    public List<ReportCm> getMonthlySupplierOrder(String year, String moth) throws SQLException, ClassNotFoundException {
        return supperOrderDAO.getMonthlySupplierOrder(year, moth);
    }

    public List<ReportCm> getDaySupplierOrder(String year, String month) throws SQLException, ClassNotFoundException {
        return supperOrderDAO.getDaySupplierOrder(year, month);
    }

    private List<SupperOrderTm> toTms(List<SupperOrderProjection> projectionList) throws SQLException {
        List<SupperOrderTm> list = new ArrayList<>();

        for (SupperOrderProjection projection : projectionList) {
            list.add(projection.toTM());
        }
        return list;
    }

    private ArrayList<Product> toProductEntity(List<ProductDTO> productDTOS) {
        ArrayList<Product> products = new ArrayList<>();
        for (ProductDTO product : productDTOS) {
            products.add(product.toEntity());
        }
        return products;
    }

    private List<SupperOrderDetails> toSupperOrderDetailsEntity(ArrayList<SupperOrderDetailsDTO> supperOrderDetailsDTOS) {
        ArrayList<SupperOrderDetails> list = new ArrayList<>();
        for (SupperOrderDetailsDTO supperOrderDetailsDTO : supperOrderDetailsDTOS) {
            list.add(supperOrderDetailsDTO.toEntity());
        }
        return list;
    }
}
