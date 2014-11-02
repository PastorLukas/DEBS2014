package cz.muni.fi.sbapr.debs2014.csv;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.time.CurrentTimeEvent;
import cz.muni.fi.sbapr.debs2014.Query2;
import static cz.muni.fi.sbapr.debs2014.csv.CSVInput.*;
import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.util.concurrent.TimeUnit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class CSVEventSender {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(CSVEventSender.class);
    private static final String eventName = SensorEvent.class.getSimpleName();
    private static final String engineURI = Query2.class.getName();        
    
    public void startSendingEvents() {

        EPServiceProvider epService = 
                EPServiceProviderManager.getProvider(engineURI);
        EPRuntime runtime = epService.getEPRuntime();
        EventSender sender = runtime.getEventSender(eventName);               
                               
//        sender.sendEvent(event);            
        final File file = new File(PC_E_CSV_DATA + SORTED_1M);
        try (final FileReader in = new FileReader(file);
                final CSVReader reader = new CSVReader(in)) {               
              
            long eventTime = 0L;                        
            SensorEvent event = null;
            
            long totalEventCount = 0L;
            while ((event = reader.getSensorEvent()) != null) {                              
                eventTime = MILLISECONDS.convert(event.getTimestamp(), SECONDS);
                try {                
                    Thread.sleep(500L);
                } catch (InterruptedException ex) {
                    
                }
                if (runtime.getCurrentTime() != eventTime) {
                    runtime.sendEvent(new CurrentTimeEvent(eventTime));
//                    log.info("runtime : {}", runtime.getCurrentTime());  
//                    log.info("");
//                    log.info("runtime : {}", formatEpochMillis(runtime.getCurrentTime()));
                }                
                sender.sendEvent(event);
                //log.info("timestamp : {}", MILLISECONDS.convert(event.getTimestamp(), SECONDS));                  
                totalEventCount++;
            }    
            LOG.info("event count : {}", totalEventCount); 
            LOG.info("evaluated : {}", runtime.getNumEventsEvaluated()); 
//            log.info("runtime : {}", formatEpochMillis(runtime.getCurrentTime()));                   
        } catch (IOException ex) {
            LOG.info(ex.getMessage());
        }                
    }                                              
}
