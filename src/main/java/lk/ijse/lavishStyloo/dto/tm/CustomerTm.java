package lk.ijse.lavishStyloo.dto.tm;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.controller.Cashier.CustomerFromController;
import lombok.*;

import java.sql.SQLException;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/5/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CustomerTm {
    CustomerBO customerBo = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    private String colId;
    private String colName;
    private String colAddress;
    private String colMail;
    private String colContact;
    private Button colAction;

    public Button getColAction() {

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
                    boolean delete = customerBo.delete(this.colId);
                    if (delete) {
                        CustomerFromController.controller().loadAllCustomer();
                    }

                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }


        });
        return colAction == null ? button : colAction;
    }
}
