package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.CrudDAO;
import lk.ijse.lavishStyloo.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface SupplierDAO extends CrudDAO<Supplier> {

    List<Supplier> findAll() throws SQLException, ClassNotFoundException;

    String countSupper() throws SQLException, ClassNotFoundException;

    boolean delete(String supplier_id) throws SQLException, ClassNotFoundException;

    String nextId() throws SQLException, ClassNotFoundException;

    boolean save(Supplier supplier) throws SQLException, ClassNotFoundException;

    Supplier findSupplierById(String id) throws SQLException, ClassNotFoundException;

    Supplier toEntity(ResultSet set) throws SQLException;

    boolean update(Supplier supplier) throws SQLException, ClassNotFoundException;

    List<Supplier> toDTOs(ResultSet set) throws SQLException;

}
