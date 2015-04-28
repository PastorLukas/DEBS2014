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
import java.math.BigDecimal;
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
    
    public void startSendingEvents(final String path, long millis) {

        EPServiceProvider epService = 
                EPServiceProviderManager.getProvider(engineURI);
        EPRuntime runtime = epService.getEPRuntime();
        EventSender sender = runtime.getEventSender(eventName);               
                               
        LOG.info("Source data file : {}", path);                
//        sender.sendEvent(event);            
        final File file = new File(path);
        try (final FileReader in = new FileReader(file);
                final CSVReader reader = new CSVReader(in)) {
              
            long eventTime = 0L;                        
            SensorEvent event = null;            
            long totalEventCount = 0L;
            
            while ((event = reader.getSensorEvent()) != null) {                              
                eventTime = MILLISECONDS.convert(event.getTimestamp(), SECONDS);
                if (millis > 0) {
                    tryToSleepFor(millis);
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
                event = null;
            }    
            LOG.info("event count : {}", totalEventCount); 
            LOG.info("evaluated : {}", runtime.getNumEventsEvaluated()); 
//            log.info("runtime : {}", formatEpochMillis(runtime.getCurrentTime()));                   
        } catch (IOException ex) {
            LOG.info(ex.getMessage());
        }                
    }
    
    public void startSendingEventsAsArray(final String path, long millis) {

        EPServiceProvider epService = 
                EPServiceProviderManager.getProvider(engineURI);
        EPRuntime runtime = epService.getEPRuntime();
        EventSender sender = runtime.getEventSender(eventName);               
                               
        LOG.info("Source data file : {}", path);                
//        sender.sendEvent(event);            
        final File file = new File(path);
        try (final FileReader in = new FileReader(file);
                final CSVReader reader = new CSVReader(in)) {
              
            long eventTime = 0L;                        
            Object[] event = null;            
            long totalEventCount = 0L;
            
            long id; 
            long timestamp;
            BigDecimal value;
            boolean property;
            long plugId;
            long householdId;
            long houseId;
//            String format = "[%d, %d, %6.3f, %b, %2d, %2d, %2d]";
            
            while ((event = reader.getSensorEventAsArray()) != null) {                              
                eventTime = MILLISECONDS.convert((Long)event[SensorEvent.TIMESTAMP], SECONDS);
                if (millis > 0) {
                    tryToSleepFor(millis);
                }                
                
                if (runtime.getCurrentTime() != eventTime) {                    
                    runtime.sendEvent(new CurrentTimeEvent(eventTime));
//                    log.info("runtime : {}", runtime.getCurrentTime());  
//                    log.info("");
//                    log.info("runtime : {}", formatEpochMillis(runtime.getCurrentTime()));
                }
                
                runtime.sendEvent(event, "SensorEventType");
                //LOG.trace("abcd - {}", log(event));                
                
//                id = (long) event[0];
//                timestamp = (long) event[1];
//                value = (BigDecimal) event[2];
//                property = (boolean) event[3];
//                plugId = (long) event[4];
//                householdId = (long) event[5];
//                houseId = (long) event[6];
//                
//                System.out.println(String.format(format, id, timestamp, value, 
//                        property, plugId, householdId, houseId));
                //sender.sendEvent(event);
                //log.info("timestamp : {}", MILLISECONDS.convert(event.getTimestamp(), SECONDS));                  
                totalEventCount++;
                event = null;
            }    
            LOG.info("event count : {}", totalEventCount); 
            LOG.info("evaluated : {}", runtime.getNumEventsEvaluated()); 
//            log.info("runtime : {}", formatEpochMillis(runtime.getCurrentTime()));                   
        } catch (IOException ex) {
            LOG.info(ex.getMessage());
        }                
    }
    
    public static void tryToSleepFor(final long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            //  silent
        }
    }
    
    private String log(final Object[] event) {
        return String.format("[%2d, %d, %6.3f, %b, %2d, %2d, %2d]",
            (long) event[0],
            (long) event[1],
            (BigDecimal) event[2], 
            (boolean) event[3], 
            (long) event[4], 
            (long) event[5], 
            (long) event[6]);                            
    }
}
