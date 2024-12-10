package dao;

import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pei
 * @version 1.0
 * 2024/12/4
 */
@SuppressWarnings({"all"})
public class BookDaoImpl {
    private static BookDaoImpl bookdao;
    static{
        bookdao = new BookDaoImpl();
    }
    private BookDaoImpl() {}

    public static BookDaoImpl getInstance(){return bookdao;}
//插入一条书的记录
    public boolean insertBook(String id,String name,String author,String publisher,int quantity){
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement ps = null;
        String sql="insert into book(book_id,book_name,author,publisher,stock_quantity)values(?,?,?,?,?)";
        int index=1;
        try {
            ps=conn.prepareCall(sql);
            ps.setString(index++, id);
            ps.setString(index++, name);
            ps.setString(index++, author);
            ps.setString(index++, publisher);
            ps.setInt(index++, quantity);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //根据书的名字更新图书信息
    public boolean updateBook(Book book1,Book book2){
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement ps = null;
        String sql="update book set book_id=?"+
                ",author=?," +
                "publisher=?,"+
                "stock_quantity=?,"+
                "book_name=?"+
                "where book_name=?;";

        try {
            ps=conn.prepareStatement(sql);
            int index=1;
            ps.setString(index++,book1.getBook_id());
            ps.setString(index++,book1.getAuthor());
            ps.setString(index++,book1.getPublisher());
            ps.setInt(index++,book1.getStock_quantity());
            ps.setString(index++,book1.getBook_name());
            ps.setString(index++,book2.getBook_name());
            if (ps.executeUpdate()>0)
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //根据书名删除书的记录
    public boolean deleteBook(String name){
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement ps = null;
        String sql="delete from book where book_name=?;";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, name);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally
        {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //根据书的序号获得书的信息
    public Book getBookById(String id){
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = new Book();
        String sql="select * from book where book_id=?";

        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, id);
            rs=ps.executeQuery();
            if(rs.next()){
                book.setBook_id(rs.getString("book_id"));
                book.setBook_name(rs.getString("book_name"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setStock_quantity(rs.getInt("stock_quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return book;
    }
    /*通过书名获得书的信息*/
    public Book getBookByname(String name){
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = new Book();
        String sql="select * from book where book_name=?";

        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, name);
            rs=ps.executeQuery();
            if(rs.next()){
                book.setBook_id(rs.getString("book_id"));
                book.setBook_name(rs.getString("book_name"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setStock_quantity(rs.getInt("stock_quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return book;
    }
    /*获得所有书的信息*/
    public List<Book> getAllBooks(){
        String sql="select * from book";
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        try {
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                String id=rs.getString("book_id");
                String book_name=rs.getString("book_name");
                String author=rs.getString("author");
                String publisher=rs.getString("publisher");
                int stock_quantity=rs.getInt("stock_quantity");
                Book book=new Book(author,id,book_name,stock_quantity,publisher);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    /**
     * 在插入一条书籍信息之前先检查有没有该书
     * @param book
     * @return
     */
    public boolean checkbook(Book book){
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id=book.getBook_id();
        String name=book.getBook_name();
        String sql="select * from book where book_id=? or book_name=? ;";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            rs=ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
