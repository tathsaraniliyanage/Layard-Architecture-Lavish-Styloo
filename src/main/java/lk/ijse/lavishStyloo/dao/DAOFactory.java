package lk.ijse.lavishStyloo.dao;

import lk.ijse.lavishStyloo.dao.custom.impl.*;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case BOOKING:
                return new BookingDAOImpl();
            case BOOKING_DETAIL:
                return new BookingDetailsDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case CUSTOMER_ORDER:
                return new CustomerOrderDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case SUPPLIER_ORDER:
                return new SupperOrderDAOImpl();
            case SUPPLIER_ORDER_DETAIL:
                return new SupperOrderDetailsDAOImpl();
            case TREATMENT:
                return new TreatmentDAOImpl();
            case USER:
                return new UserDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        ATTENDANCE, BOOKING, BOOKING_DETAIL, CUSTOMER, CUSTOMER_ORDER, EMPLOYEE, PAYMENT, PRODUCT, SALARY, SUPPLIER, SUPPLIER_ORDER, SUPPLIER_ORDER_DETAIL, ORDER_DETAIL, TREATMENT, USER, QUERY
    }
}
