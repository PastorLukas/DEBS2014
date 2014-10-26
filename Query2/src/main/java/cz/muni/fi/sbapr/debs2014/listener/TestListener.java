package cz.muni.fi.sbapr.debs2014.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.UpdateListener;
import java.math.BigDecimal;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class TestListener implements UpdateListener {
    
    private static final Logger LOG = LoggerFactory.getLogger(GlobalLoadMedianListener.class);
    private static final String className = TestListener.class.getSimpleName();
    
    @Override
    public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {
        LOG.info("[{}] - Update multiple", className);
        if (newEvents != null && newEvents.length > 0) {
            for (EventBean theEvent : newEvents) {
                handle(theEvent);
            }            
        }     
    }
    
    private void handle(EventBean theEvent) {
        if (theEvent == null) {
            LOG.info("[{}] - One of received event beans is null.", className);
            return;
        }         
        log(theEvent);
    }
    
    private static void log(final EventBean theEvent) {
        if (theEvent == null) {
            LOG.info("[{}] - One of input stream event beans is null.", className);
            return;
        }
        
        try {                                   
            long id = (long) theEvent.get("id");
            long timestamp = (long) theEvent.get("timestamp");                        
            BigDecimal value = (BigDecimal) theEvent.get("value");        
            Boolean property = (Boolean) theEvent.get("property");
            long plugId = (long) theEvent.get("plugId");
            long householdId = (long) theEvent.get("householdId");
            long houseId = (long) theEvent.get("houseId");
            
//            long count = (long) theEvent.get("cnt");
//            double median = (double) theEvent.get("median");
//            long sum = (double) theEvent.get("median");
//            BigDecimal roundedMedian = new BigDecimal(median, MathContext.DECIMAL32);
//            long max = (long) theEvent.get("max");
            
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
//
//            + "castValue : {} "
//            + "doubleValue : {} "
//            + "{} == {} == {} "
//
            + "property, : {} "
            + "houseId {} "
            + "householdId {} "
            + "plugId : {} "
//            + "count : {} "
//            + "size : {} "
//            + "median : {} "
//            + "livePlugs : {} "
//                    
              ,     
                className,
                id,
                timestamp,
                value,
//                castValue,
//                doubleValue
                property,
                houseId,
                householdId,
                plugId
//                count
//                size
//                median                  
            );
            
        } catch (final PropertyAccessException ex) {
            LOG.info("Property not in eventBean.");
        }
    }
}
