package lk.ijse.lavishStyloo.dto.tm;

import javafx.scene.control.RadioButton;
import lk.ijse.lavishStyloo.controller.Cashier.BookingFromController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/13/2023
 */
@AllArgsConstructor
@Data
@ToString
public class TreatmentTm extends RemoveButton {
    protected String id;
    private String price;
    private String category;
    private String treatment;
    private String description;
    private RadioButton tick;

    public TreatmentTm() {
        super.setTm(this);
    }

    public RadioButton getTick() {
        RadioButton radioButton = new RadioButton();
        radioButton.setOnAction(actionEvent -> {
            if (radioButton.isSelected()){

                AppointmentTm appointmentTm = new AppointmentTm();
                appointmentTm.setTreatment_id(getId());
                appointmentTm.setTreatment(getTreatment());
                appointmentTm.setAmount(getPrice());
                appointmentTm.setCategory(getCategory());
                appointmentTm.setEmployee("Choose Employee");

                BookingFromController.getInstance().setData(appointmentTm);
            }
        });
        return tick==null?radioButton:tick;
    }
}
