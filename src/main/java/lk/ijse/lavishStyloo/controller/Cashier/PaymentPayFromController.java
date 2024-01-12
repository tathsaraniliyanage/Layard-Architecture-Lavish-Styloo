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
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.BookingBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.bo.custom.PaymentBO;
import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.BookingDTO;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.PaymentDTO;
import lk.ijse.lavishStyloo.dto.tm.BookingDetailsTm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import lk.ijse.lavishStyloo.util.MailUtil;
import lk.ijse.lavishStyloo.util.Notification;
import lk.ijse.lavishStyloo.util.RegexUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 12/2/2023
 */

public class PaymentPayFromController implements Initializable {
    public Text txtBookingId;
    public Text txtStart;
    public Text txtEnd;
    public Text txtPrice;
    public Text txtDate;
    public Text txtTime;
    public JFXComboBox cmb;
    public Text txtCutId;
    public Text txtAddress;
    public Text txtName;
    public Text txtContact;
    public Text txtTotalBalance;
    public JFXTextField lblBalance;
    public Text txtNetTotal;

    public TableColumn colTreatment;
    public TableColumn colCharge;
    public TableColumn colEmployee;
    public TableView tbl;

    public JFXButton btn;
    public JFXTextField lblPayment;

    ObservableList<BookingDetailsTm> tms = FXCollections.observableArrayList();

    CustomerBO customerBo = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);


    public void btnTotalOnAction(ActionEvent actionEvent) {

        if (cmb.getValue() != null && lblPayment.getText() != null && Double.parseDouble(txtTotalBalance.getText()) >= 0) {
            try {
                PaymentDTO dto = new PaymentDTO();
                dto.setBooking_id(txtBookingId.getText());
                dto.setDate(DateTimeUtil.dateNow());
                dto.setTime(DateTimeUtil.timeNow());
                dto.setNet_payment(txtNetTotal.getText());
                dto.setPayment_id(nextID());
                boolean save = paymentBO.savePayment(dto);
                if (save) {
                    clearText();
                    printBill(dto.getBooking_id());
                    Notification.notification("Payment", "payment Success");

                } else {
                    Notification.notificationWARNING("Payment", "Something wrong");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Notification.notificationWARNING("Payment", "Fill All");
        }
    }

    private void clearText() {
        tbl.getItems().clear();
        tms.clear();
        txtBookingId.setText("@");
        txtStart.setText("Start Time");
        txtEnd.setText("End Time");
        txtPrice.setText("00.00");
        txtDate.setText("Date");
        txtTime.setText("Time");
        txtName.setText("Name");
        txtCutId.setText("@00");
        txtAddress.setText("@");
        txtContact.setText("@");
    }

    private void printBill(String id) {

        String fileNamePdf = "C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\PrintPDF\\" + id + ".pdf";
        System.out.println(id + " report id");
        InputStream resource = this.getClass().getResourceAsStream("/report/Bill2.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", id);
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

            MailUtil.sendEmail("sasindu.malshan03262001@gmail.com", "Lavish Stylo Payment", body, id);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String nextID() {
        try {
            return paymentBO.nextID();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void cmbOnAction(ActionEvent actionEvent) {
        try {
            String bookingId = (String) cmb.getValue();
            BookingDTO dto = bookingBO.findBookingByID(bookingId);
            txtBookingId.setText(dto.getBooking_id());
            txtStart.setText("Booking Start : " + dto.getBooking_start());
            txtEnd.setText("End of : " + dto.getBooking_end());
            txtPrice.setText(dto.getTotal());
            txtDate.setText("Date : " + dto.getDate());
            txtTime.setText("Time : " + dto.getTime());

            CustomerDTO customerDTO = customerBo.findCustomerById(dto.getCus_id());
            txtName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
            txtCutId.setText(customerDTO.getCustomer_id());
            txtAddress.setText(customerDTO.getCity() + " ," + customerDTO.getLane() + " ," + customerDTO.getStreet());
            txtContact.setText(customerDTO.getContact());

            List<BookingDetailsTm> list = bookingBO.findBookingDetailsByBookingId(dto.getBooking_id());
            tbl.getItems().clear();
            tms.clear();
            tms.addAll(list);

        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIds();
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        tbl.setItems(tms);

    }

    private void setIds() {
        try {
            List<String> list = bookingBO.findIdByDateForPayment();
            cmb.getItems().addAll(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void balanceOnKeyReleased(KeyEvent keyEvent) {
        try {
            boolean regex = RegexUtil.regex(btn, lblBalance, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: red");
            double total = Double.parseDouble(lblBalance.getText()) - Double.parseDouble(txtNetTotal.getText());
            txtTotalBalance.setText(String.valueOf(total));
            if (regex) {
                btn.setDisable(Double.parseDouble(txtTotalBalance.getText()) < 0);
            }
        } catch (NumberFormatException e) {
            lblBalance.clear();
        }

    }

    private void setNetTotal() {
        try {
            if (lblPayment.getText().isEmpty()) {
                txtNetTotal.setText(txtPrice.getText());
            } else {
                txtNetTotal.setText(String.valueOf(Double.parseDouble(txtPrice.getText()) + Double.parseDouble(lblPayment.getText())));
            }
        } catch (NumberFormatException e) {
            lblPayment.clear();
            txtNetTotal.setText(txtPrice.getText());
        }
    }

    public void paymentOnKeyReleased(KeyEvent keyEvent) {
        boolean regex = RegexUtil.regex(btn, lblPayment, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: red");
        if (regex) setNetTotal();
    }

}
