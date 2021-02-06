package org.rmj.g3appdriver.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SQLUtil {
    public static String quote (String string)
    {
        StringBuffer result = new StringBuffer();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            switch ( chars[i] )
            {
                case '\\':
                    result.append("\\\\"); break;
                case '\'':
                    result.append("''"); break;
                case '\"':
                    result.append("\"").append("\""); break;
                default:
                    result.append(chars[i]);
            }
        }
        return result.toString();
    }

    public static String toSQL (String string)
    {
        if(string instanceof String)
            return "'" + quote(string) + "'";
        else
            return null;
    }

    public static String toSQL (Timestamp timestamp)
    {
        return toSQL((Date)timestamp);
    }

    public static String toSQL (Date date)
    {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ret;
        if(date instanceof Date)
            ret = toSQL(sf.format(date));
        else
            ret = null;

        sf = null;
        return ret;
    }

    public static String toSQL (Calendar calendar)
    {
        return toSQL(calendar.getTime());
    }

    public static String toSQL (int value)
    {
        return Integer.toString(value);
    }

    public static String toSQL (boolean value)
    {
        return (value ? "true" : "false");
    }

    public static String toSQL (Character value)
    {
        return toSQL("'" + value.toString() + "'" );
    }

    public static String toSQL (Object object)
    {
        if ( object == null )
            return "NULL";
        if ( object instanceof Number)
            return object.toString();
        else if ( object instanceof Boolean)
            return toSQL(((Boolean)object).booleanValue());
        else if ( object instanceof String)
            return toSQL((String)object);
        else if ( object instanceof Timestamp)
            return toSQL((Timestamp)object);
        else if ( object instanceof Date)
            return toSQL((Date)object);
        else if ( object instanceof Calendar)
            return toSQL((Calendar)object);
        else if ( object instanceof Character)
            return toSQL((Character)object);
        else
            throw new IllegalArgumentException
                    ("Can not convert to sql code: " + object);
    }

    public static String dateFormat(Object date, String format){
        SimpleDateFormat sf = new SimpleDateFormat(format);
        String ret;
        if ( date instanceof Timestamp)
            ret = sf.format((Date)date);
        else if ( date instanceof Date)
            ret = sf.format(date);
        else if ( date instanceof Calendar){
            Calendar loDate = (Calendar) date;
            ret = sf.format(loDate.getTime());
            loDate = null;
        }
        else
            ret = null;

        sf = null;
        return ret;
    }

    public static Date toDate(String date, String format){
        Date loDate = null;
        try{
            //Be sure to follow the format specified
            SimpleDateFormat sf = new SimpleDateFormat(format);
            loDate = sf.parse(date);
            sf = null;
        }
        catch(ParseException ex){
            ex.printStackTrace();
            //Nothing to do;
        }

        return loDate;
    }

    public static boolean equalValue(Object obj1, Object obj2)
    {
        if ( obj1 == null && obj2 == null)
            return true;
        else if((obj1 != null && obj2 == null) || (obj1 == null && obj2 != null))
            return false;

        if ( obj1 instanceof Number && obj2 instanceof Number)
            return (new BigDecimal(obj1.toString()).compareTo(new BigDecimal(obj2.toString()))) == 0;
        else if ( obj1 instanceof Boolean && obj2 instanceof Boolean)
            return obj1 == obj2;
        else if ( obj1 instanceof String && obj2 instanceof String)
            return ((String)obj1).trim().equalsIgnoreCase(((String)obj2).trim()) ;
        else if ( obj1 instanceof Timestamp && obj2 instanceof Timestamp)
            return ((Timestamp)obj1).equals((Timestamp)obj1);
        else if ( obj1 instanceof Date && obj2 instanceof Date)
            return obj1.equals(obj2);
        else if ( obj1 instanceof Calendar && obj2 instanceof Calendar)
            return obj1.equals(obj2);
        else if ( obj1 instanceof Character && obj2 instanceof Character)
            return obj1.equals(obj2);
        else
            return false;
    }
}