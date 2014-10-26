package cz.muni.fi.sbapr.debs2014.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.UpdateListener;
import static cz.muni.fi.sbapr.debs2014.util.TimestampFormatter.formatEpochMillis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class RuntimeReportingListener implements UpdateListener {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeReportingListener.class);
    private static final String RUNTIME_PROPERTY = "runtime";
    private static final String SEPARATOR 
            = "-----------------------------------------------------------"
            + "-----------------------------------------------------------";       
                
    @Override
    public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {
        if (newEvents != null && newEvents.length > 0) {
            if (newEvents[0] != null) {
                try {                                                           
                    log((long) newEvents[0].get(RUNTIME_PROPERTY));
//                    out((long) newEvents[0].get(RUNTIME_PROPERTY));                                      
                } catch (final PropertyAccessException ex) {
                    LOG.info("Property named {} not in eventBean.", RUNTIME_PROPERTY);
                }
            }                                                   
        }       
    } 
    
    private void out(final long runtime) {
        System.out.println(SEPARATOR);
        System.out.println("runtime : " + formatEpochMillis(runtime));
        System.out.println(SEPARATOR);
    }
    
    private void log(final long runtime) {
        LOG.info(SEPARATOR);
        LOG.info("runtime : [{}] {}", runtime, formatEpochMillis(runtime));        
        LOG.info(SEPARATOR);
    }
}
