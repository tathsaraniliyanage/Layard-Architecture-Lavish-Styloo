package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.SupplierDAO;
import lk.ijse.lavishStyloo.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SupplierDAOImpl implements SupplierDAO {

    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO supplier VALUES (?,?,?,?,?,?)",
                supplier.getSupplier_id(),
                supplier.getSupplier_name(),
                supplier.getCompany(),
                supplier.getEmail(),
                supplier.getContact(),
                supplier.getLocation()

        );
    }

    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE supplier SET supplier_name=?,company=?,email=?,contact=?,location=? WHERE supplier_id=?"
                , supplier.getSupplier_name()
                , supplier.getCompany()
                , supplier.getEmail()
                , supplier.getContact()
                , supplier.getLocation()
                , supplier.getSupplier_id()
        );
    }

    public boolean delete(String supplier_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  FROM supplier WHERE  supplier_id=?", supplier_id);
    }

    public List<Supplier> findAll() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM supplier");
        return toDTOs(set);
    }

    public Supplier findSupplierById(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM supplier WHERE supplier_id =?", id);
        return toEntity(set);
    }

    public String countSupper() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*)from supplier");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public Supplier toEntity(ResultSet set) throws SQLException {
        Supplier supplier = new Supplier();
        if (set.next()) {
            supplier.setSupplier_id(set.getString(1));
            supplier.setSupplier_name(set.getString(2));
            supplier.setCompany(set.getString(3));
            supplier.setEmail(set.getString(4));
            supplier.setContact(set.getString(5));
            supplier.setLocation(set.getString(6));
        }
        return supplier;
    }

    public List<Supplier> toDTOs(ResultSet set) throws SQLException {
        List<Supplier> list = new ArrayList<>();
        while (set.next()) {
            Supplier supplier = new Supplier();
            supplier.setSupplier_id(set.getString(1));
            supplier.setSupplier_name(set.getString(2));
            supplier.setCompany(set.getString(3));
            supplier.setEmail(set.getString(4));
            supplier.setContact(set.getString(5));
            supplier.setLocation(set.getString(6));
            list.add(supplier);
        }
        return list;
    }

    public String nextId() throws SQLException, ClassNotFoundException {
        List<Supplier> ids = findAll();
        String oldId = null;
        for (Supplier supplier : ids) {
            oldId = supplier.getSupplier_id();
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
}
