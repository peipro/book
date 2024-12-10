package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class    index extends Application {
    Bookscene bookscene;
    BorrowScene borrowscene;
    @Override
    public void start(Stage primaryStage) {
        // 创建提示框（使用 Label）
        Label promptLabel = new Label("欢迎使用图书借阅服务");
        promptLabel.setFont(Font.font("Arial", 20));
        promptLabel.setTextFill(Color.DARKBLUE);
        promptLabel.setStyle("-fx-padding: 20px; -fx-background-color: #f0f8ff; -fx-border-radius: 10px; -fx-border-color: #4682b4;");

        // 设置提示框在 VBox 中居中显示
        promptLabel.setAlignment(Pos.CENTER);

        // 创建按钮：查阅书籍信息
        Button checkBookInfoButton = new Button("查阅书籍信息");
        checkBookInfoButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        checkBookInfoButton.setOnAction(e -> showBookInfoAlert());

        // 创建按钮：查阅借阅信息
        Button checkBorrowInfoButton = new Button("查阅借阅信息");
        checkBorrowInfoButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        checkBorrowInfoButton.setOnAction(e -> showBorrowInfoAlert());

        // 将两个按钮放置在一个 HBox 中，使它们水平排列
        HBox buttonsBox = new HBox(40);  // 设置按钮之间的间距为 20
        buttonsBox.setAlignment(Pos.CENTER);  // 居中对齐
        buttonsBox.getChildren().addAll(checkBookInfoButton, checkBorrowInfoButton);

        // 使用 VBox 布局，垂直排列提示框和按钮
        VBox vbox = new VBox(50);  // 设置垂直间距为 30
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);  // 设置 VBox 中的所有元素居中
        vbox.getChildren().addAll(promptLabel, buttonsBox);

        // 创建场景
        Scene scene = new Scene(vbox, 500, 300);

        // 设置窗口标题和场景
        primaryStage.setTitle("图书借阅服务");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 弹出提示框，显示图书信息
    private void showBookInfoAlert() {
        bookscene=new Bookscene();
    }

    // 弹出提示框，显示借阅信息
    private void showBorrowInfoAlert() {
       borrowscene=new BorrowScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}