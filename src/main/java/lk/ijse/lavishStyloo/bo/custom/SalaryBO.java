package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.SalaryDTO;
import lk.ijse.lavishStyloo.dto.tm.SalaryTm;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface SalaryBO extends SuperBO {

    List<SalaryTm> findSalary() throws SQLException, ClassNotFoundException;

    List<SalaryTm> findSalaryByLike(String text) throws SQLException, ClassNotFoundException;

    boolean save(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException;

    boolean isExsitThisMonth(String nic) throws SQLException, ClassNotFoundException;

    String getCount() throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    /**
     * load all ids from salary
     */
    List<String> findIdSalary() throws SQLException, ClassNotFoundException;
}
