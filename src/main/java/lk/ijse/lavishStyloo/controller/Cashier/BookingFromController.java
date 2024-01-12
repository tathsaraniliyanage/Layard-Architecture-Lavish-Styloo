package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.BookingBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerOrderBO;
import lk.ijse.lavishStyloo.dto.BookingDTO;
import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.tm.AppointmentTm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import lk.ijse.lavishStyloo.util.MailUtil;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookingFromController implements Initializable {

    private static BookingFromController instance;
    public Text txtNextAvailableTime;
    public JFXTextField lblCustomerName;
    public JFXTextField lblCusNo;
    public Text btnText;
    public Text txtAddress;
    public JFXComboBox cmbCustomerId;
    public Text txtCutId;
    public Text txtAvailableEmployee;
    public Text txtNetTotal;
    public Text txtTotal;
    public Text txtProduct;
    public JFXTimePicker timeStart;
    public JFXTimePicker timeEnd;
    public JFXButton btnTreatment;
    public TableView tblBooking;
    public TableColumn colTreatment;
    public TableColumn colAmount;
    public TableColumn colCategory;
    public TableColumn colEmployee;
    public TableColumn colChoose;
    public JFXTextField lblStartTimeH;
    public JFXTextField lblStartTimeM;
    public JFXTextField lblEndTimeH;
    public JFXTextField lblEndTimeM;
    public ObservableList<AppointmentTm> list = FXCollections.observableArrayList();

    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);
    CustomerBO customerBo = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    CustomerOrderBO customerOrderBO = (CustomerOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_ORDER);


    public BookingFromController() {
        instance = this;
    }

    public static BookingFromController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllCustomerId();
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        colChoose.setCellValueFactory(new PropertyValueFactory<>("choose"));
        tblBooking.setItems(list);

        setEmpCount();
        setNextBookingTime();
    }

    private void setNextBookingTime() {
        try {
            txtNextAvailableTime.setText(customerOrderBO.getAvailableTime());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setEmpCount() {
        try {
            txtAvailableEmployee.setText(customerOrderBO.getAvailableEmployee());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnTreatmentOnAction(ActionEvent actionEvent) {
        NavigationUtility.popupNavigation("Cashier/BookingTreatmentFrom.fxml");
    }

    public void btnApplyOnAction(ActionEvent actionEvent) {

        try {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setBooking_id(bookingBO.getNextId());
            bookingDTO.setDate(DateTimeUtil.dateNow());
            bookingDTO.setTime(DateTimeUtil.timeNow());
            bookingDTO.setCus_id(txtCutId.getText());
            bookingDTO.setTotal(txtNetTotal.getText());
            bookingDTO.setBooking_start(lblStartTimeH.getText() + ":" + lblStartTimeM.getText() + ":00");
            bookingDTO.setBooking_end(lblEndTimeH.getText() + ":" + lblEndTimeM.getText() + ":00");

            List<BookingDetailsDTO> list = new ArrayList<>();

            for (AppointmentTm tm : this.list) {
                BookingDetailsDTO dto = new BookingDetailsDTO();
                dto.setBooking_id(bookingDTO.getBooking_id());
                dto.setTreat_id(tm.getTreatment_id());
                dto.setNic(tm.getNic());
                dto.setCharge(Double.parseDouble(tm.getAmount()));
                list.add(dto);
            }
            boolean booking = bookingBO.booking(bookingDTO, list);
            if (booking) {
                Notification.notification("Appointment", "Appointment Applied");
                sendMail(bookingDTO, list);
            } else {

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void sendMail(BookingDTO bookingDTO, List<BookingDetailsDTO> list) {
        String body1 = "<h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(43, 180, 226);text-align: center;\">Lavish Stylo</h1>\n" +
                "               <p style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\">\n" +
                "                 Lavish styloo is a luxury salon located in Galle.we provide you various treatments from head to toe using thebest product,advanced and affordable price</p>\n" +
                "               \n" +
                "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-top: 40px; margin-left: 30px;\">opens at 8.00 am</h1>\n" +
                "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-left: 30px;\">close at 8.00 pm</h1>\n" +
                "               <h5 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252; margin-top: 40px;\">Bookings for treatments during the day can only be made between 8.00 am to 10.am. </h5>\n" +
                "               <h5 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252;\">For later days, the salon is open that bookings can be made at any time</h5>\n" +
                "\n" +
                "                <h4 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252;text-align: center;margin-top: 60px;\">Your Booking Successful Booking Start  </h4>\n" +
                "                <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; font-weight: bold; color: black; font-size: 30px; text-align: center;\">at " + bookingDTO.getBooking_start() + " am & End " + bookingDTO.getBooking_end() + " am </h1>";

        String bodyUlOpen = " <ul>";
       /* String bodyList=" <li>\n" +
                "                <span style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; font-weight: bold; color: black; font-size: 15px;\">xxxx</span>\n" +
                "              </li>";*/

        ArrayList<String> arrayList = new ArrayList<>();
        for (AppointmentTm s : this.list) {
            arrayList.add(" <li>\n" +
                    "                <span style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; font-weight: bold; color: black; font-size: 15px;\">" + s.getTreatment() + "</span>\n" +
                    "              </li>");
        }

        String bodyUlClose = " </ul>";

        String end = "<span style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; font-weight: bold; color: black; font-size: 15px;text-align:center;\">Booking Payment : LKR." + bookingDTO.getTotal() + "</span>\n" +
                "                              <h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(17, 45, 54);text-align: center;\">THANK YOU</h1>";


        StringBuffer body = new StringBuffer();

        body.append(body1);
        body.append(bodyUlOpen);

        for (String s : arrayList) {
            body.append(s);
        }

        body.append(bodyUlClose);
        body.append(end);

        MailUtil.sendEmail("sasindu.malshan03262001@gmail.com", "Lavish Stylo Bill Payment", body);

    }

    public void cmbCustomerOnaAction(ActionEvent actionEvent) {
        try {
            CustomerDTO customerDTO = customerBo.findCustomerById(String.valueOf(cmbCustomerId.getValue()));
            txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
            txtCutId.setText(customerDTO.getCustomer_id());
            lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
            lblCusNo.setText(customerDTO.getContact());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        NavigationUtility.popupNavigation("Cashier/CustomerAddFrom.fxml");
    }

    public void lblNameOnKeyReleased(KeyEvent keyEvent) {
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

    public void contactOnKeyReleased(KeyEvent keyEvent) {
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

    private void loadAllCustomerId() {

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

    public void setData(AppointmentTm appointmentTm) {
        boolean isNot = true;
        for (AppointmentTm tm : list) {
            if (appointmentTm.getTreatment_id().equals(tm.getTreatment_id())) {
                isNot = false;
            }
        }
        if (isNot) {
            list.add(appointmentTm);
            setTotal();
        }
    }

    private void setTotal() {
        double total = 0;
        for (AppointmentTm tm : list) {
            total += Double.parseDouble(tm.getAmount());
        }
        txtNetTotal.setText(String.valueOf(total));
    }

    public void empClick(MouseEvent mouseEvent) {
        NavigationUtility.popupNavigation("Cashier/BookingAvelebleEmployeeFrom.fxml");
    }
}
