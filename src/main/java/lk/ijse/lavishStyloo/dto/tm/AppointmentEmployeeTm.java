package lk.ijse.lavishStyloo.dto.tm;

import javafx.scene.control.RadioButton;
import lk.ijse.lavishStyloo.controller.Cashier.BookingEmployeeFromController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/13/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AppointmentEmployeeTm {
    private String nic;
    private String employee;
    private String status;
    private RadioButton choose;

    public RadioButton getChoose() {
        RadioButton radioButton = new RadioButton();
        radioButton.setOnAction(actionEvent -> {
            BookingEmployeeFromController.empLis.add(this.nic);
            BookingEmployeeFromController.empLis.add(this.employee);

        });
        return choose==null?radioButton:choose;
    }
}
