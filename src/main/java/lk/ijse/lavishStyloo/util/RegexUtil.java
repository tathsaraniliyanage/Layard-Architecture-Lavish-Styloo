package lk.ijse.lavishStyloo.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean checkFinalResult(ArrayList<String> regex, JFXButton button, JFXTextField... textFields) {

        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].getText().equals("") | !regex(button, textFields[i], regex.get(i))) {
                textFields[i].requestFocus();
                textFields[i].setFocusColor(Paint.valueOf("red"));
                button.setDisable(true);
                return false;
            }
        }
        return true;
    }

    public static boolean regex(JFXButton button, JFXTextField txt, String regex, String style) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(txt.getText());
        if (matcher.matches()) {
            txt.setFocusColor(Paint.valueOf("transparent"));
            txt.setStyle("-fx-text-fill: black");
            button.setDisable(false);
        } else {
            button.setDisable(true);
            txt.setStyle("-fx-text-fill: ReD");
        }
        return matcher.matches();
    }

    public static boolean regex(JFXButton button, JFXTextField textField, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textField.getText());
        if (matcher.matches()) {
            textField.requestFocus();
            button.setDisable(false);
        } else {
            button.setDisable(true);
        }
        return matcher.matches();
    }
}
