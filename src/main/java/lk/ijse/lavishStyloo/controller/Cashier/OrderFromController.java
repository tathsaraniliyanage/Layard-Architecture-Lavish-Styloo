package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerOrderBO;
import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.OrderDTO;
import lk.ijse.lavishStyloo.dto.OrderDetailsDTO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.tm.OrderTm;
import lk.ijse.lavishStyloo.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFromController implements Initializable {
    public TableView tblOrder;
    public TableColumn colCode;
    public TableColumn colProduct;
    public TableColumn colUnitPrice;
    public TableColumn colPrice;
    public TableColumn colQty;
    public Text txtQty;
    public Text txtDescription;
    public Text txtCutId;
    public Text txtAddress;
    public JFXComboBox cmbCustomerId;
    public JFXTextField lblCusNo;
    public JFXTextField lblCustomerName;
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
    public JFXButton btnPayNow;
    ArrayList<OrderTm> list = new ArrayList<>();
    ObservableList<OrderTm> orderTms = FXCollections.observableArrayList();

    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    CustomerOrderBO customerOrderBO = (CustomerOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_ORDER);
    CustomerBO customerBo = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);


    public void customerFromAddOnAction(ActionEvent actionEvent) {
        NavigationUtility.popupNavigation("Cashier/CustomerAddFrom.fxml");

    }

    public void cmbCustomerOnaAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            CustomerDTO customerDTO = customerBo.findCustomerById(String.valueOf(cmbCustomerId.getValue()));
            if (customerDTO != null) {
                txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
                txtCutId.setText(customerDTO.getCustomer_id());
                lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
                lblCusNo.setText(customerDTO.getContact());
            }
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        }
    }

    public void contactOnKeyReleas(KeyEvent keyEvent) {
        try {
            List<CustomerDTO> customerByLike = customerBo.findCustomerByLike(lblCusNo.getText());
            for (CustomerDTO customerDTO : customerByLike) {

                txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
                txtCutId.setText(customerDTO.getCustomer_id());
                lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
                lblCusNo.setText(customerDTO.getContact());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void lblNameOnKeyReleas(KeyEvent keyEvent) {
        try {
            List<CustomerDTO> customerByLike = customerBo.findCustomerByLike(lblCustomerName.getText());
            for (CustomerDTO customerDTO : customerByLike) {
                System.out.println(customerDTO.toString());
                txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
                txtCutId.setText(customerDTO.getCustomer_id());
                lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
                lblCusNo.setText(customerDTO.getContact());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
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
        loadAllCustomerId();
    }

    private void loadAllCustomerId() {
        //cmbCustomerId.getItems().clear();
        try {
            List<String> ids = new ArrayList<>();
            List<CustomerDTO> all = customerBo.findAll();
            for (CustomerDTO dto : all) {
                ids.add(dto.getCustomer_id());
            }
            cmbCustomerId.getItems().addAll(ids);
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

        boolean regex = RegexUtil.regex(btnAdd, lblQty, "^(^\\d{1,10}$)$", "-fx-text-fill: red");

        if (regex && !lblQty.getText().isEmpty()) {
            if (Integer.parseInt(lblQty.getText()) <= Integer.parseInt(txtQty.getText())) {
                double t = Double.parseDouble(lblQty.getText()) * Double.parseDouble(txtPrice.getText());
                txtTotal.setText(String.valueOf(t));
                btnAdd.setDisable(Double.parseDouble(txtTotal.getText()) < 0);
                btnAdd.setDisable(false);
            } else {
                btnAdd.setDisable(true);
                Notification.notificationWARNING("Order", "Out of Stock ");
            }
        } else {
            txtTotal.setText("00.00");
            btnAdd.setDisable(true);
        }


    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean isNotDuplicate = false;
        for (OrderTm orderTm : list) {
            if (orderTm.getItemCode().equals(LblCode.getText())) {
                orderTm.setQty(String.valueOf(Integer.parseInt(orderTm.getQty()) + Integer.parseInt(lblQty.getText())));
                orderTm.setPrice(String.valueOf(Double.parseDouble(orderTm.getPrice()) + Double.parseDouble(txtTotal.getText())));
                isNotDuplicate = false;
                break;
            } else {
                isNotDuplicate = true;
            }
        }

        if (isNotDuplicate) {
            OrderTm tm = new OrderTm();
            tm.setItemCode(LblCode.getText());
            tm.setProduct(txtProduct.getText());
            tm.setQty(lblQty.getText());
            tm.setPrice(txtTotal.getText());
            tm.setUnitPrice(txtPrice.getText());
            list.add(tm);
        }

        if (list.isEmpty()) {
            OrderTm tm = new OrderTm();
            tm.setItemCode(LblCode.getText());
            tm.setProduct(txtProduct.getText());
            tm.setQty(lblQty.getText());
            tm.setPrice(txtTotal.getText());
            tm.setUnitPrice(txtPrice.getText());
            list.add(tm);
        }

        tblOrder.getItems().clear();
        orderTms.clear();
        tblOrder.getItems().addAll(list);
        tblOrder.refresh();
        clearText();
        setOrderCount();
        setNetTotal();
    }

    private void clearText() {
        txtQty.setText("00");
        LblCode.clear();
        lblQty.clear();
        txtPrice.setText("00.00");
        txtTotal.setText("00.00");
        imgItem.setImage(null);
    }

    private void setNetTotal() {
        double total = 0;
        for (OrderTm tm : list) {
            total += Double.parseDouble(tm.getPrice());
        }
        txtNetTotal.setText(String.valueOf(total));
    }

    public void balanceOnKeyReleased(KeyEvent keyEvent) {
        try {


            double total = Double.parseDouble(lblBalance.getText()) - Double.parseDouble(txtNetTotal.getText());
            txtTotalBalance.setText(String.valueOf(total));
            btnPayNow.setDisable(!(Double.parseDouble(txtTotalBalance.getText()) >= 0));
        } catch (Exception e) {
        }

    }

    public void btnTotalOnAction(ActionEvent actionEvent) {

        if (Double.parseDouble(txtTotalBalance.getText()) >= 0 && cmbCustomerId.getValue() != null && !list.isEmpty() && lblBalance.getText() != null) {
            try {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setTotal(Double.parseDouble(txtNetTotal.getText()));
                orderDTO.setCust_id(txtCutId.getText());
                orderDTO.setCust_oid(nextId());
                orderDTO.setDate(DateTimeUtil.dateNow());
                orderDTO.setTime(DateTimeUtil.timeNow());

                ArrayList<OrderDetailsDTO> orderDetails = setOrderDetails(list, orderDTO);
                ArrayList<ProductDTO> productDTOS = setProduct(list, orderDTO);
                // boolean savedOrder = OrderModel.placeOrder(list, orderDTO);
                boolean savedOrder = customerOrderBO.placeOrder(orderDetails, orderDTO, productDTOS);
                if (savedOrder) {
                    //new Alert(Alert.AlertType.CONFIRMATION, "order saved").show();
                    clearAllText();
                    printBill(orderDTO.getCust_oid());
                    Notification.notification("Order", "Process Successful");
                } else {
                    //  new Alert(Alert.AlertType.WARNING, "something Wong").show();
                    Notification.notificationWARNING("Order", "something Wong");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Notification.notificationWARNING("Order", "please check the again");
        }
    }

    private ArrayList<ProductDTO> setProduct(ArrayList<OrderTm> list, OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> detailsDTOS = new ArrayList<>();
        for (OrderTm tm : list) {
            ProductDTO dto = productBO.findProductByCode(tm.getItemCode());
            dto.setQty(String.valueOf(Integer.parseInt(dto.getQty()) - Integer.parseInt(tm.getQty())));
            detailsDTOS.add(dto);
        }
        return detailsDTOS;
    }

    private ArrayList<OrderDetailsDTO> setOrderDetails(ArrayList<OrderTm> list, OrderDTO orderDTO) {
        ArrayList<OrderDetailsDTO> detailsDTOS = new ArrayList<>();
        for (OrderTm tm : list) {
            detailsDTOS.add(new OrderDetailsDTO(
                    tm.getItemCode(),
                    orderDTO.getCust_oid(),
                    Double.parseDouble(tm.getPrice()),
                    Integer.parseInt(tm.getQty())
            ));
        }
        return detailsDTOS;
    }


    private void clearAllText() {
        txtAddress.setText("@");
        txtCutId.setText("@Id");
        lblCustomerName.clear();
        lblCusNo.clear();
        txtNetTotal.setText("00.00");
        txtTotalBalance.setText("00.00");
        txtOrderCount.setText("0");
        lblBalance.clear();

        tblOrder.getItems().clear();
        orderTms.clear();
        list.clear();
    }

    private String nextId() throws SQLException, ClassNotFoundException {
        return customerOrderBO.next();
    }

    private void printBill(String cust_oid) {

        String fileNamePdf = "C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\PrintPDF\\" + cust_oid + ".pdf";
        System.out.println(cust_oid + " report id");
        InputStream resource = this.getClass().getResourceAsStream("/report/Bill.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", cust_oid);
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

            JasperExportManager.exportReportToPdfFile(print, fileNamePdf);
            System.out.println("Successfully completed the export");

            String body = "<h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(43, 180, 226);text-align: center;\">Lavish Stylo</h1>\n" +
                    "               <p style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\">\n" +
                    "                 Lavish styloo is a luxury salon located in Galle.we provide you various treatments from head to toe using thebest product,advanced and affordable price</p>\n" +
                    "               \n" +
                    "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-top: 40px; margin-left: 30px;\">opens at 8.00 am</h1>\n" +
                    "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-left: 30px;\">close at 8.00 pm</h1>\n" +
                    "               <h3 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252; margin-top: 40px;\">Bookings for treatments during the day can only be made between 8.00 am to 10.am. </h1>\n" +
                    "               <h3 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252;\">For later days, the salon is open that bookings can be made at any time</h4>\n" +
                    "                \n" +
                    "                              <h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(17, 45, 54);text-align: center;\">THANK YOU</h1>\n" +
                    "\n";

            MailUtil.sendEmail("sasindu.malshan03262001@gmail.com", "Lavish Stylo Bill Payment", body, cust_oid);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*   try {
            JasperDesign jasperDesign = JRXmlLoader.load(fileNameJrxml);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            String first_language = "Java";
            String second_language = "Structured text";
            HashMap hm = new HashMap();
            hm.put("id", cust_oid);

            JasperPrint jprint = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(jprint, fileNamePdf);
            System.out.println("Successfully completed the export");

            sendEmail(orderId, mail);

        } catch (
                Exception e) {
            System.out.println("fuck");
            System.out.print("Exception:" + e);
        }*/

    }
}
