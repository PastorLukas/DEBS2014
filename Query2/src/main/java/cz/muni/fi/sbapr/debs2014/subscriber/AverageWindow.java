package cz.muni.fi.sbapr.debs2014.subscriber;

import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Row-by-row update method preference : 
 * 1. EventType
 * 2. Map/EventType multi-row
 * 3. Object[]
 *  
 * @author Lukáš Pástor
 */
public class AverageWindow {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(AverageWindow.class);   
    private static final String className = 
            AverageWindow.class.getSimpleName();        
             
// Row-by-row delivery start, end indication
    
    /**
     * Called by the engine before delivering events to update methods
     * @param insertStreamLength    the number of events of the insert stream 
     *                              to be delivered
     * @param removeStreamLength    the number of events of the remove stream 
     *                              to be delivered
     */
//    public void updateStart(int insertStreamLength, int removeStreamLength) {
//        LOG.trace("updateStart i : {}, r : {}", insertStreamLength, removeStreamLength);
//    }
           
    /**
     * Called by the engine after delivering events
     */
//    public void updateEnd() {   
//        LOG.trace("updateEmd");
//    }
    
    
// Row-by-row Delivery :
    
    /**
     * The update method to support input stream delivery of wildcards (*) in
     * select clause representing all properties of SensorEvents in row-by-row
     * delivery mode.
     *
     * @param newEvent SensorEvent input stream event
     */
    public void update(final SensorEvent newEvent) {
        LOG.info(".I(T) - {}", newEvent.toStringPlain());
    }

    /**
     * The update method to support remove stream delivery of wildcards (*) in
     * select clause representing all properties of SensorEvents in row-by-row
     * delivery mode. Subscriber receives remove stream events if it provides a
     * method named updateRStream. The method must accept the same number and
     * types of parameters as the update method.
     *
     * @param oldEvent remove stream events
     */
    public void updateRStream(final SensorEvent oldEvent) {
        LOG.info(".R(T) - {}", oldEvent.toStringPlain());
    }
    
    
            
    /**
     * The update method to support delivery of select clause columns as an 
     * object array. Each item in the object array represents a column in the 
     * select clause. Row Delivery as Object Array.
     * @param event   columns in the select clause
     */    
    private void update(final Object[] event) {    
        //LOG.info(".Ii(O[]) - {}", event);
        LOG.info(".I(O[]) - {}", Arrays.toString(event));        
//        if (event != null && event.length > 0) {
//            LOG.info(".update(O[]) - {}", event);  
//        }
    }                  
    
    /**
     * The update method to support remove stream delivery of wildcards (*) in
     * select clause representing all properties of SensorEvents in row-by-row
     * delivery mode. Subscriber receives remove stream events if it provides a
     * method named updateRStream. The method must accept the same number and
     * types of parameters as the update method.
     *
     * @param event remove stream events
     */
    public void updateRStream(final Object[] event) {
        //LOG.info(".Rr(O[]) - {}", event);
        LOG.info(".R(O[]) - {}", Arrays.toString(event));        
//        if (event != null && event.length > 0) {
//            LOG.info(".updateRStream(O[]) - {}", event);
//        }
    }               
    
    
            
// Multi-Row Delivery  :                        
    
    /**
     * The update method to support input stream delivery of SensorEvent in
     * multi-row delivery mode.
     *
     * @param newEvents input stream events
     * @param oldEvents remove stream events
     */
    public void update(final SensorEvent[] newEvents, final SensorEvent[] oldEvents) {

        //LOG.trace(formatedLabel);
        if (newEvents != null && newEvents.length > 0) {
            for (SensorEvent newEvent : newEvents) {
                update(newEvent);                
            }
        }        
        if (oldEvents!= null && oldEvents.length > 0) {    
            for (SensorEvent oldEvent : oldEvents) {
                updateRStream(oldEvent);                                
            }
        } 
       // LOG.trace("----------------------------------------------------------\n");                       
    }  
    
    /**
     * The update method to support input stream delivery of multiple 
     * SensorEvents in multi-row delivery mode in form of Object[][].
     * @param insertStream  array of input stream SensorEvent events
     * @param removeStream  array of output stream SensorEvent events
     */
    public void update(final Object[][] insertStream, final Object[][] removeStream) {
                
        LOG.trace("---------------Average-Window----------------------");        
        if (insertStream != null && insertStream.length > 0) {            
            //int newEventsCount = insertStream.length;
            for (Object[] newEvent : insertStream) {
                update(newEvent);
            }
        }
        if (removeStream != null && removeStream.length > 0) {            
            //int oldEventsCount = removeStream.length;          
            for (Object[] oldEvent : removeStream) {         
                updateRStream(oldEvent);
            }
        }                       
        //LOG.trace("---------------------------------------------------\n");        
    }                         
}
