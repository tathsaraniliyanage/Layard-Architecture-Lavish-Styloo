package lk.ijse.lavishStyloo.bo;

import lk.ijse.lavishStyloo.bo.custom.impl.*;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public lk.ijse.lavishStyloo.bo.SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case BOOKING:
                return new BookingBOImpl();
            case BOOKING_DETAIL:
                return new BookingDetailsBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case CUSTOMER_ORDER:
                return new CustomerOrderBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case SUPPLIER_ORDER:
                return new SupperOrderBOImpl();
            case SUPPLIER_ORDER_DETAIL:
                return new SupperOrderDetailsBOImpl();
            case TREATMENT:
                return new TreatmentBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        ATTENDANCE, BOOKING, BOOKING_DETAIL, CUSTOMER, CUSTOMER_ORDER, EMPLOYEE, PAYMENT, PRODUCT, SALARY, SUPPLIER, SUPPLIER_ORDER, SUPPLIER_ORDER_DETAIL, ORDER_DETAIL, TREATMENT, USER, QUERY
    }
}
