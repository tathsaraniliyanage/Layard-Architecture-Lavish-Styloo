package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.CrudDAO;
import lk.ijse.lavishStyloo.entity.Treatment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface TreatmentDAO extends CrudDAO<Treatment> {
    List<String> loadCategory() throws SQLException, ClassNotFoundException;

    List<Treatment> findTreatmentByCategory(String value) throws SQLException, ClassNotFoundException;

    List<Treatment> toEntityList(ResultSet resultSet) throws SQLException;

    List<Treatment> loadAllTreatmentByLike(String text) throws SQLException, ClassNotFoundException;

    String countTreatment() throws SQLException, ClassNotFoundException;

    boolean save(Treatment treatment) throws SQLException, ClassNotFoundException;

    boolean update(Treatment treatment) throws SQLException, ClassNotFoundException;

    Treatment findTreatmentById(String treatmentId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    List<Treatment> findAll() throws SQLException, ClassNotFoundException;
}
