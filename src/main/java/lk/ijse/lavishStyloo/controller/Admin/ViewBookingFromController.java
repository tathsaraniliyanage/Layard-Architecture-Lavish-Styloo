package lk.ijse.lavishStyloo.controller.Admin;

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
import lk.ijse.lavishStyloo.bo.custom.BookingBO;
import lk.ijse.lavishStyloo.dto.tm.BookingDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.BookingTM;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewBookingFromController implements Initializable {
    public Text txtTodayBookingsCount;
    public RadioButton rBtnAll;
    public RadioButton rBtnTodayOrders;
    public RadioButton rBtnSelectedDateOrders;
    public DatePicker dpBooking;

    public TableView tblBooking;
    public TableColumn colBookingId;
    public TableColumn colCusId;
    public TableColumn colCustomer;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colTotal;
    public TableColumn colBookingStart;
    public TableColumn colBookingEnd;
    public TableColumn colOption;


    public Text txtAllBookings;

    public AnchorPane paneDefault;
    public AnchorPane paneBookingDetails;
    public Text txtNameOrderDetail;
    public Text txtBookingIdOrderDetails;

    public TableView tblBoookingDetails;
    public TableColumn colTreatment;
    public TableColumn colEmployee;
    public TableColumn colNic;
    public TableColumn colCharge;

    public Text txtCutIdBookingDetails;
    public Text txtDateOrderDetails;
    public Text txtTimeOrderDetails;
    public Text txtAllOrdersCount;
    public Text txtSubTotal;
    public Text txtQtyAllOrder;

    ObservableList<BookingTM> list = FXCollections.observableArrayList();
    ObservableList<BookingDetailsTm> list2 = FXCollections.observableArrayList();
    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);


    public void bookingFromTblOnClick(MouseEvent mouseEvent) {
        translateTransition();
        BookingTM tm = (BookingTM) tblBooking.getSelectionModel().getSelectedItem();
        txtSubTotal.setText(tm.getTotal());
        txtNameOrderDetail.setText(tm.getCustomer());
        txtCutIdBookingDetails.setText(tm.getCusId());
        txtBookingIdOrderDetails.setText(tm.getBookingId());
        txtTimeOrderDetails.setText(tm.getTime());
        txtDateOrderDetails.setText(tm.getDate());
        setBookingDetails(tm.getBookingId());
    }

    private void setBookingDetails(String bookingId) {
        try {
            List<BookingDetailsTm> list = bookingBO.findBookingDetailsByBookingId(bookingId);
            txtQtyAllOrder.setText(String.valueOf(list.size()));
            toProcessDetails(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcessDetails(List<BookingDetailsTm> customerOrders) {
        list2.clear();
        tblBoookingDetails.getItems().clear();
        tblBoookingDetails.getItems().addAll(customerOrders);
        tblBoookingDetails.refresh();
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
            dpBooking.setDisable(true);
            loadAllBooking();
        } else if (rBtnTodayOrders.isSelected()) {
            dpBooking.setDisable(true);
            loadToDayOrders();
        } else if (rBtnSelectedDateOrders.isSelected()) {
            dpBooking.setDisable(false);
            loadSelectedDate();
        }
    }

    private void loadSelectedDate() {
        String date = String.valueOf(dpBooking.getValue());
        if (dpBooking.getValue() == null)
            date = DateTimeUtil.dateNow();
        try {
            List<BookingTM> bookingTMList = bookingBO.findBookingByDate(date);
            if (bookingTMList.get(bookingTMList.size() - 1).getBookingId() != null) {
                toProcess(bookingTMList);
            } else {
                list.clear();
                tblBooking.getItems().clear();
                tblBooking.refresh();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadToDayOrders() {
        try {
            List<BookingTM> bookingTMList = bookingBO.findBookingByDate(DateTimeUtil.dateNow());
            if (!bookingTMList.isEmpty() && bookingTMList.get(bookingTMList.size() - 1).getBookingId() != null) {
                toProcess(bookingTMList);
            } else {
                list.clear();
                tblBooking.getItems().clear();
                tblBooking.refresh();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadAllBooking() {
        try {
            List<BookingTM> bookingTMList = bookingBO.findBooking();
            toProcess(bookingTMList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(List<BookingTM> bookingTMList) {
        list.clear();
        tblBooking.getItems().clear();
        tblBooking.getItems().addAll(bookingTMList);
        tblBooking.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colBookingStart.setCellValueFactory(new PropertyValueFactory<>("bookingStart"));
        colBookingEnd.setCellValueFactory(new PropertyValueFactory<>("bookingEnd"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("option"));
        tblBooking.setItems(list);

        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
        tblBoookingDetails.setItems(list2);

        loadAllBooking();
        setCount();
    }

    private void setCount() {
        try {
            txtAllBookings.setText(bookingBO.CountBooking() + "+");
            txtTodayBookingsCount.setText(bookingBO.CountBookingByDate(DateTimeUtil.dateNow()) + "+");
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
        translate2.setNode(paneBookingDetails);
        translate2.setDuration(Duration.millis(500));
        translate2.setByX(350);
        translate2.play();
    }

    public void resetOnAction(ActionEvent actionEvent) {
        translateTransition2();
        tblBooking.getSelectionModel().clearSelection();
    }

    private void translateTransition2() {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(paneBookingDetails);
        translate.setDuration(Duration.millis(500));
        translate.setByX(-350);
        translate.play();

        TranslateTransition translate2 = new TranslateTransition();
        translate2.setNode(paneDefault);
        translate2.setDuration(Duration.millis(500));
        translate2.setByX(350);
        translate2.play();
    }

   /* public void searchOrder(String text) {
        try {
            List<CustomerOrderTm> customerOrders = CustomerOrderModel.findCustomerOrdersByLike(text);
            toProcess(customerOrders);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }*/
}
