package cz.muni.fi.sbapr.debs2014.subscriber;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import cz.muni.fi.sbapr.debs2014.event.PlugAggregationEvent;
import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.math.BigDecimal;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class PlugAggregationEventSubscriber {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(PlugAggregationEventSubscriber.class);   
    private static final String className = 
            PlugAggregationEventSubscriber.class.getSimpleName();
    
    private static final String ROW_BY_ROW_I = "[rbr-i] : ";
    private static final String ROW_BY_ROW_R = "[rbr-r] : ";
    private static final String MULTI_ROW_I  = "[mr -i] : ";
    private static final String MULTI_ROW_R  = "[mr -r] : ";
    
    private static final String PLAIN =
            "{{}, {}, {}, {}}";
    private static final String FULL
            =   "houseId : {} "
            +   "householdId : {}, "
            +   "plugId : {}, "
            +   "value : {}, "
            ;        
  
    private static final String HALF_SEPARATOR
            = "------------------------------";
    private static final String FULL_SEPARATOR
            = HALF_SEPARATOR + HALF_SEPARATOR;
            
    private String separator;
    private String name;
    
    public PlugAggregationEventSubscriber(String name) {
        this.name = name;
        this.separator = HALF_SEPARATOR + name + HALF_SEPARATOR;        
    }
      
    /*
    public void update(Map rowMap) {
        StringBuilder sb = new StringBuilder(80);
        rowMap.values().stream().forEach((value) -> {
            sb.append(value.toString()).append(", ");                        
        });
        LOG.info(sb.toString());
        sb.setLength(0);        
    }   
    */
    
    public void update
        (
                final long plugId
            ,   final long householdId
            ,   final long houseId
            ,   final BigDecimal plugAvg
        ) 
    {
        LOG.info
        (
                "[{}] - "
            +   "{}, "
            +   "{}, "
            +   "{}, "
            +   "plug-avg : {} "                        
            ,   className
            ,   plugId
            ,   householdId
            ,   houseId
            ,   plugAvg.doubleValue()
        );       
    }
    
    
    public void update(final PlugAggregationEvent[] newEvents, final PlugAggregationEvent[] oldEvents) {
        
        LOG.info(separator);
        if (newEvents != null && newEvents.length > 0) {
            for (PlugAggregationEvent newEvent : newEvents) {                                
                log(newEvent, MULTI_ROW_I, PLAIN);
            }
        }
        LOG.info(separator);
        LOG.info("");
        /*
        if (oldEvents != null && oldEvents.length > 0) {
            for (PlugAggregationEvent oldEvent : oldEvents) {                
                log(oldEvent, MULTI_ROW_R, PLAIN);
            }
        } 
        */
    }
    
    private void log(final PlugAggregationEvent plugAggregationEvent, final String deliveryType, final String logFormatType) {
        
        if (plugAggregationEvent == null) {
            LOG.info("[{}]-{} : Event is null.", className, deliveryType);            
            return;
        }
        
        LOG.info
        (
                "[{}]-"
            +   deliveryType
            +   logFormatType
            ,   className                                                
            ,   plugAggregationEvent.getHouseId()
            ,   plugAggregationEvent.getHouseholdId()
            ,   plugAggregationEvent.getPlugId()
            ,   plugAggregationEvent.getValue()
        );
    }
      
    /*
    // Called by the engine before delivering events to update methods
    public void updateStart(int insertStreamLength, int removeStreamLength) {
        LOG.info(separator);
    }
    
    // To deliver insert stream events
    public void update(String orderId, long count) {
    
    }
    
    // To deliver remove stream events
    public void updateRStream(String orderId, long count) {
    
    }
    
    // Called by the engine after delivering events
    public void updateEnd() {
        LOG.info(separator);
    }
    
    
    
    public void update
        (
                final long plugId
            ,   final long householdId
            ,   final long houseId
            ,   final BigDecimal plugAvg
        ) 
    {
        LOG.info
        (
                "[{}] - "
            +   "{}, "
            +   "{}, "
            +   "{}, "
            +   "plug-avg : {} "                        
            ,   className
            ,   plugId
            ,   householdId
            ,   houseId
            ,   plugAvg.doubleValue()
        );       
    }
    
    // !! to force call to update(newEvents, oldEvents)
    public void update(final SensorEvent newEvent) {                
        //log(newEvent, ROW_BY_ROW_I, PLAIN);
        LOG.info("update(final SensorEvent newEvent)");
    }
    
    public void update(Map[] insertStream) {
        LOG.info("update(Map[] insertStream)");
    }
    
    public void updateRStream(final SensorEvent oldEvent) {        
        log(oldEvent, ROW_BY_ROW_R, PLAIN);
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
    */
}
