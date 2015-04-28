package cz.muni.fi.sbapr.debs2014.subscriber;

import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import cz.muni.fi.sbapr.debs2014.util.Formatter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Row-by-row update method preference : 1. EventType 2. Map/EventType multi-row
 * 3. Object[]
 *
 * @author Lukáš Pástor
 */
public class TimeWindowRemoveStream {

    private static final Logger LOG
            = LoggerFactory.getLogger(TimeWindowRemoveStream.class);
    private static final String className
            = TimeWindowRemoveStream.class.getSimpleName();
    private final String label;
    private final String formatedLabel;
    
    public TimeWindowRemoveStream(String label) {
        this.label = label;
        this.formatedLabel = Formatter.alignToCenter(label, 50, '-');    
    }
                
    /**
     * Called by the engine before delivering events to update methods
     *
     * @param insertStreamLength the number of events of the insert stream to be
     * delivered
     * @param removeStreamLength the number of events of the remove stream to be
     * delivered
     */
    public void updateStart(int insertStreamLength, int removeStreamLength) {
        LOG.trace(formatedLabel);        
        //LOG.trace("updateStart i : {}, r : {}", insertStreamLength, removeStreamLength);
    }
    /**
     * Called by the engine after delivering events
     */
    public void updateEnd() {
        LOG.trace(formatedLabel);
        //LOG.trace("updateEmd");
    }
    
        
// Row-by-row Delivery :               
    /**
     * The update method to support input stream delivery of explicit select
     * clause representing all properties of SensorEvents in row-by-row delivery
     * mode.
     *
     * @param id a unique identifier of the measurement
     * @param timestamp timestamp of measurement (epoch)
     * @param value the measurement
     * @param property type of the measurement: 0 for work or 1 for load
     * @param plugId a unique identifier (within a household)
     * @param householdId a unique identifier of a household (within a house)
     * @param houseId a unique identifier of a house
     */
    public void update(
            final long id, 
            final long timestamp, 
            final BigDecimal value, 
            final boolean property, 
            final long plugId, 
            final long householdId, 
            final long houseId
    ) {
        LOG.info(".update(X) - [{}, {}, {}, {}, {}, {}, {}]", 
                id, 
                timestamp, 
                value, 
                property, 
                plugId, 
                householdId, 
                houseId);
    }

    /**
     * The update method to support remove stream delivery of explicit select
     * clause representing all properties of SensorEvents in row-by-row delivery
     * mode. Subscriber receives remove stream events if it provides a method
     * named updateRStream. The method must accept the same number and types of
     * parameters as the update method.
     *
     * @param id a unique identifier of the measurement
     * @param timestamp timestamp of measurement (epoch)
     * @param value the measurement
     * @param property type of the measurement: 0 for work or 1 for load
     * @param plugId a unique identifier (within a household)
     * @param householdId a unique identifier of a household (within a house)
     * @param houseId a unique identifier of a house
     */
    public void updateRStream(
            final long id, 
            final long timestamp, 
            final BigDecimal value, 
            final boolean property, 
            final long plugId, 
            final long householdId,
            final long houseId
    ) {
        LOG.info(".updateRStream(X) - [{}, {}, {}, {}, {}, {}, {}]", 
                id, 
                timestamp, 
                value, 
                property, 
                plugId, 
                householdId, 
                houseId);
    }

    
  
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
     * select clause.
     *
     * @param event columns in the select clause
     */
    private void update(final Object[] event) {              
        LOG.info(".I(O[]) - {}", event);
//        if (event != null && event.length > 0) {
//            LOG.info(".update(O[]) - {}", Arrays.toString(event));
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
        LOG.info(".R(O[]) - {}", Arrays.toString(event));        
//        if (event != null && event.length > 0) {
//            LOG.info(".updateRStream(O[]) - {}", event);
//        }
    }

    
    
    /**
     * The update method to support delivery of select clause columns as an map
     * representation of each row. Each column in the select clause is then made
     * an entry in the resulting Map. The Map keys are the column name if
     * supplied, or the expression string itself for columns without a name.
     *
     * @param row input stream event
     */
    private void update(final Map row) {
        LOG.info(".I(M) - {}", row.toString());
    }

    /**
     * The update method to support remove stream delivery in form of Map
     * representing all properties of SensorEvents in row-by-row delivery mode.
     * Subscriber receives remove stream events if it provides a method named
     * updateRStream. The method must accept the same number and types of
     * parameters as the update method.
     *
     * @param row remove stream event
     */
    private void updateRStream(final Map row) {
        LOG.info(".R(M) - {}", row.toString());
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

        LOG.trace(formatedLabel);
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
     *
     * @param insertStream array of input stream SensorEvent events
     * @param removeStream array of output stream SensorEvent events
     */
    public void update(final Object[][] insertStream, final Object[][] removeStream) {             
        LOG.trace(formatedLabel);        
        if (insertStream != null && insertStream.length > 0) {                      
            for (Object[] newEvent : insertStream) {   
                update(newEvent);                                
            }
        }
        
        if (removeStream != null && removeStream.length > 0) {      
            for (Object[] oldEvent : removeStream) {
                updateRStream(oldEvent);                
            }
        }
        LOG.trace("");        
    }        
    
}
