package cz.muni.fi.sbapr.debs2014.subscriber;

import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class TestSubscriber1 {
    private static final Logger LOG = 
            LoggerFactory.getLogger(TestSubscriber1.class);
    private static final String className = 
            TestSubscriber1.class.getSimpleName();
    
//    public void update(final SensorEvent[] newEvents) {        
//        LOG.info("[{}] - Update multiple", className);
//        if (newEvents != null && newEvents.length > 0) {
//            Arrays.stream(newEvents).forEach(event -> update(event));            
//        }       
//    }        
    
//    public void update(final SensorEvent sensorEvent) {
//        LOG.trace("[{}] - Update single", className);
//        if (sensorEvent == null) {
//            LOG.info("[{}] - Received event is null.", className);
//            return;
//        }
//        
//        LOG.info(sensorEvent.toString());
//                
////        update
////        (
////            sensorEvent.getId(),
////            sensorEvent.getTimestamp(),
////            sensorEvent.getValue(),
////            sensorEvent.getProperty(),
////            sensorEvent.getPlugId(),
////            sensorEvent.getHouseholdId(),
////            sensorEvent.getHouseId()
////        );
//    }     
         
    public void update
        (
//                final long id
//            ,   final long timestamp
//            ,   final BigDecimal value
//            ,   final Boolean property
//                final long plugId
//            ,   final long householdId
//            ,   final long houseId
//            ,   final long count
//            ,   final long size
//            ,   final double median
//                long sum
//               final BigDecimal plugAvg
                final BigDecimal windowAvg
//            ,   final long ts_start
//            ,   final long ts_stop
        )
    {
        LOG.info
        (
                "[{}] - "
//            +   "{}, "            
//            +   "{}, "
//            +   "{}, "
//            +   "{}, "
//            +   "{}, "
//            +   "{}, "
//            +   "id : {} "
//            +   "timestamp : {} "                                
//            +   "value : {} "              
//            +   "property, : {} "
//            +   "houseId {} "
//            +   "householdId {} "
//            +   "plugId : {} "
//            +   "count : {} "
//            +   "size : {} "
//            +   "median : {} "
//            +   "livePlugs : {} "
//            +   "avg : {} "
//            +   "sum-id : {} "
//            +   "plug-avg : {} "
            +   "win-avg : {} "
//            +   "ts_stop : {} "                         
            ,   className
//            ,   id
//            ,   timestamp
//            ,   value
//            ,   property
//            ,   houseId
//            ,   householdId
//            ,   plugId
//            ,   count
//            ,   size
//            ,   median
//            ,   sum
//            ,   plugAvg.doubleValue()
            ,   windowAvg.doubleValue()
//            ,   windowAvg.setScale(4)
//            ,   ts_start
//            ,   ts_stop
        );                   
    }      
        
    public void updateRStream
            (
//                final long id
//            ,   final long timestamp
//            ,   final BigDecimal value
//            ,   final Boolean property
//                final long plugId
//            ,   final long householdId
//            ,   final long houseId
//            ,   final long count
//            ,   final long size
//            ,   final double median
//                long sum
//                final BigDecimal plugAvg
                final BigDecimal windowAvg
//            ,   final long ts_start
//            ,   final long ts_stop
        )
    {
        LOG.info
        (
                "[{}] - "
//            +   "{}, "            
//            +   "{}, "
//            +   "{}, "
//            +   "{}, "
//            +   "{}, "
//            +   "{}, "
//            +   "id : {} "
//            +   "timestamp : {} "                                
//            +   "value : {} "              
//            +   "property, : {} "
//            +   "houseId {} "
//            +   "householdId {} "
//            +   "plugId : {} "
//            +   "count : {} "
//            +   "size : {} "
//            +   "median : {} "
//            +   "livePlugs : {} "
//            +   "avg : {} "
//            +   "sum-id : {} "
//            +   "plug-avg : {} "
            +   "win-avg : {} "
//            +   "ts_stop : {} "                         
            ,   className
//            ,   id
//            ,   timestamp
//            ,   value
//            ,   property
//            ,   houseId
//            ,   householdId
//            ,   plugId
//            ,   count
//            ,   size
//            ,   median
//            ,   sum
//            ,   plugAvg.doubleValue()
            ,   windowAvg.doubleValue()
//            ,   ts_start
//            ,   ts_stop
        );                   
    }      
}
