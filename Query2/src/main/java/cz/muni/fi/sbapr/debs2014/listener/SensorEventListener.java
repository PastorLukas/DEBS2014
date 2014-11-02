package cz.muni.fi.sbapr.debs2014.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
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
