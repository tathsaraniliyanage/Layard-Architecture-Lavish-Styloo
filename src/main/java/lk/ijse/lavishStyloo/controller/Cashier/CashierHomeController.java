package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.AttendanceBO;
import lk.ijse.lavishStyloo.bo.custom.BookingBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerOrderBO;
import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.controller.Admin.AdminHomeController;
import lk.ijse.lavishStyloo.controller.Admin.bar.BookingBarController;
import lk.ijse.lavishStyloo.dto.tm.HomeBookingTm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CashierHomeController implements Initializable {
    public VBox vBox;
    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ATTENDANCE);
    CustomerOrderBO customerOrderBO = (CustomerOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_ORDER);
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);


    @FXML
    private Text txtPendingBookings;
    @FXML
    private Text txtToDayBokkings;
    @FXML
    private JFXTextField txtText11;
    @FXML
    private Text date;
    @FXML
    private Text txtToDayAttendance;
    @FXML
    private Text txtAvalebelEmployees;
    @FXML
    private Text txtUnComlitedBookings;
    @FXML
    private Text txtCompliteBookings;
    @FXML
    private Text timeHouer;
    @FXML
    private Text timeMin;
    @FXML
    private Text timeStatus;
    @FXML
    private Text txtLimitedItems;
    @FXML
    private Text txtTodayOrders;

    @FXML
    void onKeyReleased(KeyEvent event) {
        try {
            List<HomeBookingTm> list = bookingBO.findNameTimeId(txtText11.getText());
            setData(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTime() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("a");
            while (true) {
                LocalTime time = LocalTime.parse(format.format(new Date()));
                timeStatus.setText(format2.format(new Date()));
                timeHouer.setText(String.valueOf(time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour()));
                timeMin.setText(String.valueOf(time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void setDate() {
        SimpleDateFormat format = new SimpleDateFormat("E, dd MMM");
        date.setText(format.format(new Date()));
    }

    private void setCount() {
        try {
            txtLimitedItems.setText(productBO.CountByQTY() + "+");
            txtAvalebelEmployees.setText(customerOrderBO.getAvailableEmployee() + "+");
            txtPendingBookings.setText(bookingBO.PendingCount() + "+");
            txtUnComlitedBookings.setText(bookingBO.UnCompliedCount() + "+");
            txtCompliteBookings.setText(bookingBO.CompliedCount() + "+");
            txtToDayBokkings.setText(bookingBO.CountBookingByDate(DateTimeUtil.dateNow()) + "+");
            txtToDayAttendance.setText(attendanceBO.countAttendanceByDate(DateTimeUtil.dateNow()) + "+");
            txtTodayOrders.setText(customerOrderBO.CountCustomerOrderByDate() + "+");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setCount();
        setTime();
        loadAllTodayBookings();
    }

    private void loadAllTodayBookings() {
        try {
            List<HomeBookingTm> list = bookingBO.findNameTimeId();
            setData(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setData(List<HomeBookingTm> list) {
        vBox.getChildren().clear();
        for (HomeBookingTm tm : list) {
            try {
                FXMLLoader loader = new FXMLLoader(AdminHomeController.class.getResource("/view/Admin/bar/BookingBar.fxml"));
                Parent root = loader.load();
                BookingBarController controller = loader.getController();
                controller.setData(tm);
                vBox.getChildren().add(root);
            } catch (IOException e) {
            }
        }
    }

    public void apoimentonClick(MouseEvent mouseEvent) {
        NavigationUtility.popupNavigation("Cashier/BookingFrom.fxml");
    }

    public void customerAdd(MouseEvent mouseEvent) {
        NavigationUtility.popupNavigation("Cashier/CustomerAddFrom.fxml");
    }
}
