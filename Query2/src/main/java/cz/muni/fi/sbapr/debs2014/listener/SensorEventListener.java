package cz.muni.fi.sbapr.debs2014.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * // [is-mr[3]]
    // [rs-rbr]
    // [rs-mr[3]]
    //  [SensorEventSubscriber-t-is-mr[]] 
    //  [SensorEventSubscriber-m]-[rbr-i] 
    //  [SensorEventSubscriber-O]-[rbr-i] 
 * 
 * @author Lukáš Pástor
 */
public class SensorEventListener implements UpdateListener {

    private static final Logger LOG = 
            LoggerFactory.getLogger(SensorEventListener.class);   
    private static final String className = 
            SensorEventListener.class.getSimpleName();
    
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
    
    /**
     * Called by the engine before delivering events to update methods
     * @param insertStreamLength    the number of events of the insert stream 
     *                              to be delivered
     * @param removeStreamLength    the number of events of the remove stream 
     *                              to be delivered
     */
    public void updateStart(int insertStreamLength, int removeStreamLength) {
        LOG.info("updateStart i : {}, r : {}", insertStreamLength, removeStreamLength);
    }
           
    /**
     * Called by the engine after delivering events
     */
    public void updateEnd() {   
        LOG.info("updateEmd");
    }
                                                
    /**
     * The update method to support delivery of select clause columns as an 
     * object array. Each item in the object array represents a column in the 
     * select clause.
     * @param row   columns in the select clause
     */    
    public void update(final Object[] row) {    
        LOG.info("[{}]-{} update(Object[])", className, ROW_BY_ROW_I);
    }                          
    
    /**
     * The update method to support delivery of select clause columns as an 
     * map representation of each row. Each column in the select clause is 
     * then made an entry in the resulting Map. The Map keys are the column 
     * name if supplied, or the expression string itself for columns without 
     * a name.
     * @param row   columns in the select clause
     */
    public void update(final Map row) {
        LOG.info("[{}]-{} update(Map)", className, ROW_BY_ROW_I);
    }
    
    /**
     * The update method to support input stream delivery of wildcards (*) in 
     * select clause representing all properties of SensorEvents in row-by-row 
     * delivery mode.
     * @param newEvent  SensorEvent input stream event
     */    
    public void update(final SensorEvent newEvent) {
//        StringBuilder sb = new StringBuilder(100);
//        sb.append("[");
//        sb.append(className).append("-");
//        sb.append("E").append("-");                             
//        sb.append("]");
        log(newEvent, ROW_BY_ROW_I, PLAIN);
    }
    
    /**
     * The update method to support remove stream delivery of wildcards (*) in 
     * select clause representing all properties of SensorEvents in row-by-row 
     * delivery mode. Subscriber receives remove stream events if it provides 
     * a method named updateRStream. The method must accept the same number and
     * types of parameters as the update method.
     * @param oldEvent  remove stream events
     */
    public void updateRStream(final SensorEvent oldEvent) { 
        log(oldEvent, ROW_BY_ROW_R, PLAIN);
          
    }
    
    
    
    @Override
    public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {     
        if (newEvents != null && newEvents.length > 0) {
            for (EventBean newEvent : newEvents) {                
                //update(newEvent);
                log((SensorEvent) newEvent.getUnderlying(), MULTI_ROW_I, PLAIN);
            }
        }       
        if (oldEvents != null && oldEvents.length > 0) {
            for (EventBean oldEvent : oldEvents) {                
                //updateRStream(sensorEvent);                
                log((SensorEvent) oldEvent.getUnderlying(), MULTI_ROW_R, PLAIN);
            }
        }
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
}
