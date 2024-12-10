package model;

import java.util.Date;

/**
 * @author pei
 * @version 1.0
 * 2024/12/4
 */
public class Borrow {
    private String book_id;
    private String reader_name;
    private String borrow_date;
    private int borrow_quantity;

    public Borrow() {}

    public Borrow(String book_id, String borrow_date, int borrow_quantity, String reader_name) {
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.borrow_quantity = borrow_quantity;
        this.reader_name = reader_name;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setBorrow_date(String borrow_date) {
        this.borrow_date = borrow_date;
    }

    public void setBorrow_quantity(int borrow_quantity) {
        this.borrow_quantity = borrow_quantity;
    }

    public void setReader_name(String reader_name) {
        this.reader_name = reader_name;
    }

    public String getBook_id() {
        return book_id;
    }

    public int getBorrow_quantity() {
        return borrow_quantity;
    }

    public String getReader_name() {
        return reader_name;
    }

    public String getBorrow_date() {
        return  borrow_date;
    }
}
