package lk.ijse.lavishStyloo.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 20/12/2023
 */
public interface CrudDAO<T> extends SuperDAO {
    boolean save(T entity) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(T entity) throws SQLException, ClassNotFoundException;

    List<T> findAll() throws SQLException, ClassNotFoundException;
}
