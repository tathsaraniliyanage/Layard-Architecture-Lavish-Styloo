package lk.ijse.lavishStyloo.dto.tm;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.bo.custom.TreatmentBO;
import lk.ijse.lavishStyloo.controller.Admin.EmployeeChildFromController;
import lk.ijse.lavishStyloo.controller.Admin.ProductFromController;
import lk.ijse.lavishStyloo.controller.Admin.TreatmentFromController;
import lk.ijse.lavishStyloo.util.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.SQLException;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/23/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RemoveButton {
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    TreatmentBO treatmentBO = (TreatmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TREATMENT);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    private Button remove;
    private TreatmentTm treatmentTm;
    private ProductTm productTm;
    private EmployeeTm employeeTm;

    public void setTm(TreatmentTm tm) {
        this.treatmentTm = tm;
    }

    public Button getRemove() {

        /**
         *create new java fx button
         * */
        Button button = new Button();
        /**
         *created button to add  css style
         * */
        button.setStyle(" -fx-background-radius: 20px;\n" +
                "\n" +
                "    -fx-background-color: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(56, 116, 141, 0.43),15,0,0,2);");
        /**
         *create new image in java to load image
         * */
        Image image = new Image("view/assest/icon/icons8-remove-24 (2).png");
        /**
         *create new image view in java fx and set a image in image view constructor to load image
         * */
        ImageView view = new ImageView(image);
        /**
         *created  image view set height and width in size 17*17 box type
         * */
        view.setFitHeight(17);
        view.setFitWidth(17);

        /**
         *created  button to set created image view in this method using
         * */
        button.setGraphic(view);
        /**
         *created  button set in customer tm object to load all cel table view
         * */

        button.setOnAction(actionEvent -> {
            try {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are your Sure ?", ButtonType.NO, ButtonType.YES);
                alert.showAndWait();
                if (alert.getResult().equals(ButtonType.YES)) {


                    if (treatmentTm != null) {
                        boolean delete = treatmentBO.delete(treatmentTm.getId());
                        if (delete) {
                            Notification.notification("DELETE", "Remove successfully");
                            TreatmentFromController.getController().loadAllTreatment();
                        } else {
                            Notification.notificationWARNING("DELETE", "Something Wong");
                        }
                    } else if (productTm != null) {
                        boolean delete = productBO.delete(productTm.getProduct_code());
                        if (delete) {
                            Notification.notification("DELETE", "Remove successfully");
                            ProductFromController.getController().loadAllProduct();
                        } else {
                            Notification.notificationWARNING("DELETE", "Something Wong");
                        }
                    } else if (employeeTm != null) {
                        //System.out.println(employeeTm.getId());
                        boolean delete = employeeBO.delete(employeeTm.getId());
                        if (delete) {
                            Notification.notification("DELETE", "Remove successfully");
                            EmployeeChildFromController.getController().loadAllEmployee();
                        } else {
                            Notification.notificationWARNING("DELETE", "Something Wong");
                        }
                    }

                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }


        });
        return remove == null ? button : remove;
    }
}
