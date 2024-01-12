package lk.ijse.lavishStyloo.dto.tm;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.lavishStyloo.controller.Cashier.BookingEmployeeFromController;
import lk.ijse.lavishStyloo.util.NavigationUtility;
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
public class AppointmentTm {
   private String treatment_id;
   private String treatment;
   private String amount;
   private String category;
   private String employee;
   private String nic;
   private Button choose;

    public Button getChoose() {
        Button button = new Button();
        button.setStyle(" -fx-background-radius: 20px;\n" +
                "\n" +
                "    -fx-background-color: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(56, 116, 141, 0.43),15,0,0,2);");
        Image image = new Image("view/assest/icon/icons8-choose-64.png");
        ImageView view = new ImageView(image);
        view.setFitWidth(17);
        view.setFitHeight(17);

        button.setGraphic(view);
        button.setOnAction(actionEvent -> {
            NavigationUtility.popupNavigation("Cashier/BookingEmployeeFrom.fxml");
            BookingEmployeeFromController.empLis.add(this.treatment_id);
        });
        return choose==null?button:choose;
    }
}
