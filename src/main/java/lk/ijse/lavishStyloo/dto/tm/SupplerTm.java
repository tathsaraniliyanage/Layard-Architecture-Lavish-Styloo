package lk.ijse.lavishStyloo.dto.tm;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.SupplierBO;
import lk.ijse.lavishStyloo.controller.Admin.SupplerManageFromController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.SQLException;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/22/2023
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class SupplerTm {
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    private String supplier_id;
    private String supplier_name;
    private String company;
    private String email;
    private String contact;
    private String location;
    private Button button;

    public Button getButton() {

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
                    boolean delete = supplierBO.delete(this.supplier_id);
                    if (delete) {
                        SupplerManageFromController.getController().setTableData();
                    }

                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }


        });
        return this.button == null ? button : this.button;
    }

}
