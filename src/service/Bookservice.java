package service;

import dao.BookDaoImpl;
import model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pei
 * @version 1.0
 * 2024/12/5
 */
@SuppressWarnings({"all"})
public class Bookservice {
    private static Bookservice bookservice ;
    static{
        bookservice = new Bookservice();
    }
    public static Bookservice getInstance(){
        return bookservice;
    }
    private BookDaoImpl bookDao;
    private Bookservice(){
            bookDao=BookDaoImpl.getInstance();
    }

    //查询得到所有书的列表
    public List<Book> queryAllbooks(){
        return bookDao.getAllBooks();
    }
    //更新书本信息
    public Boolean update(Book book,Book book2){
        Book bk=bookDao.getBookByname(book2.getBook_name());
        if(bk!=null){
            bookDao.updateBook(book,book2);
            return true;
        }
        else{
            return false;
        }
    }
    //添加书本信息
    public void add(Book book){
        bookDao.insertBook(book.getBook_id(),book.getBook_name(),book.getAuthor(),book.getPublisher(),book.getStock_quantity());
    }
    //通过书名得到书本信息
    public List<Book> getbyname(String name){
        List<Book> books= new ArrayList<Book>();
        Book bk=bookDao.getBookByname(name);
        if(bk!=null){
            books.add(bk);
        }
        return books;
    }
    //通过书的序列号得到书本的信息
    public List<Book> getbyid(String id) {
        List<Book> books=new ArrayList<>();
        Book bk = bookDao.getBookById(id);
        if (bk != null) {
           books.add(bk);
        }
        return books;
    }
    //删除书本信息
    public void delete(String name) {
        Book bk = bookDao.getBookByname(name);
        if (bk != null) {
            bookDao.deleteBook(name);
        }
    }
    public boolean checkbook(Book book){
        if(bookDao.checkbook(book))
            return true;
        else
            return false;
    }
}
