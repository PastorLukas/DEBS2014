package cz.muni.fi.sbapr.debs2014.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.UpdateListener;
import java.math.BigDecimal;
import java.math.MathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class GlobalLoadMedianListener implements UpdateListener {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(GlobalLoadMedianListener.class);   
        
    @Override
    public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {
        if (newEvents != null && newEvents.length > 0) {
            if (newEvents[0] != null) {                                                                 
                log(newEvents[0]);
            }                                                   
        }       
    }        
    
    private void log(EventBean theEvent) {        
        try {
            double median = (double) theEvent.get("median");
            BigDecimal roundedMedian = new BigDecimal(median, MathContext.DECIMAL32);
            LOG.info("median : {}", roundedMedian);
        } catch (final PropertyAccessException ex) {
            LOG.info("Property not in eventBean.");
        }
    }
}
