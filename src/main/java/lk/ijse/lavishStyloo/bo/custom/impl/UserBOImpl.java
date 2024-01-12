package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.UserBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.UserDAO;
import lk.ijse.lavishStyloo.dto.UserDTO;

import java.sql.SQLException;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean save(UserDTO user) throws SQLException, ClassNotFoundException {
        return userDAO.save(user.toEntity());
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return userDAO.delete(nic);
    }

    @Override
    public String checkUserNamePassword(String user, String password) throws SQLException, ClassNotFoundException {
        return userDAO.checkUserNamePassword(user, password);
    }

    @Override
    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.update(userDTO.toEntity());
    }

    @Override
    public UserDTO getUser(String nic) throws SQLException, ClassNotFoundException {
        return userDAO.getUser(nic);
    }

    @Override
    public boolean isExUser(String nic) throws SQLException, ClassNotFoundException {
        return userDAO.isExUser(nic);
    }
}
