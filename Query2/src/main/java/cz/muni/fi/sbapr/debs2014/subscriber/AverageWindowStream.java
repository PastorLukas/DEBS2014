package cz.muni.fi.sbapr.debs2014.subscriber;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Row-by-row update method preference : 1. EventType 2. Map/EventType multi-row
 * 3. Object[]
 *
 * @author Lukáš Pástor
 */
public class AverageWindowStream {

    private static final Logger LOG
            = LoggerFactory.getLogger(AverageWindowStream.class);
    private static final String className
            = AverageWindowStream.class.getSimpleName();

    private static final Map<String, String> deliveryT = new HashMap<>();
    private static final String[] deliveryType
            = {"[rbr-i] : ", "[rbr-r] : ", "[mr -i] : ", "[mr -r] : "};

    private static final String INPUT_STREAM = "is";
    private static final String REMOVE_STREAM = "rs";

    private static final String ROW_BY_ROW_I = "[rbr-i] : ";
    private static final String ROW_BY_ROW_R = "[rbr-r] : ";
    private static final String MULTI_ROW_I = "[mr -i] : ";
    private static final String MULTI_ROW_R = "[mr -r] : ";

    private static final String PLAIN
            = "{{}, {}, {}, {}, {}, {}, {}}";
    private static final String FULL
            = "id : {}, "
            + "timestamp : {}, "
            + "value : {}, "
            + "property : {}, "
            + "plugId : {}, "
            + "householdId : {}, "
            + "houseId : {} ";

    /**
     * Called by the engine before delivering events to update methods
     *
     * @param insertStreamLength the number of events of the insert stream to be
     * delivered
     * @param removeStreamLength the number of events of the remove stream to be
     * delivered
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
            final long id, final long timestamp, final BigDecimal value, final boolean property, final long plugId, final long householdId, final long houseId
    ) {
        LOG.info(".update(X) - [{}, {}, {}, {}, {}, {}, {}]", id, timestamp, value, property, plugId, householdId, houseId);
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
            final long id, final long timestamp, final BigDecimal value, final boolean property, final long plugId, final long householdId, final long houseId
    ) {
        LOG.info(".updateRStream(X) - [{}, {}, {}, {}, {}, {}, {}]", id, timestamp, value, property, plugId, householdId, houseId);
    }

    /**
     * The update method to support input stream delivery of wildcards (*) in
     * select clause representing all properties of SensorEvents in row-by-row
     * delivery mode.
     *
     * @param newEvent SensorEvent input stream event
     */
    public void update(final SensorEvent newEvent) {
        LOG.info(".update(T) - {}", newEvent.toStringPlain());
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
        LOG.info(".updateRStream(T) - {}", oldEvent.toStringPlain());
    }

    /**
     * The update method to support delivery of select clause columns as an
     * object array. Each item in the object array represents a column in the
     * select clause.
     *
     * @param event columns in the select clause
     */
    private void update(final Object[] event) {

        LOG.info(".update(O[]) - {}", Arrays.toString(event));
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

        LOG.info(".updateRStream(O[]) - {}", Arrays.toString(event));        
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
//    public void update(final Map row) {
//        LOG.info(".update(M) - {}", row.toString());
//    }

    /**
     * The update method to support remove stream delivery in form of Map
     * representing all properties of SensorEvents in row-by-row delivery mode.
     * Subscriber receives remove stream events if it provides a method named
     * updateRStream. The method must accept the same number and types of
     * parameters as the update method.
     *
     * @param row remove stream event
     */
    public void updateRStream(final Map row) {
        LOG.info(".updateRStream(M) - {}", row.toString());
    }

// Multi-Row Delivery  :    
    /**
     * The update method to support input stream delivery of SensorEvent in
     * multi-row delivery mode.
     *
     * @param newEvents input stream events
     * @param oldEvents remove stream events
     */
//    public void update(final SensorEvent[] newEvents, final SensorEvent[] oldEvents) {
//
//        LOG.trace("[{}]-{} update(SensorEvent[])", className, MULTI_ROW_I);
//        if (newEvents != null && newEvents.length > 0) {
//            LOG.info("[{}]-{} newEvents.", className, MULTI_ROW_I);
//            int newEventsCount = newEvents.length;
//            for (SensorEvent newEvent : newEvents) {
//                log(newEvent, MULTI_ROW_I, newEventsCount, PLAIN);
//            }
//        }
//
//        if (oldEvents != null && oldEvents.length > 0) {
//            LOG.info("[{}]-{} oldEvents.", className, MULTI_ROW_R);
//            int oldEventsCount = oldEvents.length;          
//            for (SensorEvent oldEvent : oldEvents) {
//                log(oldEvent, MULTI_ROW_R, oldEventsCount, PLAIN);
//            }
//        }
//    }             
    /**
     * The update method to support input stream delivery of multiple
     * SensorEvents in multi-row delivery mode in form of Object[][].
     *
     * @param insertStream array of input stream SensorEvent events
     * @param removeStream array of output stream SensorEvent events
     */
    public void update(final Object[][] insertStream, final Object[][] removeStream) {
        
        LOG.trace("---------------------Input/Output----------------------");          
        if (insertStream != null && insertStream.length > 0) {            
            int newEventsCount = insertStream.length;
            for (Object[] newEvent : insertStream) {
                LOG.info(".InputStream  - {}", newEvent);                
            }
        }        
        if (removeStream != null && removeStream.length > 0) {            
            int oldEventsCount = removeStream.length;          
            for (Object[] oldEvent : removeStream) {
                LOG.info(".OutputStream - {}", oldEvent);                
            }
        } 
        LOG.trace("-------------------------------------------------------\n");                
    }
    /**
     * The update method to support input stream delivery of multiple
     * SensorEvent in multi-row delivery mode in form of Map[].
     *
     * @param insertStream array of input SensorEvent events
     * @param removeStream array of output stream SensorEvent events
     */
//    public void update(final Map[] insertStream, final Map[] removeStream) {
//        LOG.trace("[{}]-{} update(Map[])", className, MULTI_ROW_I);
//    }
    private void log(
            final SensorEvent sensorEvent, final String deliveryType, final String logType
    ) {
        if (sensorEvent == null) {
            LOG.info("[{}]-{} : Event is null.", className, deliveryType);
            return;
        }

        LOG.info(
                "[{}]-"
                + deliveryType
                + logType, className, sensorEvent.getId(), sensorEvent.getTimestamp(), sensorEvent.getValue(), sensorEvent.getProperty(), sensorEvent.getPlugId(), sensorEvent.getHouseholdId(), sensorEvent.getHouseId()
        );
    }

    private void log(
            final SensorEvent sensorEvent, final String deliveryType, final int rowCount, final String logFormatType
    ) {

        if (sensorEvent == null) {
            LOG.info("[{}]-{} : Event is null.", className, deliveryType);
            return;
        }

        LOG.info(
                "[{}]-"
                + deliveryType
                + logFormatType, className, sensorEvent.getId(), sensorEvent.getTimestamp(), sensorEvent.getValue(), sensorEvent.getProperty(), sensorEvent.getPlugId(), sensorEvent.getHouseholdId(), sensorEvent.getHouseId()
        );
    }

//    public void update(final SensorEvent sensorEvent) {
//        LOG.trace("[{}] - Update single", className);
//        if (sensorEvent == null) {
//            LOG.info("[{}] - Received event is null.", className);            
//            return;
//        }
//        
//        log(PLAIN_FULL_LOG, 
//            sensorEvent.getId(),
//            sensorEvent.getTimestamp(),
//            sensorEvent.getValue(),
//            sensorEvent.getProperty(),
//            sensorEvent.getPlugId(),
//            sensorEvent.getHouseholdId(),
//            sensorEvent.getHouseId());
//        
////        update(
////            sensorEvent.getId(),
////            sensorEvent.getTimestamp(),
////            sensorEvent.getValue(),
////            sensorEvent.getProperty(),
////            sensorEvent.getPlugId(),
////            sensorEvent.getHouseholdId(),
////            sensorEvent.getHouseId()
////        );
//    }
//    public void update
//        (
//            final long id,
//            final long timestamp,
//            final BigDecimal value,
//            final Boolean property,
//            final long plugId,
//            final long householdId,
//            final long houseId
//        ) 
//    {       
//        LOG.info
//        (
//            "[{}] - "
////            +   "{}, "            
////            +   "{}, "
////            +   "{}, "
////            +   "{}, "
////            +   "{}, "
////            +   "{}, "
//            +   "id : {} "
//            +   "timestamp : {} "                                
//            +   "value : {} "
//            +   "property, : {} "
//            +   "plugId : {} "                                       
//            +   "householdId {} "
//            +   "houseId {} "
//            ,   className
//            ,   id
//            ,   timestamp
//            ,   value
//            ,   property
//            ,   plugId
//            ,   householdId
//            ,   houseId
//        );                   
//    }
//          
//    public void log
//        (
//            final String pattern,
//            final long id,
//            final long timestamp,
//            final BigDecimal value,
//            final Boolean property,
//            final long plugId,
//            final long householdId,
//            final long houseId                          
//        )
//    {
//        LOG.info
//        (
//            "[{}] - " + pattern
//            ,   className
//            ,   id
//            ,   timestamp
//            ,   value
//            ,   property
//            ,   plugId
//            ,   householdId
//            ,   houseId
//        );                                      
//    }
}
