package model;

/**
 * @author pei
 * @version 1.0
 * 2024/12/4
 */
public class Book {
    private String book_id;
    private String book_name;
    private String author;
    private String publisher;
    private int stock_quantity;

    public Book(String author, String book_id, String book_name, int stock_quantity, String publisher) {
        this.author = author;
        this.book_id = book_id;
        this.book_name = book_name;
        this.stock_quantity = stock_quantity;
        this.publisher = publisher;
    }
    public Book(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", book_id='" + book_id + '\'' +
                ", book_name='" + book_name + '\'' +
                ", publisher='" + publisher + '\'' +
                ", stock_quantity=" + stock_quantity +
                '}';
    }
}
