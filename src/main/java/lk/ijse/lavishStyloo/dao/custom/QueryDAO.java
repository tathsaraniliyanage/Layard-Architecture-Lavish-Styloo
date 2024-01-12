package lk.ijse.lavishStyloo.dao.custom;

import lk.ijse.lavishStyloo.dao.SuperDAO;
import lk.ijse.lavishStyloo.dto.projection.*;
import lk.ijse.lavishStyloo.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public interface QueryDAO extends SuperDAO {
    /**
     * Salary
     */
    public String getCount() throws SQLException, ClassNotFoundException;

    boolean isExistThisMonth(String nic) throws SQLException, ClassNotFoundException;

    List<SalaryProjection> findSalary() throws SQLException, ClassNotFoundException;

    List<SalaryProjection> toEntityList(ResultSet resultSet) throws SQLException;

    List<SalaryProjection> findSalaryByLike(String text) throws SQLException, ClassNotFoundException;

    /**
     * Supplier Order Details
     */
    List<SupperOrderDetailsProjection> findCustomerOrderDetailsByOrderId(String supperOrderId) throws SQLException, ClassNotFoundException;

    /**
     * supplier Order
     */

    List<SupperOrderProjection> findAll() throws SQLException, ClassNotFoundException;

    List<SupperOrderProjection> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException;

    List<SupperOrderProjection> toSupperOrderProjection(ResultSet resultSet) throws SQLException;

    /**
     * customer order
     */
    List<String> findDistinctYears() throws SQLException, ClassNotFoundException;

    String getAvailableEmployee() throws SQLException, ClassNotFoundException;

    String getAvailableTime() throws SQLException, ClassNotFoundException;

    /**
     * Attendance
     */

    List<AttendanceProjection> toCustomer(ResultSet resultSet) throws SQLException;

    List<AttendanceProjection> findByDateAndNameAndNic(String dateNow, String text) throws SQLException, ClassNotFoundException;

    List<AttendanceProjection> findByDate(String date) throws SQLException, ClassNotFoundException;

    /**
     * Employee
     */
    List<Employee> toEntities(ResultSet result) throws SQLException;

    List<Employee> findEmployeeAvailable() throws SQLException, ClassNotFoundException;

    String CountAvailable() throws SQLException, ClassNotFoundException;

    /**
     * Customer Order
     */

    List<CustomerOrderProjecton> toCustomerOrderProjection(ResultSet resultSet) throws SQLException;

    List<CustomerOrderProjecton> findCustomerAndOrders() throws SQLException, ClassNotFoundException;

    List<CustomerOrderProjecton> findCustomerAndOrdersByDate(String date) throws SQLException, ClassNotFoundException;

    List<CustomerOrderProjecton> findCustomerAndOrdersByLike(String text) throws SQLException, ClassNotFoundException;

    List<CustomerOrderDetailsProjection> findCustomerAndOrderDetailsByOrderId(String customerOrderId) throws SQLException, ClassNotFoundException;

    List<CustomerOrderDetailsProjection> toCustomerOrderDetailsProjection(ResultSet resultSet) throws SQLException;

    /**
     * Booking
     */
    List<HomeBookingProjection> findNameTimeId() throws SQLException, ClassNotFoundException;

    List<HomeBookingProjection> findNameTimeId(String txt) throws SQLException, ClassNotFoundException;

    List<HomeBookingProjection> toHomeTm(ResultSet resultSet) throws SQLException;

    String CompliedCount() throws SQLException, ClassNotFoundException;

    List<BookingProjection> findBooking() throws SQLException, ClassNotFoundException;

    List<BookingProjection> findBookingByDate(String date) throws SQLException, ClassNotFoundException;

    List<BookingProjection> toTms(ResultSet resultSet) throws SQLException;

    List<BookingDetailsProjection> findBookingDetailsByBookingId(String bookingId) throws SQLException, ClassNotFoundException;

    List<BookingDetailsProjection> toDetailsTms(ResultSet resultSet) throws SQLException;
}
