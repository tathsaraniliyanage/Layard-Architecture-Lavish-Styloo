package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.dto.projection.*;
import lk.ijse.lavishStyloo.entity.Employee;
import lk.ijse.lavishStyloo.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class QueryDAOImpl implements QueryDAO {
    /**
     * Salary
     * */

    public String getCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE()) and (SELECT COUNT(*) FROM attendance WHERE nic= e.nic and date BETWEEN DATE_SUB(NOW(), INTERVAL 31 DAY) AND NOW()) !=0";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";

    }

    public boolean isExistThisMonth(String nic) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.city,e.street,e.lane,e.email,e.contact,s.bonus,s.salary FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE()) AND e.nic=?";
        ResultSet set = CrudUtil.crudUtil(sql, nic);
        return set.next();
    }

    public List<SalaryProjection> findSalary() throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.city,e.street,e.lane,e.email,e.contact,s.bonus,s.salary FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE())";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        return toEntityList(resultSet);
    }

    public List<SalaryProjection> findSalaryByLike(String text) throws SQLException, ClassNotFoundException {
        String arg = text + "%";
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.city,e.street,e.lane,e.email,e.contact,s.bonus,s.salary FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE()) && e.nic LIKE ? OR e.first_name LIKE ? OR  e.last_name LIKE ? OR e.contact LIKE ? OR s.salary LIKE ? OR s.bonus LIKE ?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, arg, arg, arg, arg, arg, arg);
        return toEntityList(resultSet);
    }

    public List<SalaryProjection> toEntityList(ResultSet resultSet) throws SQLException {
        List<SalaryProjection> list = new ArrayList<>();
        while (resultSet.next()) {
            SalaryProjection salaryProjection = new SalaryProjection();
            salaryProjection.setNic(resultSet.getString(1));
            salaryProjection.setName(resultSet.getString(2) + " " + resultSet.getString(3));
            salaryProjection.setAddress(resultSet.getString(4) + " ," + resultSet.getString(5) + " ," + resultSet.getString(6));
            salaryProjection.setMail(resultSet.getString(7));
            salaryProjection.setContact(resultSet.getString(8));
            salaryProjection.setBones(resultSet.getDouble(9));
            salaryProjection.setSalary(resultSet.getDouble(10));
            list.add(salaryProjection);
        }
        return list;
    }

    /**
     * Supplier Order Details
     * */
    public List<SupperOrderDetailsProjection> findCustomerOrderDetailsByOrderId(String supperOrderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.crudUtil("SELECT p.product_code,p.product,s.price,s.qty FROM supplier_order_details s inner join product p on s.product_code = p.product_code where s.sup_oid=?",supperOrderId);

        List<SupperOrderDetailsProjection> list=new ArrayList<>();

        while (resultSet.next()){
            SupperOrderDetailsProjection supperOrderDetailsProjection=new SupperOrderDetailsProjection();
            supperOrderDetailsProjection.setCode(resultSet.getString(1));
            supperOrderDetailsProjection.setProduct(resultSet.getString(2));
            supperOrderDetailsProjection.setPrice(resultSet.getString(3));
            supperOrderDetailsProjection.setQty(resultSet.getString(4));
            list.add(supperOrderDetailsProjection);
        }
        return list;
    }

    /**
     * supplier Order
     * */

    public  List<SupperOrderProjection> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT s.sup_oid,s2.supplier_id,s2.supplier_name,s.date,s.time,s.total,COUNT(sod.sup_oid) FROM supplier_order s INNER JOIN supplier s2 on s.supplier_id = s2.supplier_id inner join supplier_order_details sod on s.sup_oid = sod.sup_oid GROUP BY s.sup_oid";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        return toSupperOrderProjection(resultSet);
    }

    public  List<SupperOrderProjection> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT s.sup_oid,s2.supplier_id,s2.supplier_name,s.date,s.time,s.total,COUNT(sod.sup_oid) FROM supplier_order s INNER JOIN supplier s2 on s.supplier_id = s2.supplier_id inner join supplier_order_details sod on s.sup_oid = sod.sup_oid where s.date=? GROUP BY s.sup_oid", date);
        return toSupperOrderProjection(resultSet);
    }

    public  List<SupperOrderProjection> toSupperOrderProjection(ResultSet resultSet) throws SQLException {
        List<SupperOrderProjection> list = new ArrayList<>();

        while (resultSet.next()) {
            SupperOrderProjection supperOrderProjection = new SupperOrderProjection();
            supperOrderProjection.setSupperOrderId(resultSet.getString(1));
            supperOrderProjection.setSupperId(resultSet.getString(2));
            supperOrderProjection.setDear(resultSet.getString(3));
            supperOrderProjection.setDate(resultSet.getString(4));
            supperOrderProjection.setTime(resultSet.getString(5));
            supperOrderProjection.setTotal(resultSet.getString(6));
            supperOrderProjection.setCount(resultSet.getString(7));
            list.add(supperOrderProjection);

        }
        return list;
    }
    /**
     * customer order
     * */
    public List<String> findDistinctYears() throws SQLException, ClassNotFoundException {
        List<String> list = new ArrayList<>();
        ResultSet resultSet = CrudUtil.crudUtil("SELECT DISTINCT YEAR(o.date)  FROM customer_order o UNION SELECT DISTINCT YEAR(s.date) FROM supplier_order s UNION SELECT DISTINCT YEAR(b.date) FROM booking b");
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public String getAvailableEmployee() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(a.nic) FROM attendance a INNER JOIN employee e ON e.nic= a.nic WHERE a.date=CURDATE() AND a.nic NOT IN  (SELECT bd.nic FROM booking b INNER JOIN booking_details bd on b.booking_id = bd.booking_id WHERE b.date= CURDATE() AND b.booking_id NOT IN (SELECT b.booking_id FROM booking b RIGHT JOIN payment p on b.booking_id = p.booking_id WHERE b.date= CURDATE())) AND e.role NOT IN ('ADMIN','CASHIER')");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public String getAvailableTime() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        if (Integer.parseInt(getAvailableEmployee()) == 1) {
            resultSet = CrudUtil.crudUtil("SELECT MaX( b.booking_end)  FROM booking b WHERE b.date=CURDATE()  AND b.booking_id NOT IN (SELECT p.booking_id FROM booking b INNER JOIN payment P ON p.booking_id =b.booking_id WHERE b.date=CURDATE())");
        } else {
            resultSet = CrudUtil.crudUtil("SELECT MaX( b.booking_end)  FROM booking b WHERE b.date=CURDATE()  AND b.booking_id NOT IN (SELECT p.booking_id FROM booking b INNER JOIN payment P ON p.booking_id =b.booking_id WHERE b.date=CURDATE())");
        }
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            String sql = "SELECT MAX(b.booking_end) FROM booking b RIGHT JOIN payment P ON p.booking_id =b.booking_id WHERE b.date=CURDATE()";
            resultSet = CrudUtil.crudUtil(sql);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }

        }
        return "0";
    }

    /**
     * Attendance
     *
     * */
    public List<AttendanceProjection> findByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.street,e.lane,e.city,e.contact,a.date,a.in_time,a.out_time,e.role from attendance a INNER JOIN employee e on e.nic = a.nic where a.date=?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, date);
        return toCustomer(resultSet);

    }

    public List<AttendanceProjection> findByDateAndNameAndNic(String dateNow, String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.street,e.lane,e.city,e.contact,a.date,a.in_time,a.out_time from attendance a INNER JOIN employee e on e.nic = a.nic where a.date=? AND e.first_name LIKE ? OR e.last_name LIKE ? OR e.nic LIKE ?";
        String args = text + "%";
        ResultSet resultSet = CrudUtil.crudUtil(sql, dateNow, args, args, args);
        return toCustomer(resultSet);
    }

    public List<AttendanceProjection> toCustomer(ResultSet resultSet) throws SQLException {
        List<AttendanceProjection> tmList = new ArrayList<>();
        while (resultSet.next()) {
            AttendanceProjection attendanceProjection = new AttendanceProjection();
            attendanceProjection.setNic(resultSet.getString(1));
            attendanceProjection.setName(resultSet.getString(2) + " " + resultSet.getString(3) + "( " + resultSet.getString(11) + " )");
            attendanceProjection.setAddress(resultSet.getString(4) + ", " + resultSet.getString(5) + ", " + resultSet.getString(6));
            attendanceProjection.setContact(resultSet.getString(7));
            attendanceProjection.setDate(resultSet.getString(8));
            attendanceProjection.setInTime(resultSet.getString(9));
            attendanceProjection.setOutTime(resultSet.getString(10));

            tmList.add(attendanceProjection);
        }
        return tmList;
    }

    /**
     * Employee
     * */
    public List<Employee> findEmployeeAvailable() throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic, e.first_name, e.last_name, e.email, e.city, e.lane, e.street, e.contact, e.dateOfBirth, e.gender, e.role FROM attendance a INNER JOIN employee e ON e.nic= a.nic WHERE a.date=CURDATE() AND a.nic NOT IN  (SELECT bd.nic FROM booking b INNER JOIN booking_details bd on b.booking_id = bd.booking_id WHERE b.date= CURDATE() AND b.booking_id NOT IN (SELECT b.booking_id FROM booking b RIGHT JOIN payment p on b.booking_id = p.booking_id WHERE b.date= CURDATE())) AND e.role NOT IN ('ADMIN','CASHIER')";
        ResultSet result = CrudUtil.crudUtil(sql);
        return toEntities(result);
    }

    public String CountAvailable() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM attendance a INNER JOIN employee e ON  e.nic=a.nic WHERE NOT e.role='ADMIN' OR e.role ='CASHIER' AND date = CURDATE() AND NOT e.nic=(SELECT bd.nic FROM booking b LEFT JOIN payment p on b.booking_id = p.booking_id INNER JOIN booking_details bd on b.booking_id = bd.booking_id WHERE b.date= CURDATE())";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public  List<Employee> toEntities(ResultSet result) throws SQLException {
        List<Employee> list = new ArrayList<>();

        while (result.next()) {
            Employee employee = new Employee();
            employee.setNic(result.getString(1));
            employee.setFirst_name(result.getString(2));
            employee.setLast_name(result.getString(3));
            employee.setEmail(result.getString(4));
            employee.setCity(result.getString(5));
            employee.setLane(result.getString(6));
            employee.setStreet(result.getString(7));
            employee.setContact(result.getString(8));
            employee.setDateOfBirth(LocalDate.parse(result.getString(9)));
            employee.setGender(result.getString(10));
            list.add(employee);
        }
        return list;
    }

    /**
     * Customer Order
     * */
    public List<CustomerOrderProjecton> toCustomerOrderProjection(ResultSet resultSet) throws SQLException {
        List<CustomerOrderProjecton> list = new ArrayList<>();
        while (resultSet.next()) {
            CustomerOrderProjecton customerOrderProjecton = new CustomerOrderProjecton();
            customerOrderProjecton.setCustomerOrderId(resultSet.getString(1));
            customerOrderProjecton.setCustomerId(resultSet.getString(2));
            customerOrderProjecton.setName(resultSet.getString(3) + " " + resultSet.getString(4));
            customerOrderProjecton.setDate(resultSet.getString(5));
            customerOrderProjecton.setTime(resultSet.getString(6));
            customerOrderProjecton.setTotal(resultSet.getString(7));
            customerOrderProjecton.setCount(resultSet.getString(8));
            list.add(customerOrderProjecton);
        }
        return list;
    }

    public List<CustomerOrderProjecton> findCustomerAndOrdersByDate(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT co.cust_oid,c.cust_id,c.first_name,c.last_name,co.date,co.time,co.total,COUNT(od.cust_oid) FROM customer_order co inner join customer c on co.cust_id = c.cust_id inner join order_details od on co.cust_oid = od.cust_oid where co.date=?", date);
        return toCustomerOrderProjection(resultSet);
    }

    public List<CustomerOrderProjecton> findCustomerAndOrdersByLike(String text) throws SQLException, ClassNotFoundException {
        String arg = text + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT co.cust_oid,c.cust_id,c.first_name,c.last_name,co.date,co.time,co.total,COUNT(od.cust_oid) FROM customer_order co inner join customer c on co.cust_id = c.cust_id inner join order_details od on co.cust_oid = od.cust_oid where co.cust_oid LIKE ?  or c.first_name LIKE ? or c.last_name LIKE ? or co.date LIKE ? or co.time LIKE ? or co.cust_id LIKE ? GROUP BY co.cust_oid;", arg, arg, arg, arg, arg, arg);
        return toCustomerOrderProjection(resultSet);
    }

    public List<CustomerOrderProjecton> findCustomerAndOrders() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT co.cust_oid,c.cust_id,c.first_name,c.last_name,co.date,co.time,co.total,COUNT(od.cust_oid) FROM customer_order co inner join customer c on co.cust_id = c.cust_id inner join order_details od on co.cust_oid = od.cust_oid;");
        return toCustomerOrderProjection(resultSet);
    }

    public List<CustomerOrderDetailsProjection> findCustomerAndOrderDetailsByOrderId(String customerOrderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT o.product_code, p.product, o.price, o.qty from order_details o inner join product p on o.product_code=p.product_code where cust_oid=?", customerOrderId);
        return toCustomerOrderDetailsProjection(resultSet);
    }

    public List<CustomerOrderDetailsProjection> toCustomerOrderDetailsProjection(ResultSet resultSet) throws SQLException {
        List<CustomerOrderDetailsProjection> list = new ArrayList<>();
        while (resultSet.next()) {
            CustomerOrderDetailsProjection customerOrderDetailsProjection = new CustomerOrderDetailsProjection();
            customerOrderDetailsProjection.setCode(resultSet.getString(1));
            customerOrderDetailsProjection.setProduct(resultSet.getString(2));
            customerOrderDetailsProjection.setPrice(resultSet.getString(3));
            customerOrderDetailsProjection.setQty(resultSet.getString(4));
            list.add(customerOrderDetailsProjection);
        }
        return list;
    }

    /**
     * Booking
     * */

    public List<HomeBookingProjection> findNameTimeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT CONCAT(c.first_name,' ',c.last_name) as name,b.booking_start,b.booking_id FROM booking b LEFT JOIN payment p on b.booking_id = p.booking_id INNER JOIN customer c ON b.cust_id = c.cust_id WHERE b.date= CURDATE() AND b.booking_id NOT IN (SELECT b.booking_id FROM booking b RIGHT JOIN payment p on b.booking_id = p.booking_id WHERE b.date= CURDATE())");
        return toHomeTm(resultSet);
    }

    public List<HomeBookingProjection> findNameTimeId(String txt) throws SQLException, ClassNotFoundException {
        String args = txt + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT CONCAT(c.first_name,' ',c.last_name) as name,b.booking_start,b.booking_id FROM booking b LEFT JOIN payment p on b.booking_id = p.booking_id INNER JOIN customer c ON b.cust_id = c.cust_id WHERE b.date= CURDATE() AND b.booking_id NOT IN (SELECT b.booking_id FROM booking b RIGHT JOIN payment p on b.booking_id = p.booking_id WHERE b.date= CURDATE()) and c.first_name LIKE ? or c.last_name LIKE ? or b.booking_start LIKE ?",
                args, args, args
        );
        return toHomeTm(resultSet);
    }

    public List<HomeBookingProjection> toHomeTm(ResultSet resultSet) throws SQLException {
        List<HomeBookingProjection> list = new ArrayList<>();
        while (resultSet.next()) {
            HomeBookingProjection homeBookingProjection = new HomeBookingProjection();
            homeBookingProjection.setName(resultSet.getString(1));
            homeBookingProjection.setTime(resultSet.getString(2));
            homeBookingProjection.setId(resultSet.getString(3));

            list.add(homeBookingProjection);
        }
        return list;
    }

    public String CompliedCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM booking b RIGHT JOIN payment p on b.booking_id = p.booking_id WHERE b.date= CURDATE()";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public List<BookingProjection> findBooking() throws SQLException, ClassNotFoundException {
        String sql = "SELECT b.booking_id,c.cust_id,c.first_name,c.last_name,b.date,b.time,b.total,b.booking_start,b.booking_end FROM booking b  INNER JOIN customer c on b.cust_id = c.cust_id";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        return toTms(resultSet);
    }

    public List<BookingProjection> findBookingByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT b.booking_id,c.cust_id,c.first_name,c.last_name,b.date,b.time,b.total,b.booking_start,b.booking_end FROM booking b  INNER JOIN customer c on b.cust_id = c.cust_id WHERE b.date=?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, date);
        return toTms(resultSet);
    }

    public List<BookingProjection> toTms(ResultSet resultSet) throws SQLException {
        List<BookingProjection> list = new ArrayList<>();
        while (resultSet.next()) {
            BookingProjection bookingProjection = new BookingProjection();
            bookingProjection.setBookingId(resultSet.getString(1));
            bookingProjection.setCusId(resultSet.getString(2));
            bookingProjection.setCustomer(resultSet.getString(3) + " " + resultSet.getString(4));
            bookingProjection.setDate(resultSet.getString(5));
            bookingProjection.setTime(resultSet.getString(6));
            bookingProjection.setTotal(resultSet.getString(7));
            bookingProjection.setBookingStart(resultSet.getString(8));
            bookingProjection.setBookingEnd(resultSet.getString(9));
            list.add(bookingProjection);
        }
        return list;
    }

    public List<BookingDetailsProjection> findBookingDetailsByBookingId(String bookingId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT t.treatment,e.first_name,e.last_name,e.nic,b.charge  FROM booking_details b JOIN booking b2 on b.booking_id = b2.booking_id INNER JOIN employee e on b.nic = e.nic INNER JOIN treatment t on b.treat_id = t.treat_id WHERE b.booking_id=?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, bookingId);
        return toDetailsTms(resultSet);
    }

    public List<BookingDetailsProjection> toDetailsTms(ResultSet resultSet) throws SQLException {
        List<BookingDetailsProjection> list = new ArrayList<>();
        while (resultSet.next()) {
            BookingDetailsProjection projection = new BookingDetailsProjection();
            projection.setTreatment(resultSet.getString(1));
            projection.setEmployee(resultSet.getString(2) + " " + resultSet.getString(3));
            projection.setNic(resultSet.getString(4));
            projection.setCharge(resultSet.getString(5));
            list.add(projection);
        }
        return list;
    }

}
