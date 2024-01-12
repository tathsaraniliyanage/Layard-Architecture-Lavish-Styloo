package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.bo.custom.SupperOrderBO;
import lk.ijse.lavishStyloo.bo.custom.SupplierBO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.SupperOrderDTO;
import lk.ijse.lavishStyloo.dto.SupperOrderDetailsDTO;
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.dto.tm.OrderTm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplerOrderFromController implements Initializable {
    public TableView tblOrder;
    public TableColumn colCode;
    public TableColumn colProduct;
    public TableColumn colUnitPrice;
    public TableColumn colPrice;
    public TableColumn colQty;
    public Text txtQty;
    public Text txtDescription;
    public Text txtAddress;
    public JFXComboBox cmbSupperId;
    public Text txtOrderCount;
    public Text txtTotalBalance;
    public JFXTextField lblBalance;
    public Text txtNetTotal;
    public Text txtTotal;
    public JFXTextField lblQty;
    public JFXTextField LblCode;
    public ImageView imgItem;
    public Text txtPrice;
    public Text txtProduct;
    public JFXButton btnAdd;
    public Text txtDealer;
    public Text txtCompany;
    public Text txtContact;

    ArrayList<OrderTm> list = new ArrayList<>();
    ObservableList<OrderTm> orderTms = FXCollections.observableArrayList();
    SupperOrderBO supperOrderBO = (SupperOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER_ORDER);
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);


    public void cmbSupperOnaAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            SupplierDTO supplierDTO = supplierBO.findSupplierById(String.valueOf(cmbSupperId.getValue()));
            txtAddress.setText(supplierDTO.getLocation());
            txtDealer.setText(supplierDTO.getSupplier_name());
            txtCompany.setText(supplierDTO.getCompany());
            txtContact.setText(supplierDTO.getContact());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrder.getItems().addAll(orderTms);
        loadAllSupplerId();
    }

    private void loadAllSupplerId() {

        try {
            List<String> ids = new ArrayList<>();
            List<SupplierDTO> all = supplierBO.findAll();
            for (SupplierDTO dto : all) {
                ids.add(dto.getSupplier_id());
            }
            cmbSupperId.getItems().addAll(ids);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void lblCodeOnKeyReleased(KeyEvent keyEvent) {
        try {
            List<ProductDTO> productByCode = productBO.findProductsByCode(LblCode.getText());
            for (ProductDTO dto : productByCode) {
                System.out.println(dto.toString());
                txtQty.setText(dto.getQty());
                txtPrice.setText(dto.getUnit_price());
                Image image = new Image("/imgAsset/" + dto.getImg());
                imgItem.setImage(image);
            }
            setOrderCount();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setOrderCount() {
        txtOrderCount.setText(String.valueOf(list.size()));
    }

    public void lblQryOnKeReleasd(KeyEvent keyEvent) {

        if (!lblQty.getText().isEmpty()) {
            double t = Double.parseDouble(lblQty.getText()) * Double.parseDouble(txtPrice.getText());
            txtTotal.setText(String.valueOf(t));
            btnAdd.setDisable(Double.parseDouble(txtTotal.getText()) < 0);
        } else {
            txtTotal.setText("00.00");
            btnAdd.setDisable(true);
        }


    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean isNotExists = true;
        for (OrderTm orderTm : list) {
            if (orderTm.getItemCode().equals(LblCode.getText())) {
                orderTm.setQty(String.valueOf(Integer.parseInt(orderTm.getQty()) + Integer.parseInt(lblQty.getText())));
                orderTm.setPrice(String.valueOf(Double.parseDouble(orderTm.getPrice()) + Double.parseDouble(txtPrice.getText())));
                isNotExists = false;
            }
        }
        if (isNotExists) {
            OrderTm tm = new OrderTm();
            tm.setItemCode(LblCode.getText());
            tm.setProduct(txtProduct.getText());
            tm.setQty(lblQty.getText());
            tm.setPrice(txtPrice.getText());
            tm.setUnitPrice(txtTotal.getText());
            list.add(tm);
        }

        if (list.isEmpty()) {
            OrderTm tm = new OrderTm();
            tm.setItemCode(LblCode.getText());
            tm.setProduct(txtProduct.getText());
            tm.setQty(lblQty.getText());
            tm.setPrice(txtPrice.getText());
            tm.setUnitPrice(txtTotal.getText());
            list.add(tm);
        }

        tblOrder.getItems().clear();
        orderTms.clear();
        tblOrder.getItems().addAll(list);
        tblOrder.refresh();
        setOrderCount();
        setNetTotal();

    }

    private void setNetTotal() {
        double total = 0;
        for (OrderTm tm : list) {
            total += Double.parseDouble(tm.getPrice());
        }
        txtNetTotal.setText(String.valueOf(total));
    }

    public void balanceOnKeyReleased(KeyEvent keyEvent) {
        double total = Double.parseDouble(lblBalance.getText()) - Double.parseDouble(txtNetTotal.getText());
        txtTotalBalance.setText(String.valueOf(total));
    }

    public void btnTotalOnAction(ActionEvent actionEvent) {
        SupperOrderDTO supperOrderDTO = new SupperOrderDTO();
        supperOrderDTO.setTotal(Double.parseDouble(txtNetTotal.getText()));
        supperOrderDTO.setSupplier_id(String.valueOf(cmbSupperId.getValue()));
        supperOrderDTO.setDate(DateTimeUtil.dateNow());
        supperOrderDTO.setTime(DateTimeUtil.timeNow());
        supperOrderDTO.setSup_oid(getNext());
        try {
//          ArrayList<SupperOrderDetailsDTO> supperOrderDetailsDTOS, List<ProductDTO> productDTOS, SupperOrderDTO supperOrderDTO
            ArrayList<SupperOrderDetailsDTO> detailsDTOS = setSupperOrderDetailsDTO(list, supperOrderDTO);
            ArrayList<ProductDTO> productDTOS = setProductDTO(list, supperOrderDTO);
            boolean savedOrder = supperOrderBO.placeOrder(detailsDTOS, productDTOS, supperOrderDTO);

            if (savedOrder) {
                new Alert(Alert.AlertType.CONFIRMATION, "order saved").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "something Wong").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private ArrayList<SupperOrderDetailsDTO> setSupperOrderDetailsDTO(ArrayList<OrderTm> list, SupperOrderDTO supperOrderDTO) {
        ArrayList<SupperOrderDetailsDTO> supperOrderDetailsDTOS = new ArrayList<>();
        for (OrderTm tm : list) {
            supperOrderDetailsDTOS.add(new SupperOrderDetailsDTO(
                    supperOrderDTO.getSup_oid(),
                    tm.getItemCode(),
                    tm.getPrice(),
                    tm.getQty()
            ));
        }
        return supperOrderDetailsDTOS;
    }

    private ArrayList<ProductDTO> setProductDTO(ArrayList<OrderTm> list, SupperOrderDTO supperOrderDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (OrderTm tm : list) {
            ProductDTO productByCode = productBO.findProductByCode(tm.getItemCode());
            productByCode.setQty(String.valueOf(Integer.parseInt(productByCode.getQty()) - Integer.parseInt(tm.getQty())));
            productByCode.setUnit_price(tm.getUnitPrice());

        }
        return productDTOS;
    }

    private String getNext() {
        try {
            return supperOrderBO.nextId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
