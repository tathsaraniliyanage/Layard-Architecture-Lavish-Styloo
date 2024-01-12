package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.SalaryBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.dao.custom.SalaryDAO;
import lk.ijse.lavishStyloo.dto.SalaryDTO;
import lk.ijse.lavishStyloo.dto.projection.SalaryProjection;
import lk.ijse.lavishStyloo.dto.tm.SalaryTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SalaryBOImpl implements SalaryBO {

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);

    public List<SalaryTm> findSalary() throws SQLException, ClassNotFoundException {
        return setTms(queryDAO.findSalary());
    }

    private List<SalaryTm> setTms(List<SalaryProjection> projectionList) throws SQLException {
        List<SalaryTm> list = new ArrayList<>();
        for (SalaryProjection projection : projectionList) {
            list.add(projection.toTm());
        }
        return list;
    }

    public List<SalaryTm> findSalaryByLike(String text) throws SQLException, ClassNotFoundException {
        return setTms(queryDAO.findSalaryByLike(text));
    }

    public boolean save(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        return salaryDAO.save(salaryDTO.toEntity());
    }

    public boolean isExsitThisMonth(String nic) throws SQLException, ClassNotFoundException {
        return queryDAO.isExistThisMonth(nic);
    }

    public String getCount() throws SQLException, ClassNotFoundException {
        return queryDAO.getCount();

    }

    public String getNext() throws SQLException, ClassNotFoundException {
        List<String> salary = findIdSalary();
        String oldId = null;
        for (String id : salary) {
            oldId = id;
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

    /**
     * load all ids from salary
     */
    public List<String> findIdSalary() throws SQLException, ClassNotFoundException {
        return salaryDAO.findIdSalary();
    }
}
