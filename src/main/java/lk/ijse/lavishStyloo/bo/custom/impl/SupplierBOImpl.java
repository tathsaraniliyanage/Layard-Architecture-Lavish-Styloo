package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.SupplierBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.SupplierDAO;
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public List<SupplierDTO> findAll() throws SQLException, ClassNotFoundException {
        return toDTO(supplierDAO.findAll());
    }

    private List<SupplierDTO> toDTO(List<Supplier> all) {
        ArrayList<SupplierDTO> list = new ArrayList<>();
        for (Supplier supplier : all) {
            list.add(supplier.toDTO());
        }
        return list;
    }

    @Override
    public String countSupper() throws SQLException, ClassNotFoundException {
        return supplierDAO.countSupper();
    }

    @Override
    public boolean delete(String supplier_id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplier_id);
    }

    public String nextId() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> ids = findAll();
        String oldId = null;
        for (SupplierDTO supplierDTO : ids) {
            oldId = supplierDTO.getSupplier_id();
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

    @Override
    public boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(supplierDTO.toEntity());
    }

    @Override
    public SupplierDTO findSupplierById(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.findSupplierById(id).toDTO();
    }

    @Override
    public boolean update(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(supplierDTO.toEntity());
    }
}
