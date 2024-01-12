package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.UserDAO;
import lk.ijse.lavishStyloo.dto.UserDTO;
import lk.ijse.lavishStyloo.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class UserDAOImpl implements UserDAO {

    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO user VALUES (?,?,?)",
                user.getUserName(),
                user.getPassword(),
                user.getNic()
        );

    }

    public boolean update(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE user SET userName=? ,password=? WHERE nic=?",
                user.getUserName(),
                user.getPassword(),
                user.getNic()
        );
    }

    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM user WHERE nic=?", nic);
    }

    public UserDTO getUser(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM user WHERE nic=?", nic);
        UserDTO userDTO = new UserDTO();
        if (resultSet.next()) {
            userDTO.setUserName(resultSet.getString(1));
            userDTO.setPassword(resultSet.getString(2));
            userDTO.setNic(resultSet.getString(3));
        }
        return userDTO;
    }

    public String checkUserNamePassword(String user, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT e.role FROM user u inner join employee e on u.nic=e.nic where u.password=? && u.userName=?", user, password);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "404";
    }

    public boolean isExUser(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM user WHERE nic=?", nic);
        return resultSet.next();
    }

}
