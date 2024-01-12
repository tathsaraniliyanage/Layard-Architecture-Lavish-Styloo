package lk.ijse.lavishStyloo.bo.custom;

import lk.ijse.lavishStyloo.bo.SuperBO;
import lk.ijse.lavishStyloo.dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public interface ProductBO extends SuperBO {
    List<ProductDTO> findProduct() throws SQLException, ClassNotFoundException;

    String MaxProductByPrice() throws SQLException, ClassNotFoundException;

    String MinProductByPrice() throws SQLException, ClassNotFoundException;

    List<ProductDTO> betweenProductByPrice(String price) throws SQLException, ClassNotFoundException;

    List<ProductDTO> findProductByLike(String searchText) throws SQLException, ClassNotFoundException;

    List<ProductDTO> findProductsByCode(String code) throws SQLException, ClassNotFoundException;

    ProductDTO findProductByCode(String code) throws SQLException, ClassNotFoundException;

    boolean updateItems(List<ProductDTO> productDTOS) throws SQLException, ClassNotFoundException;

    boolean save(ProductDTO dto) throws SQLException, ClassNotFoundException;

    boolean update(ProductDTO dto) throws SQLException, ClassNotFoundException;

    String nextId() throws SQLException, ClassNotFoundException;

    boolean delete(String product_code) throws SQLException, ClassNotFoundException;

    String CountByQTY() throws SQLException, ClassNotFoundException;
}
