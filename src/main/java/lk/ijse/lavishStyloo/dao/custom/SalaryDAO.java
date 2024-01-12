package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.entity.Salary;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface SalaryDAO extends SuperDAO {

     /* List<SalaryProjection> findSalary() throws SQLException, ClassNotFoundException;

    List<SalaryProjection> toEntityList(ResultSet resultSet) throws SQLException;

    List<SalaryProjection> findSalaryByLike(String text) throws SQLException, ClassNotFoundException;*/

    boolean save(Salary salary) throws SQLException, ClassNotFoundException;

    List<String> findIdSalary() throws SQLException, ClassNotFoundException;
}
