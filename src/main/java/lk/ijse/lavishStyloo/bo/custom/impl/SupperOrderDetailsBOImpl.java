package lk.ijse.lavishStyloo.bo.custom.impl;

import lk.ijse.lavishStyloo.bo.custom.SupperOrderDetailsBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.QueryDAO;
import lk.ijse.lavishStyloo.dto.projection.SupperOrderDetailsProjection;
import lk.ijse.lavishStyloo.dto.tm.SupperOrderDetailsTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project Lavish_Styloo
 * @Date 24/12/2023
 */

public class SupperOrderDetailsBOImpl implements SupperOrderDetailsBO {

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public List<SupperOrderDetailsTm> findCustomerOrderDetailsByOrderId(String supperOrderId) throws SQLException, ClassNotFoundException {
        return toTm(queryDAO.findCustomerOrderDetailsByOrderId(supperOrderId));
    }

    private List<SupperOrderDetailsTm> toTm(List<SupperOrderDetailsProjection> p) {
        ArrayList<SupperOrderDetailsTm> list = new ArrayList<>();
        for (SupperOrderDetailsProjection projection : p) {
            list.add(projection.toTM());
        }
        return list;
    }
}
