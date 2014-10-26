package cz.muni.fi.sbapr.debs2014.util;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author LuPa
 */
public class TimestampFormatter {
    
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MILLIS_PER_SECOND = 1000;
    private static final String MINUTES_SEPARATOR = ":";
    private static final String SECONDS_SEPARATOR = ".";    
    private static final String ZERO = "0";     
    private static final StringBuilder sb = new StringBuilder(20);
    
    //"HH:mm:ss";
    public static String formatEpochSecond(final long epochSecond) {                                        
        sb.setLength(0);        
        int unit = 0;
        
        //  hours
        unit = (int) (TimeUnit.SECONDS.toHours(epochSecond) % 24L);
        if (unit < 10) {
            sb.append(ZERO);
        }
        sb.append(unit).append(MINUTES_SEPARATOR);
        
        
        //  minutes
        unit = (int) (TimeUnit.SECONDS.toMinutes(epochSecond) % 60L);
        if (unit < 10) {
            sb.append(ZERO);
        }       
        sb.append(unit).append(MINUTES_SEPARATOR);
               
        
        //  seconds
        unit = (int) (epochSecond % 60L);
        if (unit < 10) {
            sb.append(ZERO);
        }       
        sb.append(unit);
        return sb.toString();
    }
    
    //"HH:mm:ss.SSS";
    public static String formatEpochMillis(final long epochMilli) { 
        sb.setLength(0);
        int unit = 0;   
                
        //  hours
        unit = (int) (TimeUnit.MILLISECONDS.toHours(epochMilli) % 24L);
        if (unit < 10) {
            sb.append(ZERO);
        }
        sb.append(unit).append(MINUTES_SEPARATOR);
        
        
        //  minutes
        unit = (int) (TimeUnit.MILLISECONDS.toMinutes(epochMilli) % 60L);
        if (unit < 10) {
            sb.append(ZERO);
        }       
        sb.append(unit).append(MINUTES_SEPARATOR);
               
        
        //  seconds
        unit = (int) (TimeUnit.MILLISECONDS.toSeconds(epochMilli) % 60L);
        if (unit < 10) {
            sb.append(ZERO);
        }       
        sb.append(unit).append(SECONDS_SEPARATOR);                               
        
        //  millis
        unit = (int) (epochMilli % 1000L);
        if (unit < 100) {
            sb.append(ZERO);
            if (unit < 10) {
                sb.append(ZERO);
            }
        }        
        sb.append(unit);              
        return sb.toString();
    }
}
