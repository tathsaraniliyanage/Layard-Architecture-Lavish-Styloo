package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface SupplierBO extends SuperBO {
    List<SupplierDTO> findAll() throws SQLException, ClassNotFoundException;

    String countSupper() throws SQLException, ClassNotFoundException;

    boolean delete(String supplier_id) throws SQLException, ClassNotFoundException;

    String nextId() throws SQLException, ClassNotFoundException;

    boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;

    SupplierDTO findSupplierById(String id) throws SQLException, ClassNotFoundException;

    boolean update(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;


}
