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
import service.Bookservice;

import java.util.List;
import java.util.Optional;

/**
 * @author pei
 * @version 1.0
 * 2024/12/8
 */
@SuppressWarnings({"all"})
public class Bookscene {
  Stage stage=new Stage();
  Bookservice book = Bookservice.getInstance();
  Book book2;
  public  Bookscene(){
    VBox leftMenu = new VBox(40);
    leftMenu.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
    StackPane rightContent = new StackPane();
    rightContent.setStyle("-fx-padding: 10; -fx-background-color: #ffffff;");

    Button operation1 = new Button("全部书籍信息");
    operation1.setOnAction(e->updateContent(rightContent,1));
    Button operation2 = new Button("根据书名查找书本");
    operation2.setOnAction(e->updateContent(rightContent,2));
    Button operation3 = new Button("根据书序查找书本");
    operation3.setOnAction(e->updateContent(rightContent,3));
    Button operation4 = new Button("添加新的书本信息");
    operation4.setOnAction(e->updateContent(rightContent,4));
    Button operation5 = new Button("通过书本名字更新书本信息");
    operation5.setOnAction(e->updateContent(rightContent,5));
    Button operation6 = new Button("删除书本(通过书本名字)");
    operation6.setOnAction(e->updateContent(rightContent,6));

    leftMenu.getChildren().addAll(operation1, operation2, operation3, operation4, operation5, operation6);

    SplitPane splitpane=new SplitPane();
    splitpane.getItems().addAll(leftMenu,rightContent);
    splitpane.setDividerPositions(0.2);

    Scene scene =new Scene(splitpane,1000,400);
    stage.setScene(scene);
    stage.setTitle("书本业务操作");
    stage.show();
  }
  public void updateContent(StackPane contentPane, int i) {
    contentPane.getChildren().clear(); // 清空现有内容
    switch(i) {
      case 1://得到所有书本的信息
        update1(contentPane);
        break;
      case 2://根据书名查找书的信息
        update2(contentPane);
        break;
      case 3://根据书的序列号查找书的信息
        update3(contentPane);
        break;
      case 4://新增加书的信息
        update4(contentPane);
        break;
      case 5://更新书本的信息
        update5(contentPane);
        break;
      case 6://删除书本信息
        update6(contentPane);
        break;
    }
  }

  /**
   * 展示所有书籍的信息
   * @param contentPane
   */
  public void update1(StackPane contentPane) {
    TableView<Book> tableView = new TableView<>();

    TableColumn<Book, String> idcolumn = new TableColumn<>("序列号");
    idcolumn.setCellValueFactory(new PropertyValueFactory<>("book_id"));
    idcolumn.setMinWidth(200);
    TableColumn<Book, String> namecolumn = new TableColumn<>("书名");
    namecolumn.setCellValueFactory(new PropertyValueFactory<>("book_name"));
    namecolumn.setMinWidth(200);
    TableColumn<Book, String> authorcolumn = new TableColumn<>("作者");
    authorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    authorcolumn.setMinWidth(200);
    TableColumn<Book, String> publishercolumn = new TableColumn<>("出版社");
    publishercolumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    publishercolumn.setMinWidth(200);
    TableColumn<Book, Integer> quantitycolumn = new TableColumn<>("书籍数量");
    quantitycolumn.setCellValueFactory(new PropertyValueFactory<>("stock_quantity"));
    quantitycolumn.setMinWidth(200);

    tableView.getColumns().addAll(idcolumn, namecolumn, authorcolumn, publishercolumn, quantitycolumn);
    List<Book> booklist = book.queryAllbooks();
    ObservableList<Book> books = FXCollections.observableArrayList(booklist);
    tableView.setItems(books);
    contentPane.getChildren().add(tableView);
  }

  /**
   * 通过书的名字查找书的信息
   * @param contentPane
   */
  public void update2(StackPane contentPane) {
    TextField namefield = new TextField();
    namefield.setPromptText("请输入书名");
    Button searchbutton = new Button("查询");
    TableView<Book> tableView = new TableView<>();
    TableColumn<Book, String> idcolumn = new TableColumn<>("序列号");
    idcolumn.setCellValueFactory(new PropertyValueFactory<>("book_id"));
    idcolumn.setMinWidth(200);
    TableColumn<Book, String> namecolumn = new TableColumn<>("书名");
    namecolumn.setCellValueFactory(new PropertyValueFactory<>("book_name"));
    namecolumn.setMinWidth(200);
    TableColumn<Book, String> authorcolumn = new TableColumn<>("作者");
    authorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    authorcolumn.setMinWidth(200);
    TableColumn<Book, String> publishercolumn = new TableColumn<>("出版社");
    publishercolumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    publishercolumn.setMinWidth(200);
    TableColumn<Book, Integer> quantitycolumn = new TableColumn<>("书籍数量");
    quantitycolumn.setCellValueFactory(new PropertyValueFactory<>("stock_quantity"));
    quantitycolumn.setMinWidth(200);
    tableView.getColumns().addAll(idcolumn, namecolumn, authorcolumn, publishercolumn, quantitycolumn);
    HBox hbox = new HBox(namefield, searchbutton);
    searchbutton.setOnAction(e -> {
      handleSearchButtonClickName(namefield, tableView);
    });
     VBox box=new VBox(hbox,tableView);

     contentPane.getChildren().add(box);
   }

  /**
   * 根据书序查找书籍信息
   * @param contentPane
   */
   public void update3(StackPane contentPane) {
     TextField namefield = new TextField();
     namefield.setPromptText("请输入书的序列号");
     Button searchbutton = new Button("查询");
     TableView<Book> tableView = new TableView<>();
     TableColumn<Book, String> idcolumn = new TableColumn<>("序列号");
     idcolumn.setCellValueFactory(new PropertyValueFactory<>("book_id"));
     idcolumn.setMinWidth(200);
     TableColumn<Book, String> namecolumn = new TableColumn<>("书名");
     namecolumn.setCellValueFactory(new PropertyValueFactory<>("book_name"));
     namecolumn.setMinWidth(200);
     TableColumn<Book, String> authorcolumn = new TableColumn<>("作者");
     authorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
     authorcolumn.setMinWidth(200);
     TableColumn<Book, String> publishercolumn = new TableColumn<>("出版社");
     publishercolumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
     publishercolumn.setMinWidth(200);
     TableColumn<Book, Integer> quantitycolumn = new TableColumn<>("书籍数量");
     quantitycolumn.setCellValueFactory(new PropertyValueFactory<>("stock_quantity"));
     quantitycolumn.setMinWidth(200);
     tableView.getColumns().addAll(idcolumn, namecolumn, authorcolumn, publishercolumn, quantitycolumn);
     HBox hbox = new HBox(namefield, searchbutton);
     searchbutton.setOnAction(e -> {
       handleSearchButtonClickID(namefield, tableView);
     });
     VBox box = new VBox(hbox, tableView);
     contentPane.getChildren().add(box);
   }

  /**向book中插入数据
   * @param contentPane
   */
   public void update4(StackPane contentPane){
     Label laber1 = new Label("序列号");
     Label laber2 = new Label("书籍名称");
     Label laber3 = new Label("作者");
     Label laber4 = new Label("出版社");
     Label laber5 = new Label("库存量");

     TextField namefield = new TextField();
     TextField idfield = new TextField();
     TextField authorfield = new TextField();
     Button insert = new Button("确定插入信息");
     TextField publisherfield = new TextField();
     TextField quantityfield = new TextField();

     HBox box1=new HBox(10,laber1,idfield);
     HBox box2=new HBox(10,laber2,namefield);
     HBox box3=new HBox(10,laber3,authorfield,insert);
     HBox box4=new HBox(10,laber4,publisherfield);
     HBox box5=new HBox(10,laber5,quantityfield);

     VBox box=new VBox(20,box1,box2,box3,box4,box5);
     box.setPrefHeight(200); // 设置首选高度为 200
     box.setMinHeight(100);  // 设置最小高度为 100
     box.setMaxHeight(300);

     namefield.setPromptText("请在这里输入书籍的名称");
     idfield.setPromptText("请在这里输入书籍的序列号");
     authorfield.setPromptText("请在这里输入书籍的作者");
     publisherfield.setPromptText("请在这里输入书籍的出版社");
     quantityfield.setPromptText("请在这里输入书籍的库存量");

     insert.setOnAction(e -> {
       handleInsertButton(namefield,idfield,authorfield,publisherfield,quantityfield);
     });
     contentPane.getChildren().add(box);}

  public void update5(StackPane contentPane) {
    Label laber1 = new Label("书籍序列号");
    Label laber2 = new Label("书籍名称");
    Label laber3 = new Label("作者");
    Label laber4 = new Label("出版社");
    Label laber5 = new Label("库存量");

    TextField namefield = new TextField();
    TextField idfield = new TextField();
    TextField authorfield = new TextField();
    Button search = new Button("查询书籍信息");
    TextField publisherfield = new TextField();
    TextField quantityfield = new TextField();
    Button update = new Button("进行更新操作");

    HBox box1 = new HBox(10, laber1, idfield);
    HBox box2 = new HBox(10, laber2, namefield);
    HBox box3 = new HBox(10, laber3, authorfield, search);
    HBox box4 = new HBox(10, laber4, publisherfield);
    HBox box5 = new HBox(10, laber5, quantityfield);

    VBox box = new VBox(20, box1, box2, box3, box4, box5, update);
    box.setPrefHeight(200); // 设置首选高度为 200
    box.setMinHeight(100);  // 设置最小高度为 100
    box.setMaxHeight(300);

    namefield.setPromptText("在这里输入更新书目的名字");
    search.setOnAction(e -> {book2=handleSearchButtonClickName(namefield,idfield,authorfield,publisherfield,quantityfield);});
    update.setOnAction(e->{handleUpdateButton(namefield,idfield,authorfield,publisherfield,quantityfield,book2);});
    contentPane.getChildren().add(box);
  }
public void update6(StackPane contentPane) {
  Label label = new Label("待删除书目的名字");
  TextField namefield = new TextField();
  namefield.setPromptText("在这里输入删除书目的名字");
  Button button = new Button("确定");
  button.setOnAction(e -> {
    handleDeleteButton(namefield);
  });
  HBox hbox = new HBox(10,namefield, button);
  VBox box=new VBox(20,hbox);
  contentPane.getChildren().add(box);
}

  /**
   * 根据书名查找时用到的触发器
   * @param searchfield
   * @param tableView
   */
  public void handleSearchButtonClickName(TextField searchfield, TableView tableView) {
    String name = searchfield.getText();
    List<Book> booklist = book.getbyname(name);
    System.out.println(name);
    ObservableList<Book> books = FXCollections.observableArrayList(booklist);
    tableView.setItems(books);
  }
  public Book handleSearchButtonClickName(TextField searchfield,TextField idfield,TextField authorfield,TextField publisherfield,TextField quantityfield) {
    String name = searchfield.getText();
    List<Book> booklist = book.getbyname(name);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    Book book = booklist.get(0);
    if (book.getBook_id() != null) {
      String id = book.getBook_id();
      String author = book.getAuthor();
      String publisher = book.getPublisher();
      int quantity = book.getStock_quantity();
      idfield.setText(id);
      authorfield.setText(author);
      publisherfield.setText(publisher);
      quantityfield.setText(String.valueOf(quantity));
    }else{
      alert.setTitle("提醒");
      alert.setContentText("书库中没有这本书,请检查书名");
      alert.showAndWait();
    }
    return book;
  }
  /**根据书的序列号用到的触发器
   * @param namefield
   * @param tableView
   */
  public void handleSearchButtonClickID(TextField namefield, TableView tableView) {
    String id = namefield.getText();
    List<Book> booklist = book.getbyid(id);
    System.out.println(id);
    ObservableList<Book> books = FXCollections.observableArrayList(booklist);
    tableView.setItems(books);
  }

  /**
   * 插入图书的时候用到的触发器
   * @param name
   * @param id
   * @param author
   * @param publisher
   * @param quantity
   */
  public void handleInsertButton(TextField name, TextField id, TextField author, TextField publisher, TextField quantity) {
    String name1 = name.getText();
    String id1 = id.getText();
    Alert alertwarning = new Alert(Alert.AlertType.WARNING);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    String author1 = author.getText();
    String publisher1 = publisher.getText();
    String quantity1 = (quantity.getText());
    if (!(name1.trim().isEmpty() || name1 == null
            || id1.trim().isEmpty() || id1 == null
            || author1 == null || author1.trim().isEmpty()
            || publisher1 == null || publisher1.trim().isEmpty()
            || quantity1 == null || quantity1.trim().isEmpty())) {
      try {
        Integer.parseInt(quantity1);
        if (quantity1.matches("\\d+")) {
          Book book1 = new Book(author1, id1, name1, Integer.parseInt(quantity1), publisher1);
          if (book.checkbook(book1)) {//说明找到了书
            alertwarning.setTitle("警告");
            alertwarning.setHeaderText("请注意插入信息");
            alertwarning.setContentText("请认真检查插入记录是否已经存在");
            alertwarning.showAndWait();
          } else {
            book.add(book1);
            alert.setTitle("提示");
            alert.setHeaderText("信息已插入");
            alert.setContentText("新增图书已入库");
            alert.showAndWait();
          }
        }
      } catch (NumberFormatException e) {
        alertwarning.setTitle("警告");
        alertwarning.setHeaderText("请注意插入信息");
        alertwarning.setContentText("请认真检查插入的数量是否为整数");
        alertwarning.showAndWait();
      }
    } else {
      alert.setTitle("提示");
      alert.setHeaderText("书本信息未插入");
      alert.setContentText("请检查插入的信息是否正确完整");
      alert.showAndWait();
    }
  }

  /**
   * 删除操作对应的触发器
   * @param name
   */
  public void handleDeleteButton(TextField name) {
    String name1 = name.getText();
    List<Book> booklist = book.getbyname(name1);
    Book book1 = booklist.get(0);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    if (book.checkbook(book1)) {
      Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
      confirm.setTitle("注意");
      confirm.setHeaderText("请确认");
      confirm.setContentText("您确定要删除这条书籍记录吗");

      Optional<ButtonType> result = confirm.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {

        book.delete(name1);
        alert.setTitle("提示");
        alert.setHeaderText("已删除");
        alert.setContentText("已删除您想要删除的书目");
        alert.showAndWait();
      }
    } else {
      alert.setTitle("提示");
      alert.setHeaderText("未删除");
      alert.setContentText("请检查您输入的书名");
      alert.showAndWait();
    }
  }
  public void handleUpdateButton(TextField name, TextField id, TextField author, TextField publisher, TextField quantity, Book book2) {
    String name1 = name.getText();
    String id1 = id.getText();
    String author1 = author.getText();
    String publisher1 = publisher.getText();
    String quantity1 = quantity.getText();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    if (!(name1.trim().isEmpty() || name1 == null
            || id1.trim().isEmpty() || id1 == null
            || author1 == null || author1.trim().isEmpty()
            || publisher1 == null || publisher1.trim().isEmpty()
            || quantity1 == null || quantity1.trim().isEmpty())) {
      try {
        Integer.parseInt(quantity1);
        if (quantity1.matches("\\d+")) {
          Book book1 = new Book(author1, id1, name1, Integer.parseInt(quantity1), publisher1);
          if (book.update(book1, book2)) {
            alert.setTitle("提示");
            alert.setHeaderText("更新成功！");
            alert.setContentText("书本信息已经成功更新!");
            alert.showAndWait();
          } else {
            alert.setTitle("提示");
            alert.setHeaderText("更新失败!");
            alert.setContentText("书本信息更新失败!");
            alert.showAndWait();
          }
        }
      } catch (NumberFormatException e) {
        alert.setTitle("提示");
        alert.setHeaderText("更新失败！");
        alert.setContentText("请检查您更新的数量是否为整数!");
        alert.showAndWait();
      }
    }else{
      alert.setTitle("提示");
      alert.setHeaderText("注意！");
      alert.setContentText("请先查询书籍信息");
      alert.showAndWait();
    }
  }
}