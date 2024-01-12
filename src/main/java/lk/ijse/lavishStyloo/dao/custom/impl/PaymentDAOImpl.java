package lk.ijse.lavishStyloo.dao.custom.impl;

import lk.ijse.lavishStyloo.dao.CrudUtil;
import lk.ijse.lavishStyloo.dao.custom.PaymentDAO;
import lk.ijse.lavishStyloo.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */
public class PaymentDAOImpl implements PaymentDAO {
    public List<String> findId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT payment_id FROM payment ORDER BY LENGTH(payment_id),payment_id");
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO  payment VALUES (?,?,?,?,?)",
                payment.getPayment_id(),
                payment.getNet_payment(),
                payment.getDate(),
                payment.getTime(),
                payment.getBooking_id()
        );
    }

    public String nextID() throws SQLException, ClassNotFoundException {
        List<String> ids = findId();
        String oldId = null;
        for (String s : ids) {
            oldId = s;
        }
        int lastIndex;
        try {
            String[] split = oldId.split("P00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "P001";
        }
        lastIndex++;
        return "P00" + lastIndex;
    }
}
