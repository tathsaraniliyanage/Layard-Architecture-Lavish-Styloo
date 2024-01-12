package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.CrudDAO;
import lk.ijse.lavishStyloo.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface ProductDAO extends CrudDAO<Product> {

    List<Product> toDTOs(ResultSet resultSet) throws SQLException;

    Product toDTO(ResultSet resultSet) throws SQLException;

    String MaxProductByPrice() throws SQLException, ClassNotFoundException;

    String MinProductByPrice() throws SQLException, ClassNotFoundException;

    List<Product> betweenProductByPrice(String price) throws SQLException, ClassNotFoundException;

    List<Product> findProductByLike(String searchText) throws SQLException, ClassNotFoundException;

    List<Product> findProductsByCode(String code) throws SQLException, ClassNotFoundException;

    Product findProductByCode(String code) throws SQLException, ClassNotFoundException;

    boolean update(List<Product> list) throws SQLException, ClassNotFoundException;

    boolean update(Product product) throws SQLException, ClassNotFoundException;

    String nextId() throws SQLException, ClassNotFoundException;

    boolean delete(String product_code) throws SQLException, ClassNotFoundException;

    String CountByQTY() throws SQLException, ClassNotFoundException;
}
