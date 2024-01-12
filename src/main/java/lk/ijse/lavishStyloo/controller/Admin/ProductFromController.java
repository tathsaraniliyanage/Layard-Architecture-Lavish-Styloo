package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.tm.ProductTm;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ProductFromController implements Initializable {
    public static String product_code;
    private static ProductFromController controller;
    public Text txtPrice;
    public Text txtQty;
    public Text txtDescription;
    public JFXTextField txtSearchText;
    public TableView tblProduct;
    public TableColumn colCode;
    public TableColumn colProduct;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn colDescription;
    public Text txtAllTodayAttendance;
    public Slider sPriceRange;
    public Text txtMin;
    public Text txtMax;
    public JFXComboBox cmbCategory;
    public ImageView img;
    public Text txtDyPrice;
    public JFXButton btnClear;
    public Text btnText;
    public TableColumn colRemove;
    ObservableList<ProductTm> list = FXCollections.observableArrayList();
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);

    public ProductFromController() {

        controller = this;
    }

    public static ProductFromController getController() {
        return controller;
    }

    public void productFromSearchOnKeyReleased(KeyEvent keyEvent) {
        try {
            List<ProductDTO> product = productBO.findProductByLike(txtSearchText.getText());
            setTm(product);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void productFromTblOnClick(MouseEvent mouseEvent) {
        ProductTm productTm = (ProductTm) tblProduct.getSelectionModel().getSelectedItem();
        try {
            txtDescription.setText(productTm.getDescription());
            txtPrice.setText(productTm.getUnit_price());
            txtQty.setText(productTm.getQty());
            Image image = new Image("/imgAsset/" + productTm.getImg());
            img.setImage(image);
        } catch (Exception e) {
        }

        product_code = productTm.getProduct_code();
        btnText.setText("UPDATE");
        btnClear.setVisible(true);
    }

    public void CategoryOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("product_code"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
        tblProduct.setItems(list);
        loadAllProduct();
        setPriceRange();
    }

    private void setPriceRange() {
        try {
            String min = productBO.MinProductByPrice();
            String max = productBO.MaxProductByPrice();
            txtMax.setText(max);
            txtMin.setText(min);
            sPriceRange.setMax(Double.parseDouble(max) + 2000);
            sPriceRange.setMin(Double.parseDouble(min));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void loadAllProduct() {
        try {
            List<ProductDTO> product = productBO.findProduct();
            setTm(product);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTm(List<ProductDTO> product) {
        list.clear();
        tblProduct.getItems().clear();
        for (ProductDTO dto : product) {
            ProductTm tm = new ProductTm();
            tm.setProduct_code(dto.getProduct_code());
            tm.setProduct(dto.getProduct());
            tm.setDescription(dto.getDescription());
            tm.setUnit_price(dto.getUnit_price());
            tm.setQty(dto.getQty());
            tm.setImg(dto.getImg());
            list.addAll(tm);

        }

        while (!product.isEmpty()) {
            txtDescription.setText(product.get(0).getDescription());
            txtPrice.setText(product.get(0).getUnit_price());
            txtQty.setText(product.get(0).getQty());

            Image image = new Image("/imgAsset/" + product.get(0).getImg());
            img.setImage(image);
            break;
        }
        setCount(product.size());
        tblProduct.refresh();
    }

    private void setCount(int size) {
        txtAllTodayAttendance.setText(size + "+");
    }


    public void priceOnDrag(MouseEvent mouseEvent) {
        double number = sPriceRange.getValue();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String format = numberFormat.format(number);
        txtDyPrice.setText(format);
        try {
            List<ProductDTO> product = productBO.betweenProductByPrice(format);
            setTm(product);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        try {
            String min = productBO.MinProductByPrice();
            txtDyPrice.setText("00.00");
            sPriceRange.setValue(Double.parseDouble(min));
            loadAllProduct();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void CustomerFromClearOnAction(ActionEvent actionEvent) {
        tblProduct.getSelectionModel().clearSelection();
        btnClear.setVisible(false);
        btnText.setText("NEW");
    }

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        if (btnText.getText().equals("NEW")) {
            NavigationUtility.popupNavigation("Admin/ProductAddFrom.fxml");
        } else {
            NavigationUtility.popupNavigation("Admin/ProductUpdateFrom.fxml");
        }
    }
}
