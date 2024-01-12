package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.ProductDAO;
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

public class ProductDAOImpl implements ProductDAO {

    public boolean save(Product product) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO  product VALUES (?,?,?,?,?,?)"
                , product.getProduct_code()
                , product.getProduct()
                , product.getDescription()
                , product.getUnit_price()
                , product.getQty()
                , product.getImg()
        );
    }

    public List<Product> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product ");
        return toDTOs(resultSet);
    }

    public List<Product> toDTOs(ResultSet resultSet) throws SQLException {
        List<Product> list = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product();
            product.setProduct_code(resultSet.getString(1));
            product.setProduct(resultSet.getString(2));
            product.setDescription(resultSet.getString(3));
            product.setUnit_price(resultSet.getString(4));
            product.setQty(resultSet.getString(5));
            product.setImg(resultSet.getString(6));
            list.add(product);
        }
        return list;
    }

    public Product toDTO(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        if (resultSet.next()) {
            product.setProduct_code(resultSet.getString(1));
            product.setProduct(resultSet.getString(2));
            product.setDescription(resultSet.getString(3));
            product.setUnit_price(resultSet.getString(4));
            product.setQty(resultSet.getString(5));
            product.setImg(resultSet.getString(6));
        }
        return product;
    }

    public String MaxProductByPrice() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT MAX(unit_price) from product");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public String MinProductByPrice() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT MIN(unit_price) from product");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public List<Product> betweenProductByPrice(String price) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where unit_price between ( SELECT MIN(unit_price) from product) and ? ", price);
        return toDTOs(resultSet);
    }

    public List<Product> findProductByLike(String searchText) throws SQLException, ClassNotFoundException {
        String args = searchText + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where product LIKE ? OR  unit_price LIKE  ? OR  product_code LIKE  ? OR qty LIKE ?", args, args, args, args);
        return toDTOs(resultSet);
    }

    public List<Product> findProductsByCode(String code) throws SQLException, ClassNotFoundException {
        String args = code + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where  product_code LIKE  ?", args);
        return toDTOs(resultSet);
    }

    public Product findProductByCode(String code) throws SQLException, ClassNotFoundException {
        String args = code + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where  product_code LIKE  ?", args);
        return toDTO(resultSet);
    }

    public boolean update(Product product) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE product SET product=?,description=?,unit_price=?,img=?,qty=? WHERE product_code=?"
                , product.getProduct()
                , product.getDescription()
                , product.getUnit_price()
                , product.getImg()
                , product.getQty()
                , product.getProduct_code()
        );
    }

    public boolean update(List<Product> list) throws SQLException, ClassNotFoundException {
        for (Product product : list) {
            boolean isSaved = update(product);
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    public String nextId() throws SQLException, ClassNotFoundException {
       /* List<ProductDTO> ids = findAll();
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
        return "P00" + lastIndex;*/
        return null;
    }

    public boolean delete(String product_code) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE from product where product_code=?", product_code);
    }

    public String CountByQTY() throws SQLException, ClassNotFoundException {
        String sql = "SELECT  COUNT(*) FROM product WHERE qty='0'";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

}
