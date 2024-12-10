package util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author pei
 * @version 1.0
 * 2024/12/9
 */
public class CheckDate {
    public static boolean isValidDate(String date,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            formatter.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
