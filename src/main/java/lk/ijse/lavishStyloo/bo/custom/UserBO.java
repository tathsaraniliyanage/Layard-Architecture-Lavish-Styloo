package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.UserDTO;

import java.sql.SQLException;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface UserBO extends SuperBO {

    boolean save(UserDTO user) throws SQLException, ClassNotFoundException;

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    String checkUserNamePassword(String user, String password) throws SQLException, ClassNotFoundException;

    boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException;

    UserDTO getUser(String nic) throws SQLException, ClassNotFoundException;

    boolean isExUser(String nic) throws SQLException, ClassNotFoundException;
}
