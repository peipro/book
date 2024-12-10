package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pei
 * @version 1.0
 * 2024/12/4
 */
@SuppressWarnings({"all"})
public class Dateutil {
    public static java.sql.Date getDate(String date) {  //把时间以yyyy-MM-dd格式格式化
        // 将英文时间转为指定格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse=dateFormat.parse(date);
            return new java.sql.Date(parse.getTime());
        } catch (ParseException e) {
            System.out.println(date);
        }
        return null;
    }
}
