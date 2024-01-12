package lk.ijse.lavishStyloo.util;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class Notification {
    public static void notification(String title,String message) {
        TrayNotification notification=new TrayNotification();
        AnimationType type=AnimationType.POPUP;
        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.millis(300));
    }
    public static void notificationWARNING(String title,String message) {
        TrayNotification notification=new TrayNotification();
        AnimationType type=AnimationType.POPUP;
        notification.setAnimationType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(NotificationType.WARNING);
        notification.showAndDismiss(Duration.millis(3000));
    }
}
