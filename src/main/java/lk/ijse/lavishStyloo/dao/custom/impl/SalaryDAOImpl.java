package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.SalaryDAO;
import lk.ijse.lavishStyloo.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SalaryDAOImpl implements SalaryDAO {

    public boolean save(Salary salary) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO salary VALUES (?,?,?,?,?,?)";
        return CrudUtil.crudUtil(sql
                , salary.getS_id()
                , salary.getDate()
                , salary.getTime()
                , salary.getSalary()
                , salary.getBonus()
                , salary.getNic()
        );
    }

    public List<String> findIdSalary() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT s_id from salary ORDER BY LENGTH(s_id),s_id");
        List<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

}
