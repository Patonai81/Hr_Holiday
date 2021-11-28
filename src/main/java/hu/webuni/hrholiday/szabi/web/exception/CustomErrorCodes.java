package hu.webuni.hrholiday.szabi.web.exception;

import java.util.HashMap;


public class CustomErrorCodes {

    public static final String CUSTOMER_NOT_FOUND="CUST_001";
    private static final String CUSTOMER_NOT_FOUND_MESSAGE="Given customer not found in DB";
    public static final String CUSTOMER_USERNAME_NOT_FOUND="CUST_002";
    private static final String CUSTOMER_USERNAME_NOT_FOUND_MESSAGE="Given customer with given userName not found in DB";

    public static final String HOLIDAY_NOT_FOUND="HOLD_001";
    private static final String HOLIDAY_NOT_FOUND_MESSAGE="Given holiday request not found in DB";
    public static final String HOLIDAY_NOT_MODIFY="HOLD_002";
    private static final String HOLIDAY_NOT_MODIFY_MESSAGE="Given holiday request cannot be modified";
    public static final String QUERY_SHORT_NOT_OK="QUERY_001";
    private static final String QUERY_SHORT_NOT_OK_MESSAGE="Given shorting condition is not appropriate";



    private static  HashMap<String,String> codeToMessage= new HashMap<>();

    static{
        codeToMessage.put(CUSTOMER_NOT_FOUND,CUSTOMER_NOT_FOUND_MESSAGE);
        codeToMessage.put(CUSTOMER_USERNAME_NOT_FOUND,CUSTOMER_USERNAME_NOT_FOUND_MESSAGE);
        codeToMessage.put(HOLIDAY_NOT_FOUND,HOLIDAY_NOT_FOUND_MESSAGE);
        codeToMessage.put(HOLIDAY_NOT_MODIFY,HOLIDAY_NOT_MODIFY_MESSAGE);
        codeToMessage.put(QUERY_SHORT_NOT_OK,QUERY_SHORT_NOT_OK_MESSAGE);

    }

    public static String getMessage(String code){
        return codeToMessage.get(code);
    }

}
