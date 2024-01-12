package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.TreatmentDAO;
import lk.ijse.lavishStyloo.dto.tm.TreatmentTm;
import lk.ijse.lavishStyloo.entity.Treatment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class TreatmentDAOImpl implements TreatmentDAO {

    public List<Treatment> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment");
        return toEntityList(resultSet);
    }

    public List<String> loadCategory() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT category From treatment");
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public List<Treatment> findTreatmentByCategory(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment WHERE category=?", value);
        return toEntityList(resultSet);
    }

    public List<Treatment> toEntityList(ResultSet resultSet) throws SQLException {
        List<lk.ijse.lavishStyloo.entity.Treatment> list = new ArrayList<>();
        while (resultSet.next()) {
            lk.ijse.lavishStyloo.entity.Treatment treatment = new lk.ijse.lavishStyloo.entity.Treatment();
            treatment.setTreat_id(resultSet.getString(1));
            treatment.setPrice(Double.parseDouble(resultSet.getString(2)));
            treatment.setCategory(resultSet.getString(3));
            treatment.setTreatment(resultSet.getString(4));
            treatment.setDescription(resultSet.getString(5));
            list.add(treatment);
        }
        return list;
    }

    public List<Treatment> loadAllTreatmentByLike(String text) throws SQLException, ClassNotFoundException {

        String arg = text + "%";

        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment WHERE treat_id LIKE ? OR price LIKE ? OR category LIKE ? OR treatment LIKE ? OR description LIKE ? "
                , arg, arg, arg, arg, arg
        );
        return toEntityList(resultSet);
    }

    public String countTreatment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(treat_id) from treatment");
        List<TreatmentTm> list = new ArrayList<>();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public boolean save(Treatment treatment) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO treatment VALUES (?,?,?,?,?)"
                , treatment.getTreat_id()
                , treatment.getPrice()
                , treatment.getCategory()
                , treatment.getTreatment()
                , treatment.getDescription()
        );
    }

    public boolean update(Treatment treatment) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE treatment SET price=?,category=?,treatment=?,description=? WHERE treat_id=?"
                , treatment.getPrice()
                , treatment.getCategory()
                , treatment.getTreatment()
                , treatment.getDescription()
                , treatment.getTreat_id()
        );
    }

    public Treatment findTreatmentById(String treatmentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment where treat_id=?", treatmentId);

        Treatment treatment = new Treatment();
        if (resultSet.next()) {
            treatment.setTreat_id(resultSet.getString(1));
            treatment.setPrice(resultSet.getDouble(2));
            treatment.setCategory(resultSet.getString(3));
            treatment.setTreatment(resultSet.getString(4));
            treatment.setDescription(resultSet.getString(5));

        }
        return treatment;
    }

    public String getNext() throws SQLException, ClassNotFoundException {
        List<Treatment> treatments = findAll();
        String oldId = null;
        for (Treatment tm : treatments) {
            oldId = tm.getTreat_id();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("T00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "T001";
        }
        lastIndex++;
        return "T00" + lastIndex;
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE from treatment where treat_id=?", id);
    }
}
