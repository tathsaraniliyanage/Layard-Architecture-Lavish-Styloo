package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.TreatmentDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface TreatmentBO extends SuperBO {

    List<String> loadCategory() throws SQLException, ClassNotFoundException;

    List<TreatmentDTO> findTreatmentByCategory(String value) throws SQLException, ClassNotFoundException;

    List<TreatmentDTO> loadAllTreatmentByLike(String text) throws SQLException, ClassNotFoundException;

    String countTreatment() throws SQLException, ClassNotFoundException;

    boolean save(TreatmentDTO treatment) throws SQLException, ClassNotFoundException;

    boolean update(TreatmentDTO treatment) throws SQLException, ClassNotFoundException;

    TreatmentDTO findTreatmentById(String treatmentId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    List<TreatmentDTO> findAll() throws SQLException, ClassNotFoundException;
}
