package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.ProductDAO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class ProductBOImpl implements ProductBO {

    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);

    public List<ProductDTO> findProduct() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product ");
        return toDTOs(productDAO.findAll());
    }

    private List<ProductDTO> toDTOs(List<Product> productList) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : productList) {
            list.add(product.toDTO());
        }
        return list;
    }

    public String MaxProductByPrice() throws SQLException, ClassNotFoundException {
        return productDAO.MaxProductByPrice();
    }

    public String MinProductByPrice() throws SQLException, ClassNotFoundException {
        return productDAO.MinProductByPrice();
    }

    public List<ProductDTO> betweenProductByPrice(String price) throws SQLException, ClassNotFoundException {
        return toDTOs(productDAO.betweenProductByPrice(price));
    }

    public List<ProductDTO> findProductByLike(String searchText) throws SQLException, ClassNotFoundException {
        return toDTOs(productDAO.findProductByLike(searchText));
    }

    public List<ProductDTO> findProductsByCode(String code) throws SQLException, ClassNotFoundException {
        return toDTOs(productDAO.findProductsByCode(code));
    }

    public ProductDTO findProductByCode(String code) throws SQLException, ClassNotFoundException {
        return productDAO.findProductByCode(code).toDTO();
    }

    public boolean updateItems(List<ProductDTO> productDTOS) throws SQLException, ClassNotFoundException {
        for (ProductDTO dto : productDTOS) {
            if (update(dto)) {
                return false;
            }
        }
        return true;
    }

    public boolean save(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return productDAO.save(dto.toEntity());
    }

    public boolean update(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return productDAO.update(dto.toEntity());
    }

    public String nextId() throws SQLException, ClassNotFoundException {
        List<ProductDTO> ids = findProduct();
        String oldId = null;
        for (ProductDTO dto : ids) {
            oldId = dto.getProduct_code();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("P00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "P001";
        }
        lastIndex++;
        return "P00" + lastIndex;
    }

    public boolean delete(String product_code) throws SQLException, ClassNotFoundException {
        return productDAO.delete(product_code);
    }

    public String CountByQTY() throws SQLException, ClassNotFoundException {
        return productDAO.CountByQTY();
    }
}
