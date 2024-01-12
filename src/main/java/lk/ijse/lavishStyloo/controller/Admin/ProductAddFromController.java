package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.ProductBO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.Notification;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductAddFromController {
    public JFXTextField txtProduct;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public ImageView image;
    public JFXButton btnAdd;

    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);


    public void AddOnAction(ActionEvent actionEvent) {
        Image image = this.image.getImage();
        if (isCheckFinal() && image != null) {
            ProductDTO dto = new ProductDTO();
            dto.setProduct(txtProduct.getText());
            dto.setQty("0");
            dto.setDescription(txtDescription.getText());
            dto.setUnit_price(txtPrice.getText());
            dto.setImg(getNextID() + ".png");
            dto.setProduct_code(getNextID());
            try {
                boolean isSave = productBO.save(dto);
                if (isSave) {
                    closeOnAction(actionEvent);
                    ProductFromController.getController().loadAllProduct();
//                new Alert(Alert.AlertType.CONFIRMATION,"ok").show();
                    Notification.notification("Product", "Process Completely successful");

                } else {
//                new Alert(Alert.AlertType.CONFIRMATION,"not").show();
                    Notification.notificationWARNING("Product", "Process Something Wrong");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Notification.notificationWARNING("Product", "Check All Fields");
        }

    }

    private boolean isCheckFinal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");

        return RegexUtil.checkFinalResult(list, btnAdd,
                txtPrice,
                txtDescription,
                txtProduct);

    }

    private String getNextID() {
        try {
            return productBO.nextId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    public void ImageAddOnAction(ActionEvent actionEvent) {
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open a file");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.png"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.getPath());
                this.image.setImage(image);
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                String name = selectedFile.getName();
                System.out.println(name);
                String[] split = name.split("\\.");
                System.out.println(Arrays.toString(split));
                String extenuation = split[split.length - 1];
                Path path = Paths.get("C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\imgAsset\\" + getNextID() + "." + extenuation);
                Files.write(path, bytes);
            } else {
                System.out.println("No file has been selected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void priceOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtPrice, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: black");
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtDescription, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }

    public void productOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtProduct, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }
}
