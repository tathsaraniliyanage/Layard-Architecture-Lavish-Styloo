package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.TreatmentBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.TreatmentDAO;
import lk.ijse.lavishStyloo.dto.TreatmentDTO;
import lk.ijse.lavishStyloo.entity.Treatment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class TreatmentBOImpl implements TreatmentBO {


    TreatmentDAO treatmentDAO = (TreatmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TREATMENT);

    @Override
    public List<String> loadCategory() throws SQLException, ClassNotFoundException {
        return treatmentDAO.loadCategory();
    }

    @Override
    public List<TreatmentDTO> findTreatmentByCategory(String value) throws SQLException, ClassNotFoundException {
        return toDTO(treatmentDAO.findTreatmentByCategory(value));
    }

    @Override
    public List<TreatmentDTO> loadAllTreatmentByLike(String text) throws SQLException, ClassNotFoundException {
        return toDTO(treatmentDAO.loadAllTreatmentByLike(text));
    }

    @Override
    public String countTreatment() throws SQLException, ClassNotFoundException {
        return treatmentDAO.countTreatment();
    }

    @Override
    public boolean save(TreatmentDTO treatment) throws SQLException, ClassNotFoundException {
        return treatmentDAO.save(treatment.toEntity());
    }

    @Override
    public boolean update(TreatmentDTO treatment) throws SQLException, ClassNotFoundException {
        return treatmentDAO.update(treatment.toEntity());
    }

    @Override
    public TreatmentDTO findTreatmentById(String treatmentId) throws SQLException, ClassNotFoundException {
        return treatmentDAO.findTreatmentById(treatmentId).toDTO();
    }

    public String getNext() throws SQLException, ClassNotFoundException {
        List<TreatmentDTO> list = findAll();
        String oldId = null;
        for (TreatmentDTO dto : list) {
            oldId = dto.getTreat_id();
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

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return delete(id);
    }

    @Override
    public List<TreatmentDTO> findAll() throws SQLException, ClassNotFoundException {
        return toDTO(treatmentDAO.findAll());
    }

    private List<TreatmentDTO> toDTO(List<Treatment> treatmentList) {
        ArrayList<TreatmentDTO> list = new ArrayList<>();
        for (Treatment treatment : treatmentList) {
            list.add(treatment.toDTO());
        }
        return list;
    }


}
