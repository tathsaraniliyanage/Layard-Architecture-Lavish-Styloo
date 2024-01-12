package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.BookingBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerOrderBO;
import lk.ijse.lavishStyloo.bo.custom.SupperOrderBO;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportFromController implements Initializable {

    public LineChart orders;
    public LineChart appointment;
    public LineChart CustomerOrders;
    public CategoryAxis OY;
    public CategoryAxis CY;
    public CategoryAxis AY;
    public JFXComboBox cmbMonth;
    public JFXComboBox cmbYear;
    public Text txtReportStatus;

    CustomerBO customerBo = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    SupperOrderBO supperOrderBO = (SupperOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER_ORDER);
    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);


    String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    CustomerOrderBO customerOrderBO = (CustomerOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_ORDER);

    public void todayAttendanceAddOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/report/TodayAttendance.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void allEmployeeAddOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/report/AllEmployee.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void appointmentAddOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/report/TodayApoiment.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cmbMothAction(ActionEvent actionEvent) {
        setTitle();
    }

    /*public List<ReportCm> loadCustomerOrderForReport() {
        List<ReportCm> list = null;
        try {
            list = CustomerModel.getCustomerOrder();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public List<ReportCm> loadSupplerOrderForReport() {
        List<ReportCm> list = null;
        try {
            list = SupperOrderModel.getSupplierOrder();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public List<ReportCm> loadAppointmentForReport() {
        List<ReportCm> list = null;
        try {
            list = BookingModel.getAppointment();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }*/

    public void cmbYearOnAction(ActionEvent actionEvent) {
        setTitle();
    }

    private XYChart.Series setCustomerOrders(List<ReportCm> list) {

        XYChart.Series series = new XYChart.Series();

        for (ReportCm cm : list) {
            series.getData().add(new XYChart.Data<String, Double>(cm.getTitle(), cm.getValue()));
        }
//        chart.getData().addAll(series);
        CustomerOrders.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
//        series.getNode().setStyle("-fx-stroke: #FE030A");
        series.setName("Customer Orders");
//        return series;
        return series;
    }

    private XYChart.Series setSupplerOrders(List<ReportCm> list) {

        XYChart.Series series = new XYChart.Series();

        for (ReportCm cm : list) {
            series.getData().add(new XYChart.Data<String, Double>(cm.getTitle(), cm.getValue()));
        }
//        chart.getData().addAll(series);
        orders.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
//        series.getNode().setStyle("-fx-stroke: #FE030A");
        series.setName("Suppler Orders");
//        return series;
        return series;
    }



  /*  private void setCustomerOrderChart() {
        XYChart.Series series = setCustomerOrders(loadCustomerOrderForReport());
        CustomerOrders.getData().clear();
        CY.setAnimated(false);
        CY.setTickMarkVisible(false);
        CustomerOrders.getData().addAll(series);
        CustomerOrders.getXAxis().setAutoRanging(true);
    }

    private void setAppointmentChart() {
        XYChart.Series series1 = setAppointment(loadAppointmentForReport());
        appointment.getData().clear();
        AY.setAnimated(false);
        AY.setTickMarkVisible(false);
        appointment.getData().addAll(series1);
        appointment.getXAxis().setAutoRanging(true);
    }

    private void setOrderChart() {
        XYChart.Series series1 = setCustomerOrders(loadCustomerOrderForReport());
        XYChart.Series series2 = setSupplerOrders(loadSupplerOrderForReport());
        orders.getData().clear();
        OY.setAnimated(false);
        OY.setTickMarkVisible(false);
        orders.getData().addAll(series1, series2);
        orders.getXAxis().setAutoRanging(true);
    }*/

    private XYChart.Series setAppointment(List<ReportCm> list) {

        XYChart.Series series = new XYChart.Series();

        for (ReportCm cm : list) {
            series.getData().add(new XYChart.Data<String, Double>(cm.getTitle(), cm.getValue()));
        }
//        chart.getData().addAll(series);
        appointment.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
//        series.getNode().setStyle("-fx-stroke: #FE030A");
        series.setName("Appointment");
//        return series;
        return series;
    }

    private void setMonth() {
        List<String> months = new ArrayList<>();
        for (String s : allMonth) {
            months.add(s);
        }
        months.add("Select Month");
        cmbMonth.getItems().addAll(months);
        cmbMonth.setValue(cmbMonth.getItems().get((Integer.parseInt(new SimpleDateFormat("M").format(new Date())) - 1)));
    }

    private void setYear() {
        try {
            List<String> distinctYears = customerOrderBO.findDistinctYears();
            distinctYears.add("Select Year");
            cmbYear.getItems().addAll(distinctYears);
            if (!distinctYears.isEmpty()) cmbYear.setValue(distinctYears.get(0));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTitle() {
        String month = String.valueOf(cmbMonth.getValue());
        String year = String.valueOf(cmbYear.getValue());
        if (month.startsWith("Select")) {
            txtReportStatus.setText(cmbYear.getValue() + " Yearly ");
            cmbYear.setDisable(true);
            setYearlyReport();
        } else if (year.startsWith("Select")) {
            txtReportStatus.setText(cmbMonth.getValue() + " Monthly ");
            cmbMonth.setDisable(true);
            setMonthlyReport();
        } else {
            cmbYear.setDisable(false);
            cmbMonth.setDisable(false);
            txtReportStatus.setText(cmbYear.getValue() + " ," + cmbMonth.getValue());
            setSelectedDateReport();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      /*  setCustomerOrderChart();
        setOrderChart();
        setAppointmentChart();*/
        setMonth();
        setYear();
        setTitle();

        setCustomerOrderChart(setCustomerOrders(getDayCustomerOrder()));
        setAppointmentChart(setAppointment(getDayAppointmentChart()));
        setOrderChart(setCustomerOrders(getDayCustomerOrder()), setSupplerOrders(getDaySupplerOrders()));

    }

    private void setSelectedDateReport() {
        setCustomerOrderChart(setCustomerOrders(getDayCustomerOrder()));
        setAppointmentChart(setAppointment(getDayAppointmentChart()));
        setOrderChart(setCustomerOrders(getDayCustomerOrder()), setSupplerOrders(getDaySupplerOrders()));
    }

    private void setMonthlyReport() {
        setCustomerOrderChart(setCustomerOrders(getMonthlyCustomerOrder()));
        setAppointmentChart(setAppointment(getMonthlyAppointmentChart()));
        setOrderChart(setCustomerOrders(getMonthlyCustomerOrder()), setSupplerOrders(getMonthlySupplerOrders()));
    }

    private void setYearlyReport() {
        setCustomerOrderChart(setCustomerOrders(getYearlyCustomerOrder()));
        setAppointmentChart(setAppointment(getYearlyAppointmentChart()));
        setOrderChart(setCustomerOrders(getYearlyCustomerOrder()), setSupplerOrders(getYearlySupplerOrders()));
    }

    /**
     * set Yearly Chart
     */

    private List<ReportCm> getYearlySupplerOrders() {
        List<ReportCm> list = null;
        try {
            list = supperOrderBO.getYearlySupplierOrder(String.valueOf(cmbYear.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    private List<ReportCm> getYearlyAppointmentChart() {
        List<ReportCm> list = null;
        try {
            list = bookingBO.getYearlyAppointment(String.valueOf(cmbYear.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    private List<ReportCm> getYearlyCustomerOrder() {
        List<ReportCm> list = null;
        try {
            list = customerBo.getYearlyCustomerOrder(String.valueOf(cmbYear.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * Set Monthly
     */
    private List<ReportCm> getMonthlySupplerOrders() {
        List<ReportCm> list = null;
        try {
            list = supperOrderBO.getMonthlySupplierOrder(DateTimeUtil.yearNow(), String.valueOf(cmbMonth.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    private List<ReportCm> getMonthlyAppointmentChart() {
        List<ReportCm> list = null;
        try {
            list = bookingBO.getMonthlyAppointment(DateTimeUtil.yearNow(), String.valueOf(cmbMonth.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    private List<ReportCm> getMonthlyCustomerOrder() {
        List<ReportCm> list = null;
        try {
            list = customerBo.getMonthlyCustomerOrder(DateTimeUtil.yearNow(), String.valueOf(cmbMonth.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(list.toString());
        return list;
    }

    /**
     * Set Day
     */
    private List<ReportCm> getDaySupplerOrders() {
        List<ReportCm> list = null;
        try {
            list = supperOrderBO.getMonthlySupplierOrder((String) cmbYear.getValue(), String.valueOf(cmbMonth.getValue()));

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    private List<ReportCm> getDayAppointmentChart() {
        List<ReportCm> list = null;
        try {
            list = bookingBO.getMonthlyAppointment(String.valueOf(cmbYear.getValue()), String.valueOf(cmbMonth.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    private List<ReportCm> getDayCustomerOrder() {
        List<ReportCm> list = null;
        try {
            list = customerBo.getMonthlyCustomerOrder(String.valueOf(cmbYear.getValue()), String.valueOf(cmbMonth.getValue()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


    private void setCustomerOrderChart(XYChart.Series series) {
        CustomerOrders.getData().clear();
        CY.setAnimated(false);
        CY.setTickMarkVisible(false);
        CustomerOrders.getData().addAll(series);
        CustomerOrders.getXAxis().setAutoRanging(true);
    }

    private void setAppointmentChart(XYChart.Series series) {
        appointment.getData().clear();
        AY.setAnimated(false);
        AY.setTickMarkVisible(false);
        appointment.getData().addAll(series);
        appointment.getXAxis().setAutoRanging(true);
    }

    private void setOrderChart(XYChart.Series series1, XYChart.Series series2) {
        orders.getData().clear();
        OY.setAnimated(false);
        OY.setTickMarkVisible(false);
        orders.getData().addAll(series1, series2);
        orders.getXAxis().setAutoRanging(true);
    }


}
