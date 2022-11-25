package com.company.bankservice.utils;

import java.util.Date;

public class DateTimeUtils {

    public static Date unixToDate(long date){
        return new Date(date/1000);
    }

}
