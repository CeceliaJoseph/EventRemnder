package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils { public static long getMillisecFromDateTime(String dateTime) {
    try {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
       Date date = sdf.parse(dateTime);
        long millis = date.getTime();
        return millis;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
}
