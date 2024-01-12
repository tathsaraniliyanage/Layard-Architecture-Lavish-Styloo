package lk.ijse.lavishStyloo.controller.Cashier;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.CustomerOrderBO;
import lk.ijse.lavishStyloo.dto.tm.CustomerOrderDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.CustomerOrderTm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class VewOrderFromController implements Initializable {
    private static VewOrderFromController controller;
    public Text txtTodayOrdersCount;
    public RadioButton rBtnAll;
    public RadioButton rBtnTodayOrders;
    public RadioButton rBtnSelectedDateOrders;
    public DatePicker dpOrder;
    public TableView tblOrder;
    public TableColumn colOdId;
    public TableColumn colCusId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colTotal;
    public TableColumn colQty;
    public TableColumn colCustomer;
    public Text txtAllOrders;
    public AnchorPane paneDefault;
    public AnchorPane paneOrderDetails;
    public Text txtNameOrderDetail;
    public Text txtOrderIdOrderDetails;
    public TableView tblOrderDetails;
    public TableColumn colItemCode;
    public TableColumn colItem;
    public TableColumn colQtyItem;
    public TableColumn colPriceItem;
    public Text txtCutIdOrderDetails;
    public Text txtDateOrderDetails;
    public Text txtTimeOrderDetails;
    public Text txtAllOrdersCount;
    public Text txtSubTotal;
    public Text txtQtyAllOrder;
    ObservableList<CustomerOrderTm> list = FXCollections.observableArrayList();
    ObservableList<CustomerOrderDetailsTm> list2 = FXCollections.observableArrayList();
    CustomerOrderBO customerOrderBO = (CustomerOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_ORDER);


    public VewOrderFromController() {
        controller = this;
    }

    public static VewOrderFromController getController() {
        return controller;
    }

    public void productFromTblOnClick(MouseEvent mouseEvent) {
        translateTransition();
        CustomerOrderTm tm = (CustomerOrderTm) tblOrder.getSelectionModel().getSelectedItem();
        txtSubTotal.setText(tm.getTotal());
        txtQtyAllOrder.setText(tm.getCount());
        txtNameOrderDetail.setText(tm.getName());
        txtCutIdOrderDetails.setText(tm.getCustomerId());
        txtOrderIdOrderDetails.setText(tm.getCustomerOrderId());
        txtTimeOrderDetails.setText(tm.getTime());
        txtDateOrderDetails.setText(tm.getDate());
        setOderDetails(tm.getCustomerOrderId());
    }

    private void setOderDetails(String customerOrderId) {
        try {
            List<CustomerOrderDetailsTm> customerOrders = customerOrderBO.findCustomerOrderDetailsByOrderId(customerOrderId);
            toProcessDetails(customerOrders);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcessDetails(List<CustomerOrderDetailsTm> customerOrders) {
        list2.clear();
        tblOrderDetails.getItems().clear();
        tblOrderDetails.getItems().addAll(customerOrders);
        tblOrderDetails.refresh();
    }

    public void rBtnAllOnAction(ActionEvent actionEvent) {

        setFilter();
    }

    public void rBtnTodayOrdersOnAction(ActionEvent actionEvent) {
        setFilter();
    }

    public void rBtnSelectedDateOrdersOnAction(ActionEvent actionEvent) {
        setFilter();
    }

    private void setFilter() {
        if (rBtnAll.isSelected()) {
            dpOrder.setDisable(true);
            loadAllOrders();
        } else if (rBtnTodayOrders.isSelected()) {
            dpOrder.setDisable(true);
            loadToDayOrders();
        } else if (rBtnSelectedDateOrders.isSelected()) {
            dpOrder.setDisable(false);
            loadSelectedDate();
        }
    }

    private void loadSelectedDate() {
        String date = String.valueOf(dpOrder.getValue());
        if (dpOrder.getValue() == null)
            date = DateTimeUtil.dateNow();
        try {
            List<CustomerOrderTm> customerOrders = customerOrderBO.findCustomerOrdersByDate(date);
            if (customerOrders.get(customerOrders.size() - 1).getCustomerOrderId() != null) {
                toProcess(customerOrders);
            } else {
                list.clear();
                tblOrder.getItems().clear();
                tblOrder.refresh();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadToDayOrders() {
        try {
            List<CustomerOrderTm> customerOrders = customerOrderBO.findCustomerOrdersByDate(DateTimeUtil.dateNow());
            if (customerOrders.get(customerOrders.size() - 1).getCustomerOrderId() != null) {
                toProcess(customerOrders);
            } else {
                list.clear();
                tblOrder.getItems().clear();
                tblOrder.refresh();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadAllOrders() {
        try {
            List<CustomerOrderTm> customerOrders = customerOrderBO.findCustomerOrders();
            toProcess(customerOrders);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(List<CustomerOrderTm> customerOrders) {
        list.clear();
        tblOrder.getItems().clear();
        tblOrder.getItems().addAll(customerOrders);
        tblOrder.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colOdId.setCellValueFactory(new PropertyValueFactory<>("customerOrderId"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("count"));
        tblOrder.setItems(list);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQtyItem.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPriceItem.setCellValueFactory(new PropertyValueFactory<>("price"));
        tblOrderDetails.setItems(list2);

        loadAllOrders();
        setCount();
    }

    private void setCount() {
        try {
            txtAllOrders.setText(customerOrderBO.CountCustomerOrder() + "+");
            txtTodayOrdersCount.setText(customerOrderBO.CountCustomerOrderByDate() + "+");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dpOrderOnAction(ActionEvent actionEvent) {
        loadSelectedDate();
    }

    private void translateTransition() {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(paneDefault);
        translate.setDuration(Duration.millis(500));
        translate.setByX(-350);
        translate.play();

        TranslateTransition translate2 = new TranslateTransition();
        translate2.setNode(paneOrderDetails);
        translate2.setDuration(Duration.millis(500));
        translate2.setByX(350);
        translate2.play();
    }

    public void resetOnAction(ActionEvent actionEvent) {
        translateTransition2();
        tblOrder.getSelectionModel().clearSelection();
    }

    private void translateTransition2() {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(paneOrderDetails);
        translate.setDuration(Duration.millis(500));
        translate.setByX(-350);
        translate.play();

        TranslateTransition translate2 = new TranslateTransition();
        translate2.setNode(paneDefault);
        translate2.setDuration(Duration.millis(500));
        translate2.setByX(350);
        translate2.play();
    }

    public void searchOrder(String text) {
        try {
            List<CustomerOrderTm> customerOrders = customerOrderBO.findCustomerOrdersByLike(text);
            toProcess(customerOrders);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
