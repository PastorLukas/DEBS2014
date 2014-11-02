package cz.muni.fi.sbapr.debs2014.subscriber;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class SensorEventSubscriber {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(SensorEventSubscriber.class);   
    private static final String className = 
            SensorEventSubscriber.class.getSimpleName();
    
    private static final String ROW_BY_ROW_I = "[rbr-i] : ";
    private static final String ROW_BY_ROW_R = "[rbr-r] : ";
    private static final String MULTI_ROW_I  = "[mr -i] : ";
    private static final String MULTI_ROW_R  = "[mr -r] : ";
    
    private static final String PLAIN =
            "{{}, {}, {}, {}, {}, {}, {}}";
    private static final String FULL
            =   "id : {}, "
            +   "timestamp : {}, "
            +   "value : {}, "
            +   "property : {}, "
            +   "plugId : {}, "
            +   "householdId : {}, "
            +   "houseId : {} "
            ;                                      
    
    public void update(final SensorEvent[] newEvents, final SensorEvent[] oldEvents) {
        if (newEvents != null && newEvents.length > 0) {
            for (SensorEvent newEvent : newEvents) {                                
                log(newEvent, MULTI_ROW_I, PLAIN);
            }
        }       
        if (oldEvents != null && oldEvents.length > 0) {
            for (SensorEvent oldEvent : oldEvents) {                
                log(oldEvent, MULTI_ROW_R, PLAIN);
            }
        }
    }       
    
    public void update(final SensorEvent newEvent) {                
        log(newEvent, ROW_BY_ROW_I, PLAIN);
    }
    
    public void updateRStream(final SensorEvent oldEvent) {        
        log(oldEvent, ROW_BY_ROW_R, PLAIN);
    }
    
    private void log(final SensorEvent sensorEvent, final String deliveryType, final String logType) {
        
        if (sensorEvent == null) {
            LOG.info("[{}]-{} : Event is null.", className, deliveryType);            
            return;
        }
        
        LOG.info
        (
                "[{}]-"
            +   deliveryType
            +   logType
            ,   className
            ,   sensorEvent.getId()
            ,   sensorEvent.getTimestamp()
            ,   sensorEvent.getValue()
            ,   sensorEvent.getProperty()
            ,   sensorEvent.getPlugId()
            ,   sensorEvent.getHouseholdId()
            ,   sensorEvent.getHouseId()
        );
    }
    
    //  row-by-row delivery
    //  multi-row delivery
    
//    public void update(final SensorEvent sensorEvent) {
//        LOG.trace("[{}] - Update single", className);
//        if (sensorEvent == null) {
//            LOG.info("[{}] - Received event is null.", className);            
//            return;
//        }
//        
//        log(PLAIN_FULL_LOG, 
//            sensorEvent.getId(),
//            sensorEvent.getTimestamp(),
//            sensorEvent.getValue(),
//            sensorEvent.getProperty(),
//            sensorEvent.getPlugId(),
//            sensorEvent.getHouseholdId(),
//            sensorEvent.getHouseId());
//        
////        update(
////            sensorEvent.getId(),
////            sensorEvent.getTimestamp(),
////            sensorEvent.getValue(),
////            sensorEvent.getProperty(),
////            sensorEvent.getPlugId(),
////            sensorEvent.getHouseholdId(),
////            sensorEvent.getHouseId()
////        );
//    }
    
//    public void update
//        (
//            final long id,
//            final long timestamp,
//            final BigDecimal value,
//            final Boolean property,
//            final long plugId,
//            final long householdId,
//            final long houseId
//        ) 
//    {       
//        LOG.info
//        (
//            "[{}] - "
////            +   "{}, "            
////            +   "{}, "
////            +   "{}, "
////            +   "{}, "
////            +   "{}, "
////            +   "{}, "
//            +   "id : {} "
//            +   "timestamp : {} "                                
//            +   "value : {} "
//            +   "property, : {} "
//            +   "plugId : {} "                                       
//            +   "householdId {} "
//            +   "houseId {} "
//            ,   className
//            ,   id
//            ,   timestamp
//            ,   value
//            ,   property
//            ,   plugId
//            ,   householdId
//            ,   houseId
//        );                   
//    }
//          
//    public void log
//        (
//            final String pattern,
//            final long id,
//            final long timestamp,
//            final BigDecimal value,
//            final Boolean property,
//            final long plugId,
//            final long householdId,
//            final long houseId                          
//        )
//    {
//        LOG.info
//        (
//            "[{}] - " + pattern
//            ,   className
//            ,   id
//            ,   timestamp
//            ,   value
//            ,   property
//            ,   plugId
//            ,   householdId
//            ,   houseId
//        );                                      
//    }
}
