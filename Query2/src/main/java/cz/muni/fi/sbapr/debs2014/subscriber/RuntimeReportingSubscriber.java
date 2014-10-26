package cz.muni.fi.sbapr.debs2014.subscriber;

import static cz.muni.fi.sbapr.debs2014.util.TimestampFormatter.formatEpochMillis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class RuntimeReportingSubscriber {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(RuntimeReportingSubscriber.class);
    private static final String SEPARATOR 
            = "-----------------------------------------------------------"
            + "-----------------------------------------------------------";
    
    public void update(final long runtime) {   
        log(runtime);
//        out(runtime);        
    }
    
    private static void log(final long runtime) {
        LOG.info(SEPARATOR);
        LOG.info("runtime : [{}] {}", runtime, formatEpochMillis(runtime));
        LOG.info(SEPARATOR);
    }
    
    private static void out(final long runtime) {
        System.out.println(SEPARATOR);
        System.out.println("runtime : " + formatEpochMillis(runtime));                
        System.out.println(SEPARATOR);
    }        
}
