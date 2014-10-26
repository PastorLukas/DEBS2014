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
public class SensorEventReportingListener implements UpdateListener {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventReportingListener.class);   
    private static final String className = TestListener.class.getSimpleName();
    
    @Override
    public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {
        LOG.trace("[{}] - Update multiple", className);        
        if (newEvents != null && newEvents.length > 0) {
            for (EventBean theEvent : newEvents) {
                handle(theEvent);
            }                       
        }                
    }       
    
    private static void handle(EventBean theEvent) {
        if (theEvent == null) {
            LOG.info("[{}] - One of received event beans is null.", className);
            return;
        }        
        log((SensorEvent) theEvent.getUnderlying());
    }
    
    private static void log(final SensorEvent sensorEvent) {               
        LOG.info
        (
            "[{}] - "
            + "{"
            + "{}"   
            + ", {}"
            + ", {}"
            + ", {}"
            + ", {}"
            + ", {}"
            + ", {}"
            + "}"            
            ,                         
            className,
            sensorEvent.getId(),
            sensorEvent.getTimestamp(),
            sensorEvent.getValue(),
            sensorEvent.getProperty(),
            sensorEvent.getPlugId(),
            sensorEvent.getHouseholdId(),
            sensorEvent.getHouseId()            
        );
    }        
}
