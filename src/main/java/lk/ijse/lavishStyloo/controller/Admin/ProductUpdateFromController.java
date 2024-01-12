package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductUpdateFromController implements Initializable {
    private static String imgName;
    public Text txtCode;
    public JFXTextField txtProduct;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public ImageView image;
    public JFXButton btn;

    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);


    private boolean isCheckFinal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");

        return RegexUtil.checkFinalResult(list, btn,
                txtPrice,
                txtDescription,
                txtProduct);
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        if (isCheckFinal() && imgName != null) {
            imgName = null;
            ProductDTO dto = new ProductDTO();
            dto.setProduct(txtProduct.getText());
            dto.setDescription(txtDescription.getText());
            dto.setUnit_price(txtPrice.getText());
            dto.setImg(txtCode.getText() + ".png");
            dto.setProduct_code(txtCode.getText());
            try {
                boolean isSave = productBO.update(dto);
                if (isSave) {
                    closeOnAction(actionEvent);
                    ProductFromController.getController().loadAllProduct();
                    ProductFromController.getController().btnClear.setVisible(false);
                    ProductFromController.getController().btnText.setText("NEW");
                    Notification.notification("Product", "Process Completely successful");
                } else {
//                new Alert(Alert.AlertType.CONFIRMATION, "not").show();
                    Notification.notificationWARNING("Product", "Process Something Wrong");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Notification.notificationWARNING("Product", "Check All Fields");
        }

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
                imgName = name;
                String[] split = name.split("\\.");
                // System.out.println(Arrays.toString(split));
                String extenuation = split[split.length - 1];
                Path path = Paths.get("C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\imgAsset\\" + txtCode.getText() + "." + extenuation);
                Files.write(path, bytes);
            } else {
                System.out.println("No file has been selected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProductById(ProductFromController.product_code);
    }

    private void loadProductById(String product_code) {
        try {
            ProductDTO product = productBO.findProductByCode(product_code);
            txtCode.setText(product.getProduct_code());
            txtProduct.setText(product.getProduct());
            txtPrice.setText(product.getUnit_price());
            txtDescription.setText(product.getDescription());
            Image img = new Image("/imgAsset/" + product.getImg());
            image.setImage(img);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void priceOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtPrice, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: black");
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtDescription, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }

    public void productOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtProduct, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }
}
