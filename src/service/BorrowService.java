package service;

import dao.BookDaoImpl;
import dao.BorrowDaoImpl;
import model.Borrow;

import java.util.List;

/**
 * @author pei
 * @version 1.0
 * 2024/12/5
 */
public class BorrowService {
    private static  BorrowService borrowService ;
    static{
        borrowService = new BorrowService();
    }
    private BorrowDaoImpl borrowDao ;
    private BookDaoImpl bookDao ;

    private BorrowService(){
        borrowDao=BorrowDaoImpl.getInstance();
        bookDao=BookDaoImpl.getInstance();
    }
    public static BorrowService getInstance(){return borrowService;}

    /**
     *
     * @param readername
     * @param date
     * @param bookid
     * @param quantity
     * @return 新增语句
     */
    public boolean lendbook(String readername, String date, String bookid, int quantity){
            if(bookDao.getBookByname(bookid)!=null){
                if(borrowDao.insertBorrow(readername,date,bookid,quantity))
                return true;
                else return false;
            }
            else
                return false;
    }

    /**
     *
     * @param borrow
     * @param borrow2
     * @return  更新语句
     */
    public boolean update_borrow(Borrow borrow,Borrow borrow2){
        if(borrowDao.checkBorrow(borrow2)){
            if(borrowDao.updateBorrow(borrow,borrow2))
            return   true;
            else return false;
        }
        else{
            return false;
        }

    }

    /**
     * 通过日期返回借阅记录的object数组
     * @param date
     * @return 借阅信息的二维数组表
     */
    public List<Borrow> get_borrowbydate(String date){
        List<Borrow> borrowByDate = borrowDao.getBorrowByDate(date);
        return borrowByDate;
    }

    /**
     * 通过name得到借阅记录的信息
     * @param name
     * @return
     */
    public List<Borrow> get_borrowbyname(String name){
        List<Borrow> borrowByName = borrowDao.getBorrowByName(name);
        return borrowByName;
    }
    public List<Borrow> queryAllBorrow(){
        return borrowDao.queryAllBorrows();
    }
    /**
     * 通过书的序号获得书的借阅信息
     * @param id
     * @return
     */
    public List<Borrow> get_borrowbyid(String id){
        List<Borrow> borrowByDate = borrowDao.getBorrowBybook(id);
        return borrowByDate;
    }
    public boolean delete(Borrow borrow){
        if(borrowDao.deleteBorrow(borrow))
            return true;
        else return false;
    }
    public boolean checkBorrow(Borrow borrow){
        return borrowDao.checkBorrow(borrow);
    }
    public Borrow getBorrow(String name,String id,String date){
        return borrowDao.getborrow(name,id,date);
    }
}
