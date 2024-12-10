package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import model.Borrow;
import service.BorrowService;
import util.CheckDate;
import util.Dateutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author pei
 * @version 1.0
 * 2024/12/9
 */
public class BorrowScene {
    Stage stage=new Stage();
    BorrowService borrow=BorrowService.getInstance();
    Borrow borrow2;
    public BorrowScene(){
        VBox leftMenu = new VBox(40);
        leftMenu.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
        StackPane rightContent = new StackPane();
        rightContent.setStyle("-fx-padding: 10; -fx-background-color: #ffffff;");

        Button button1=new Button("查看所有借书情况");
        button1.setOnAction(e->updateContent(rightContent,1));
        Button button2=new Button("根据书的序号查看图书的借阅情况");
        button2.setOnAction(e->updateContent(rightContent,2));
        Button button3=new Button("根据读者姓名查看该读者的借阅情况");
        button3.setOnAction(e->updateContent(rightContent,3));
        Button button4=new Button("根据日期查看当天的借阅情况");
        button4.setOnAction(e->updateContent(rightContent,4));
        Button button5=new Button("新增借书记录");
        button5.setOnAction(e->updateContent(rightContent,5));
        Button button6=new Button("删除借书记录");
        button6.setOnAction(e->updateContent(rightContent,6));
        Button button7=new Button("更新借书记录");
        button7.setOnAction(e->updateContent(rightContent,7));

        leftMenu.getChildren().addAll(button1,button2,button3,button4,button5,button6,button7);
        SplitPane splitPane=new SplitPane();
        splitPane.getItems().addAll(leftMenu,rightContent);
        splitPane.setDividerPositions(0.25);

        Scene scene =new Scene(splitPane,1000,450);
        stage.setScene(scene);
        stage.setTitle("借阅记录业务操作");
        stage.show();
    }
    public void updateContent(StackPane contentPane,int i){
        contentPane.getChildren().clear();
        switch(i){
            case 1://得到所有借阅记录的信息
                update1(contentPane);
                break;
            case 2://根据书的序号查询借阅记录
                update2(contentPane);
                break;
            case 3://根据读者姓名查询借阅记录
                update3(contentPane);
                break;
            case 4://根据日期查看当天的借阅记录
                update4(contentPane);
                break;
            case 5://新增借阅记录
                update5(contentPane);
                break;
            case 6://删除借阅记录
                update6(contentPane);
                break;
            case 7://更新借阅记录
                update7(contentPane);
                break;
        }
    }
    public void update1(StackPane contentPane){
        TableView<Borrow> tableView=new TableView<>();

        TableColumn<Borrow,String> datecolumn=new TableColumn<>("借书日期");
        datecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("borrow_date"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> idcolumn=new TableColumn<>("书籍序列号");
        idcolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("book_id"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> namecolumn=new TableColumn<>("读者姓名");
        namecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("reader_name"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,Integer> quantitycolumn=new TableColumn<>("借阅数量");
        quantitycolumn.setCellValueFactory(new PropertyValueFactory<Borrow,Integer>("borrow_quantity"));
        datecolumn.setMinWidth(150);

        tableView.getColumns().addAll(datecolumn,idcolumn,namecolumn,quantitycolumn);
        List<Borrow> borrows=borrow.queryAllBorrow();
        ObservableList<Borrow> borrowslist = FXCollections.observableArrayList(borrows);
        tableView.setItems(borrowslist);
        contentPane.getChildren().add(tableView);
    }
    public void update2(StackPane contentPane){
        TableView<Borrow> tableView=new TableView<>();
        TextField idfield = new TextField();
        idfield.setPromptText("请输入书的序列号");
        Button searchbutton = new Button("查询");

        TableColumn<Borrow,String> datecolumn=new TableColumn<>("借书日期");
        datecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("borrow_date"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> idcolumn=new TableColumn<>("书籍序列号");
        idcolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("book_id"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> namecolumn=new TableColumn<>("读者姓名");
        namecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("reader_name"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,Integer> quantitycolumn=new TableColumn<>("借阅数量");
        quantitycolumn.setCellValueFactory(new PropertyValueFactory<Borrow,Integer>("borrow_quantity"));
        datecolumn.setMinWidth(150);

        tableView.getColumns().addAll(datecolumn,idcolumn,namecolumn,quantitycolumn);
        HBox hbox = new HBox(idfield, searchbutton);
        searchbutton.setOnAction(e -> {
            handleSearchButtonClickID(idfield, tableView);
        });
        VBox box=new VBox(hbox,tableView);

        contentPane.getChildren().add(box);
    }
    public void update3(StackPane contentPane){
        TableView<Borrow> tableView=new TableView<>();
        TextField namefield = new TextField();
        namefield.setPromptText("请输入借书人的名称");
        Button searchbutton = new Button("查询");

        TableColumn<Borrow,String> datecolumn=new TableColumn<>("借书日期");
        datecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("borrow_date"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> idcolumn=new TableColumn<>("书籍序列号");
        idcolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("book_id"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> namecolumn=new TableColumn<>("读者姓名");
        namecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("reader_name"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,Integer> quantitycolumn=new TableColumn<>("借阅数量");
        quantitycolumn.setCellValueFactory(new PropertyValueFactory<Borrow,Integer>("borrow_quantity"));
        datecolumn.setMinWidth(150);

        tableView.getColumns().addAll(datecolumn,idcolumn,namecolumn,quantitycolumn);
        HBox hbox = new HBox(namefield, searchbutton);
        searchbutton.setOnAction(e -> {
            handleSearchButtonClickName(namefield, tableView);
        });
        VBox box=new VBox(hbox,tableView);

        contentPane.getChildren().add(box);
    }
    public void update4(StackPane contentPane){
        TableView<Borrow> tableView=new TableView<>();
        TextField datefield = new TextField();
        datefield.setPromptText("请输入查询的日期(形式如:xxxx-xx-xx)");
        Button searchbutton = new Button("查询");

        TableColumn<Borrow,String> datecolumn=new TableColumn<>("借书日期");
        datecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("borrow_date"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> idcolumn=new TableColumn<>("书籍序列号");
        idcolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("book_id"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,String> namecolumn=new TableColumn<>("读者姓名");
        namecolumn.setCellValueFactory(new PropertyValueFactory<Borrow,String>("reader_name"));
        datecolumn.setMinWidth(150);
        TableColumn<Borrow,Integer> quantitycolumn=new TableColumn<>("借阅数量");
        quantitycolumn.setCellValueFactory(new PropertyValueFactory<Borrow,Integer>("borrow_quantity"));
        datecolumn.setMinWidth(150);

        tableView.getColumns().addAll(datecolumn,idcolumn,namecolumn,quantitycolumn);
        HBox hbox = new HBox(datefield, searchbutton);
        searchbutton.setOnAction(e -> {
            handleSearchButtonClickDate(datefield, tableView);
        });
        VBox box=new VBox(hbox,tableView);

        contentPane.getChildren().add(box);
    }
    public void update5(StackPane contentPane){
        Label laber1 = new Label("书籍的序列号");
        Label laber2 = new Label("读者的名字");
        Label laber3 = new Label("借书的日期");
        Label laber4 = new Label("借阅的数量");

        TextField namefield = new TextField();
        TextField idfield = new TextField();
        Button insert = new Button("确定插入信息");
        TextField datefield = new TextField();
        TextField quantityfield = new TextField();

        HBox box1=new HBox(10,laber1,idfield);
        HBox box2=new HBox(10,laber2,namefield,insert);
        HBox box3=new HBox(10,laber3,datefield);
        HBox box4=new HBox(10,laber4,quantityfield);

        VBox box=new VBox(20,box1,box2,box3,box4);
        box.setPrefHeight(200); // 设置首选高度为 200
        box.setMinHeight(100);  // 设置最小高度为 100
        box.setMaxHeight(300);

        namefield.setPromptText("请在这里读者的名称");
        idfield.setPromptText("请在这里输入书籍的序列号");
        datefield.setPromptText("请在这里输入借阅的日期");
        quantityfield.setPromptText("请在这里输入借阅的数量");

        insert.setOnAction(e -> {
            handleInsertButton(namefield,idfield,datefield,quantityfield);
        });
        contentPane.getChildren().add(box);
    }
    public void update6(StackPane contentPane){
        Label laber1 = new Label("书籍的序列号");
        Label laber2 = new Label("读者名称");
        Label laber3 = new Label("借阅日期");
        Label laber5 = new Label("借阅量");

        TextField namefield = new TextField();
        TextField idfield = new TextField();
        TextField datefield = new TextField();
        Button search = new Button("查询借阅信息");
        TextField quantityfield = new TextField();
        Button delete = new Button("删除");

        HBox box1 = new HBox(10, laber3, datefield);
        HBox box2 = new HBox(10, laber1, idfield);
        HBox box3 = new HBox(10, laber2,  namefield, search);
        HBox box4 = new HBox(10, laber5, quantityfield);

        VBox box = new VBox(20, box1, box2, box3, box4,delete);
        box.setPrefHeight(200); // 设置首选高度为 200
        box.setMinHeight(100);  // 设置最小高度为 100
        box.setMaxHeight(300);

        namefield.setPromptText("在这里输入读者的名字");
        idfield.setPromptText("在这里输入书籍的序列号");
        datefield.setPromptText("在这里输入借阅的日期");
        search.setOnAction(e -> {handleSearchButtonClickName(namefield,idfield,datefield,quantityfield);});
        delete.setOnAction(e->{handleDeleteButton(namefield,idfield,datefield,quantityfield);});
        contentPane.getChildren().add(box);
    }
    public void update7(StackPane contentPane){
        Label laber1 = new Label("书籍的序列号");
        Label laber2 = new Label("读者名称");
        Label laber3 = new Label("借阅日期");
        Label laber5 = new Label("借阅量");

        TextField namefield = new TextField();
        TextField idfield = new TextField();
        TextField datefield = new TextField();
        Button search = new Button("查询借阅信息");
        TextField quantityfield = new TextField();
        Button delete = new Button("更新");

        HBox box1 = new HBox(10, laber3, datefield);
        HBox box2 = new HBox(10, laber1, idfield);
        HBox box3 = new HBox(10, laber2,  namefield, search);
        HBox box4 = new HBox(10, laber5, quantityfield);

        VBox box = new VBox(20, box1, box2, box3, box4,delete);
        box.setPrefHeight(200); // 设置首选高度为 200
        box.setMinHeight(100);  // 设置最小高度为 100
        box.setMaxHeight(300);

        namefield.setPromptText("在这里输入读者的名字");
        idfield.setPromptText("在这里输入书籍的序列号");
        datefield.setPromptText("在这里输入借阅的日期");
        search.setOnAction(e -> {borrow2=handleSearchButtonClickName(namefield,idfield,datefield,quantityfield);});
        delete.setOnAction(e->{handleUpdateButton(namefield,idfield,datefield,quantityfield);});
        contentPane.getChildren().add(box);
    }
    public void handleSearchButtonClickID(TextField idfield, TableView<Borrow> tableView){
        String id = idfield.getText();
        List<Borrow> borrowlist = borrow.get_borrowbyid(id);
        System.out.println(id);
        ObservableList<Borrow> borrows = FXCollections.observableArrayList(borrowlist);
        tableView.setItems(borrows);
    }
    public void handleSearchButtonClickName(TextField namefield, TableView<Borrow> tableView){
        String name = namefield.getText();
        List<Borrow> borrowlist = borrow.get_borrowbyname(name);
        System.out.println(name);
        ObservableList<Borrow> borrows = FXCollections.observableArrayList(borrowlist);
        tableView.setItems(borrows);
    }
    public Borrow handleSearchButtonClickName(TextField name,TextField id,TextField date,TextField quantity){
        String name1=name.getText();
        String id1=id.getText();
        String date1=date.getText();
        Borrow borrow3=null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!(name1 == null || name1.trim().isEmpty()
                || id1 == null || id1.trim().isEmpty()
                || date1 == null || date1.trim().isEmpty())){
            if(CheckDate.isValidDate(date1,"yyyy-MM-dd")) {
                Borrow borrow1 = borrow.getBorrow(name1, id1, date1);
                if (borrow.checkBorrow(borrow1)) {
                    quantity.setText(String.valueOf(borrow1.getBorrow_quantity()));
                    borrow3=new Borrow(id1,date1,Integer.parseInt(quantity.getText()),name1);
                }
            }else{
                alert.setTitle("提醒");
                alert.setHeaderText("注意");
                alert.setContentText("请注意您填写的日期格式");
                alert.showAndWait();
            }
        }else{
            alert.setTitle("提醒");
            alert.setHeaderText("注意");
            alert.setContentText("请注意需要填充的文本框");
            alert.showAndWait();
        }
        return borrow3;
    }

    public void handleSearchButtonClickDate(TextField datefield, TableView<Borrow> tableView){
        String date = datefield.getText();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        if(CheckDate.isValidDate(date,"yyyy-MM-dd")){
            List<Borrow> borrowlist = borrow.get_borrowbydate(date);
            System.out.println(date);
            ObservableList<Borrow> borrows = FXCollections.observableArrayList(borrowlist);
            tableView.setItems(borrows);
        }else{
            alert.setTitle("提醒");
            alert.setHeaderText("注意!");
            alert.setContentText("请您检查你的输入是否满足xxxx-xx-xx的格式");
            alert.showAndWait();
        }
    }
    public void handleInsertButton(TextField name,TextField id,TextField date,TextField quantity){
        String name1 = name.getText();
        String id1 = id.getText();
        String date1 = date.getText();
        String quantity1 = quantity.getText();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);

        if (!(name1.trim().isEmpty() || name1 == null
                || id1.trim().isEmpty() || id1 == null
                || date1 == null || date1.trim().isEmpty())){
            Borrow borrow1 = new Borrow();
            try{
                Integer.parseInt(quantity1);
                if(quantity1.matches("\\d+")){
                    if(borrow.lendbook(name1,date1,id1,Integer.parseInt(quantity1))){
                        alert.setTitle("提醒");
                        alert.setHeaderText("插入成功");
                        alert.setContentText("新的借阅记录已经成功插入");
                        alert.showAndWait();
                    }
                }
            }catch (NumberFormatException e){
                alert.setTitle("提醒");
                alert.setHeaderText("插入失败");
                alert.setContentText("请检查借阅数量是否是整数");
                alert.showAndWait();
            }
        }else{
            alert.setTitle("提醒");
            alert.setHeaderText("插入失败");
            alert.setContentText("请检查插入信息是否完整");
            alert.showAndWait();
        }
    }
    public void handleDeleteButton(TextField name,TextField id,TextField date,TextField quantity){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!(name.getText().trim().isEmpty() || name.getText() == null
                || id.getText().trim().isEmpty() || id.getText() == null
                || date.getText() == null || date.getText().trim().isEmpty()
                || quantity.getText() == null || quantity.getText().trim().isEmpty())){

            Borrow borrow1 = new Borrow(id.getText(), date.getText(), Integer.parseInt(quantity.getText()), name.getText());

                if(borrow.delete(borrow1)){
                    alert.setTitle("提醒");
                    alert.setHeaderText("删除成功");
                    alert.setContentText("对应的借阅记录已成功删除");
                    alert.showAndWait();
                }
                else{
                    alert.setTitle("提醒");
                    alert.setHeaderText("删除失败");
                    alert.setContentText("对应的借阅记录未能成功删除");
                    alert.showAndWait();
                }
        }else{
            alert.setTitle("提醒");
            alert.setHeaderText("注意");
            alert.setContentText("书库中并没有这本书或者您还没查询,请重新输入或查询");
            alert.showAndWait();
        }
    }
    public void handleUpdateButton(TextField name,TextField id,TextField date,TextField quantity){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!(name.getText().trim().isEmpty() || name.getText() == null
                || id.getText().trim().isEmpty() || id.getText() == null
                || date.getText() == null || date.getText().trim().isEmpty()
                || quantity.getText() == null || quantity.getText().trim().isEmpty())) {
            try {
                Integer.parseInt(quantity.getText());
                if (quantity.getText().matches("\\d+")) {
                    Borrow borrow1 = new Borrow(id.getText(), date.getText(), Integer.parseInt(quantity.getText()), name.getText());

                    if (borrow.update_borrow(borrow1, borrow2)) {
                        alert.setTitle("提醒");
                        alert.setHeaderText("修改成功");
                        alert.setContentText("对应的借阅记录已成功修改");
                        alert.showAndWait();
                    }
                }
            }catch (NumberFormatException e){
                alert.setTitle("提醒");
                alert.setHeaderText("修改失败");
                alert.setContentText("请检查您输入的修改的数量是否为整数");
                alert.showAndWait();
            }
        }else{
            alert.setTitle("提醒");
            alert.setHeaderText("注意");
            alert.setContentText("书库中并没有这本书或者您还没查询或者您还没填入修改信息,请重新输入或查询");
            alert.showAndWait();
        }
    }
    public static void showErrorInsertDialog(String message) {
        Alert alertwarning = new Alert(Alert.AlertType.ERROR);
        alertwarning.setTitle("错误");
        alertwarning.setHeaderText("在插入记录的时候遇到错误");
        alertwarning.setContentText(message);
        alertwarning.showAndWait();
    }
    public static void showErrorDeleteDialog(String message) {
        Alert alertwarning = new Alert(Alert.AlertType.ERROR);
        alertwarning.setTitle("错误");
        alertwarning.setHeaderText("在删除记录的时候遇到错误");
        alertwarning.setContentText(message);
        alertwarning.showAndWait();
    }
    public static void showErrorGetDialog(String message) {
        Alert alertwarning = new Alert(Alert.AlertType.ERROR);
        alertwarning.setTitle("错误");
        alertwarning.setHeaderText("在查询记录的时候遇到错误");
        alertwarning.setContentText(message);
        alertwarning.showAndWait();
    }
}
