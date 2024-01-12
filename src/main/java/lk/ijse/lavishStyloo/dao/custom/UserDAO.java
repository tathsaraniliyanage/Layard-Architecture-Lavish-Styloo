package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.dto.UserDTO;
import lk.ijse.lavishStyloo.entity.User;

import java.sql.SQLException;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface UserDAO extends SuperDAO {

    boolean save(User user) throws SQLException, ClassNotFoundException;

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    String checkUserNamePassword(String user, String password) throws SQLException, ClassNotFoundException;

    boolean update(User userDTO) throws SQLException, ClassNotFoundException;

    UserDTO getUser(String nic) throws SQLException, ClassNotFoundException;

    boolean isExUser(String nic) throws SQLException, ClassNotFoundException;
}
