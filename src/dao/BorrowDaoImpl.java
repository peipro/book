package dao;

import model.Borrow;
import ui.BorrowScene;
import util.Dateutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pei
 * @version 1.0
 * 2024/12/3
 */
@SuppressWarnings({"all"})
public class BorrowDaoImpl {
    private static BorrowDaoImpl borrowdao;
    static{
        borrowdao = new BorrowDaoImpl();
    }
    private BorrowDaoImpl() {}

    public static BorrowDaoImpl getInstance(){return borrowdao;}

    /**
     * 插入一条borrow记录
     * @param readername
     * @param date
     * @param bookid
     * @param quantity
     * @return 布尔值，true表示插入成功
     */
    public boolean insertBorrow(String readername, String date, String bookid, int quantity){
        String sql="insert into borrow (borrow_date,book_id,reader_name,borrow_quantity) values(?,?,?,?)";
        Connection conn=BaseDaoImpl.getConn();
        PreparedStatement psts = null;//预编译处理
        Date date1=Dateutil.getDate(date);
        try {
            if(date1==null){
                throw new IllegalArgumentException("请检查日期格式是否为xxxx-xx-xx格式");
            }
            psts=conn.prepareStatement(sql);
            psts.setDate(1, Dateutil.getDate(date));
            psts.setString(2, bookid);
            psts.setString(3, readername);
            psts.setInt(4, quantity);
            return psts.executeUpdate()>0;
        } catch (Exception e) {
            BorrowScene.showErrorInsertDialog(e.getMessage());
        }finally {
            try {
                conn.close();
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据borrow2来更新borrow记录
     * @param borrow
     * @param borrow2
     * @return  true表示修改成功
     */
    public boolean updateBorrow(Borrow borrow,Borrow borrow2){
        String sql="update borrow set Borrow_date=?,book_id=?,reader_name=?,borrow_quantity=? where borrow_date=?" +
                "and reader_name=? and book_id=?";
        Connection conn=BaseDaoImpl.getConn();
        Date  date1=Dateutil.getDate(borrow.getBorrow_date());
        PreparedStatement psts = null;
        try {
            if(date1==null)
            {
                throw new IllegalArgumentException("请检查更新日期格式是否为xxxx-xx-xx格式");
            }
            psts=conn.prepareStatement(sql);
            if(borrow.getBorrow_date()!=null){
                psts.setDate(1, date1);
            }
            if(borrow.getBook_id()!=null){
                psts.setString(2, borrow.getBook_id());
            }
            if(borrow.getReader_name()!=null){
                psts.setString(3, borrow.getReader_name());
            }
            if(borrow.getBorrow_quantity()!=0){
                psts.setInt(4, borrow.getBorrow_quantity());
            }
            psts.setString(5, borrow2.getBorrow_date());
            psts.setString(6, borrow2.getReader_name());
            psts.setString(7, borrow2.getBook_id());

                return  psts.executeUpdate()>0;
        } catch (Exception e) {
            BorrowScene.showErrorInsertDialog(e.getMessage());
        }finally{
            try {
                if (conn != null)
                    conn.close();
                if (psts!=null)
                    psts.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 删除一条记录
     * @param borrow
     * @return  true表示删除成功
     */
    public boolean deleteBorrow(Borrow borrow){
        String sql="delete from borrow where borrow_date = ? and book_id = ? and reader_name = ? ;";
        Connection conn=BaseDaoImpl.getConn();
        int index=1;
        PreparedStatement psts = null;
        try {
            psts=conn.prepareStatement(sql);
            psts.setDate(index++, Dateutil.getDate(borrow.getBorrow_date()));
            psts.setString(index++, borrow.getBook_id());
            psts.setString(index++, borrow.getReader_name());

            if(psts.executeUpdate()>0)
                return true;
        } catch (SQLException e) {
            BorrowScene.showErrorDeleteDialog(e.getMessage());
        }finally{
            try {
                if(conn!=null){
                    conn.close();
                }
                if(psts!=null){
                    psts.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 查询得到所有的借阅记录
     * @return
     */
    public List<Borrow> queryAllBorrows(){
        String sql="select * from borrow";
        Connection conn=BaseDaoImpl.getConn();
        PreparedStatement psts = null;
        ResultSet rs = null;
        List<Borrow> borrowlist=new ArrayList<Borrow>();
        try {
            psts=conn.prepareStatement(sql);
            rs=psts.executeQuery();
            while(rs.next()){
                String id=rs.getString("book_id");
                String name=rs.getString("reader_name");
                int  quantity=rs.getInt("borrow_quantity");
                String date=rs.getString("borrow_date");
                Borrow borrow=new Borrow(id,date,quantity,name);
                borrowlist.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                psts.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return borrowlist;
    }

    /**
     * 通过借书人姓名查找借阅记录
     * @param readername
     * @return  借阅记录的list列表
     */
    public List<Borrow> getBorrowByName(String readername){
        Connection conn=BaseDaoImpl.getConn();
        List<Borrow> lists=new ArrayList<Borrow>();
        PreparedStatement psts = null;
        ResultSet rs = null;
        String sql="select * from borrow where reader_name=?";
        try {
            psts=conn.prepareStatement(sql);
            psts.setString(1,readername);
            rs=psts.executeQuery();
            while(rs.next()){
                String date=rs.getString("borrow_date");
                String id=rs.getString("book_id");
                String name=rs.getString("reader_name");
                int quantity=rs.getInt("borrow_quantity");
                Borrow borrow=new Borrow(id,date,quantity,name);
                lists.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                rs.close();
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    /**
     * 通过日期查看当天的借阅记录
     * @param borrowdate
     * @return list
     */
    public List<Borrow> getBorrowByDate(String borrowdate){
        Connection conn=BaseDaoImpl.getConn();
        List<Borrow> lists=new ArrayList<Borrow>();
        PreparedStatement psts = null;
        ResultSet rs = null;
        String sql="select * from borrow where borrow_date=?";
        try {
            psts=conn.prepareStatement(sql);
            psts.setString(1,borrowdate);
            rs=psts.executeQuery();
            while(rs.next()){
                String date=rs.getString("borrow_date");
                String id=rs.getString("book_id");
                String name=rs.getString("reader_name");
                int quantity=rs.getInt("borrow_quantity");
                Borrow borrow=new Borrow(id,date,quantity,name);
                lists.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                rs.close();
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    /**
     * 通过书的序号查找书的借阅情况
     * @param Bookid
     * @return
     */
    public List<Borrow> getBorrowBybook(String Bookid){
        Connection conn=BaseDaoImpl.getConn();
        List<Borrow> lists=new ArrayList<Borrow>();
        PreparedStatement psts = null;
        ResultSet rs = null;
        String sql="select * from borrow where book_id=?";
        try {
            psts=conn.prepareStatement(sql);
            psts.setString(1,Bookid);
            rs=psts.executeQuery();
            while(rs.next()){
                String date=rs.getString("borrow_date");
                String id=rs.getString("book_id");
                String name=rs.getString("reader_name");
                int quantity=rs.getInt("borrow_quantity");
                Borrow borrow=new Borrow(id,date,quantity,name);
                lists.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                rs.close();
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }
    public boolean checkBorrow(Borrow borrow){
        String sql="select * from borrow where borrow_date = ? and reader_name = ? and book_id = ? and borrow_quantity = ? ;";
            Connection conn=BaseDaoImpl.getConn();
            PreparedStatement psts = null;
            ResultSet rs = null;
            int index=1;
        try {
            if (borrow == null || borrow.getBorrow_date() == null || borrow.getReader_name() == null ||
                    borrow.getBook_id() == null || borrow.getBorrow_quantity() == 0) {
                throw new IllegalArgumentException("库中没有这条借阅记录！");
            }
            psts=conn.prepareStatement(sql);
            psts.setString(index++, borrow.getBorrow_date());
            psts.setString(index++, borrow.getReader_name());
            psts.setString(index++, borrow.getBook_id());
            psts.setInt(index++, borrow.getBorrow_quantity());
            rs=psts.executeQuery();
            return rs!=null &&rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e) {
            // 显示错误信息
            BorrowScene.showErrorGetDialog(e.getMessage());
        }
            finally
        {
            try {
                if (rs != null) rs.close();
                if (psts != null) psts.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public Borrow getborrow(String name,String id,String date){
        String sql="select * from borrow where book_id=? and reader_name=? and borrow_date=? ;";
        Connection conn=BaseDaoImpl.getConn();
        PreparedStatement psts = null;
        ResultSet rs = null;
        Borrow borrow=null;
        int index=1;
        try {
            psts=conn.prepareStatement(sql);
            psts.setString(index++,id);
            psts.setString(index++,name);
            psts.setString(index++,date);
            rs=psts.executeQuery();
            if(rs.next()){
                int quantity1=rs.getInt("borrow_quantity");
                borrow=new Borrow(id,date,quantity1,name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                if(psts!=null)
                psts.close();
                if(rs!=null)
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return borrow;
    }
}

