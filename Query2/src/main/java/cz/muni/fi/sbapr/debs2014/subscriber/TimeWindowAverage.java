package cz.muni.fi.sbapr.debs2014.subscriber;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class TimeWindowAverage {
         
    private static final Logger LOG = 
            LoggerFactory.getLogger(TimeWindowAverage.class);
    private static final String className = 
            TimeWindowAverage.class.getSimpleName();       
            
    public void update
        (
            final long id,
            final long timestamp,
            final BigDecimal value,
            final Boolean property,
            final long plugId,
            final long householdId,
            final long houseId,
            final long count,
//            final long size,        
//            final double median,                            
            final double avg
//            final long ts_start,
//            final long ts_stop
        ) 
    {            
            LOG.info
            (
                    "[{}] - "                
//                +   "{}, "            
//                +   "{}, "
//                +   "{}, "
//                +   "{}, "
//                +   "{}, "
//                +   "{}, "
//                +   "id : {} "
//                +   "timestamp : {} "                                
//                +   "value : {} "
//                +   "property, : {} "
//                +   "houseId {} "
//                +   "householdId {} "
//                +   "plugId : {} "
//                +   "count : {} "
//                +   "size : {} "
//                +   "median : {} "
                +   "glob-avg : {} "
//                +   "ts_start : {} "
//                +   "ts_stop : {} "                      
                ,   className
//                ,   id
//                ,   timestamp
//                ,   value.doubleValue()
//                ,   property
//                ,   houseId
//                ,   householdId
//                ,   plugId
//                ,   count
//                ,   size
//                ,   median
                ,   avg
//                ,   ts_start
//                ,   ts_stop
            );
    }       
        
    public void update
        (            
            final BigDecimal avg
//            final double avg
        ) 
    {          
            LOG.info
            (
                    "[{}] - "                         
                +   "TimeWindowAverage : {} "
                ,   className
                ,                
                avg.setScale(3, RoundingMode.HALF_UP)
            );
    }
        
    public void update(final Double avg) 
    {
        update(BigDecimal.valueOf(avg));
//        
//            LOG.info
//            (
//                    "[{}] - "                         
//                +   "TimeWindowAverage : {} "
//                ,   className
//                ,
//                BigDecimal.valueOf(avg).setScale(3, RoundingMode.HALF_UP)
//            );
    }
}
