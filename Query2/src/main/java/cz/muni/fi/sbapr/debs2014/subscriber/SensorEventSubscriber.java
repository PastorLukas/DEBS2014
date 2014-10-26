package cz.muni.fi.sbapr.debs2014.subscriber;

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
    private static final String PLAIN_FULL_LOG = 
                "{{}, {}, {}, {}, {}, {}, {}}";
                
//    public void update(final SensorEvent sensorEvent) {
//        LOG.info("[{}] - Update single", className);
//        logCustom(sensorEvent);        
//        log(sensorEvent);
//    }
    
    public void update(final SensorEvent sensorEvent) {
        LOG.trace("[{}] - Update single", className);
        if (sensorEvent == null) {
            LOG.info("[{}] - Received event is null.", className);            
            return;
        }
        
        log(PLAIN_FULL_LOG, 
            sensorEvent.getId(),
            sensorEvent.getTimestamp(),
            sensorEvent.getValue(),
            sensorEvent.getProperty(),
            sensorEvent.getPlugId(),
            sensorEvent.getHouseholdId(),
            sensorEvent.getHouseId());
        
//        update(
//            sensorEvent.getId(),
//            sensorEvent.getTimestamp(),
//            sensorEvent.getValue(),
//            sensorEvent.getProperty(),
//            sensorEvent.getPlugId(),
//            sensorEvent.getHouseholdId(),
//            sensorEvent.getHouseId()
//        );
    }
    
    public void update
        (
            final long id,
            final long timestamp,
            final BigDecimal value,
            final Boolean property,
            final long plugId,
            final long householdId,
            final long houseId                          
        ) 
    {       
        LOG.info
        (
            "[{}] - "
//            + "{}, "            
//            + "{}, "
//            + "{}, "
//            + "{}, "
//            + "{}, "
//            + "{}, "
            + "id : {} "
            + "timestamp : {} "                                
            + "value : {} "
            + "property, : {} "
            + "houseId {} "
            + "householdId {} "
            + "plugId : {} "
            ,                         
            className,
            id,
            timestamp,
            value,
            property,
            houseId,
            householdId,
            plugId
        );                   
    }
          
    public void log
        (
            final String pattern,
            final long id,
            final long timestamp,
            final BigDecimal value,
            final Boolean property,
            final long plugId,
            final long householdId,
            final long houseId                          
        ) 
    {       
        LOG.info
        (
            "[{}] - " + pattern
            ,                         
            className,
            id,
            timestamp,
            value,
            property,
            houseId,
            householdId,
            plugId
        );                                      
    }
}
