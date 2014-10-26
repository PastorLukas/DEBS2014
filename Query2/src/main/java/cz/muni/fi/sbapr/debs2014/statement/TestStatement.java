package cz.muni.fi.sbapr.debs2014.statement;

import cz.muni.fi.sbapr.debs2014.annotation.AnnotationProcessor;
import com.espertech.esper.client.EPAdministrator;
import cz.muni.fi.sbapr.debs2014.listener.TestListener;
import cz.muni.fi.sbapr.debs2014.subscriber.TestSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class TestStatement extends AnnotationProcessor {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(TestStatement.class);
    private static final String statementName =
            TestStatement.class.getSimpleName();
    private static final String listenerName = 
            TestListener.class.getName();
    private static final String subscriberName =
            TestSubscriber.class.getName();
        
    private static final String statement0 
            = "@Name('" + statementName + "') "
            + "@Description('Reports median of value property.') "
//            + "@Listeners(classNames={'" + listenerName + "'}) " 
            + "@Subscriber(className='" + subscriberName + "') "
            + "select "
//                + "distinct "
//                + "*, "
//                + "id, "
//                + "timestamp, "
//                + "value "                            
//                + "median(value), "
//                + "median(cast(value, Double)) as mcastValue "
//                + "median(value.doubleValue()) as mdoubleValue "
//                + "property, "
                + "plugId, "
                + "householdId, "           
                + "houseId, "                                    
//                + "count(distinct houseId) as cnt "
//                + "max(houseId) as max "
                + "size "
//                + "median(value) as median "                
            + "from "
//                + "SensorEvent "
                + "SensorEvent(property = true)"
//                + "LastHourWindow "
//                + "LoadSensorStream"
//                + ".win:time(10 min) "
//                + ".win:time(1 min) "
//                + ".std:lastevent() "
                + ".std:groupwin(plugId, householdId, houseId) "
//                + ".std:unique(houseId, householdId, plugId) "
                + ".std:size(plugId, householdId, houseId) "                
//                + ".win:expr_batch(newest_event.timestamp != oldest_event.timestamp, false) "
//        + "group by "
//                + "plugId "
        + "output "                 //  remove from statement (will calculate median only at output time)   
//                + "after "
//                + "first "            
//                + "last "
                + "snapshot "
                + "every "
                + "1  "
//                + "5  "
//                + "10 "
//                + "60 "                                
                + "seconds "
//                + "minutes "
//                + "hours " 
//          + "having houseId > max "
        + "order by "
                + "houseId "
                + "asc, "
                + "householdId "
                + "asc, "
                + "plugId "
                + "asc "
//                + "desc "
        ;
    
    private static final String statement1 
            = "@Name('" + statementName + "') "
            + "@Description('Reports median of value property.') "
//            + "@Listeners(classNames={'" + listenerName + "'}) " 
            + "@Subscriber(className='" + subscriberName + "') "
            + "context SegmentedByHouse "
            + "select "
//                + "distinct "
//                + "*, "
//                + "id, "
//                + "timestamp, "
//                + "value "                            
//                + "median(value), "
//                + "median(cast(value, Double)) as mcastValue "
//                + "median(value.doubleValue()) as mdoubleValue "
//                + "property, "
                + "plugId, "
                + "householdId, "           
                + "houseId, "                                    
//                + "count(distinct houseId) as cnt "
//                + "max(houseId) as max "
                + "size "
//                + "median(value) as median "                
            + "from "
                + "SensorEvent "
//                + "SensorEvent(property = true)"
//                + "LastHourWindow "
//                + "LoadSensorStream"
//                + ".win:time(10 min) "
//                + ".win:time(1 min) "
//                + ".std:lastevent() "
//                + ".std:groupwin(plugId, householdId, houseId) "
//                + ".std:unique(houseId, householdId, plugId) "
//                + ".std:size(houseId) "  
                + ".std:size(plugId, householdId, houseId) "                
//                + ".win:expr_batch(newest_event.timestamp != oldest_event.timestamp, false) "
//        + "group by "
//                + "plugId "
        + "output "                 //  remove from statement (will calculate median only at output time)   
//                + "after "
//                + "first "            
//                + "last "
                + "snapshot "
                + "every "
                + "1  "
//                + "5  "
//                + "10 "
//                + "60 "                                
                + "seconds "
//                + "minutes "
//                + "hours " 
//          + "having houseId > max "
        + "order by "
                + "houseId "
                + "asc, "
                + "householdId "
                + "asc, "
                + "plugId "
                + "asc "
//                + "desc "
        ;
    
    private static final String statement2 
            = "@Name('" + statementName + "') "
            + "@Description('Reports median of value property.') "
//            + "@Listeners(classNames={'" + listenerName + "'}) " 
            + "@Subscriber(className='" + subscriberName + "') "
            + "context SegmentedByHouse "
            + "select "
//                + "distinct "
//                + "*, "
//                + "id, "
//                + "timestamp, "
//                + "value "                            
//                + "median(value), "
//                + "median(cast(value, Double)) as mcastValue "
//                + "median(value.doubleValue()) as mdoubleValue "
//                + "property, "
//                + "plugId, "
//                + "householdId, "           
//                + "houseId, "                                    
//                + "count(distinct houseId) as cnt "
//                + "max(houseId) as max "            
                + "context.key1 as houseId, "
                + "count(*) as cnt "
//                + "size "
//                + "median(value) as median "                
            + "from "
                + "SensorEvent "
//                + "SensorEvent(property = true)"
//                + "LastHourWindow "
//                + "LoadSensorStream"
//                + ".win:time(10 min) "
//                + ".win:time(1 min) "
//                + ".std:lastevent() "
//                + ".std:groupwin(plugId, householdId, houseId) "
                + ".std:unique(householdId, plugId) "
//                + ".std:unique(houseId, householdId, plugId) " 
//                + ".std:size(houseId) "  
//                + ".std:size(plugId, householdId, houseId) "                
//                + ".win:expr_batch(newest_event.timestamp != oldest_event.timestamp, false) "
//        + "group by "
//                + "plugId "
//        + "output "                 //  remove from statement (will calculate median only at output time)   
//                + "after "
//                + "first "            
//                + "last "
//                + "snapshot "
//                + "every "
//                + "1  "
//                + "5  "
//                + "10 "
//                + "60 "                                
//                + "seconds "
//                + "minutes "
//                + "hours " 
//          + "having houseId > max "
//        + "order by "
//                + "context.key1 "
//                + "asc "
//                + "householdId "
//                + "asc, "
//                + "plugId "
//                + "asc "
//                + "desc "
        ;
    
    
    
    String statement3 = "context SegmentedByHouse select context.key1, count(*) from SensorEvent.std:unique(householdId, plugId)";    
          
    private static final String stream1 
        = "@Name('" + statementName + "') "
        + "@Description('Reports median of value property.') "
    //            + "@Listeners(classNames={'" + listenerName + "'}) " 
        + "@Subscriber(className='" + subscriberName + "') "
        + "context SegmentedByHouse "
        + "select "
    //                + "distinct "
    //                + "*, "
    //                + "id, "
    //                + "timestamp, "
    //                + "value "                            
    //                + "median(value), "
    //                + "median(cast(value, Double)) as mcastValue "
    //                + "median(value.doubleValue()) as mdoubleValue "
    //                + "property, "
    //                + "plugId, "
    //                + "householdId, "           
    //                + "houseId, "                                    
    //                + "count(distinct houseId) as cnt "
    //                + "max(houseId) as max "            
            + "context.key1 as houseId, "
            + "count(*) as cnt "
    //                + "size "
    //                + "median(value) as median "                
        + "from "
            + "SensorEvent "
    //                + "SensorEvent(property = true)"
    //                + "LastHourWindow "
    //                + "LoadSensorStream"
    //                + ".win:time(10 min) "
    //                + ".win:time(1 min) "
    //                + ".std:lastevent() "
    //                + ".std:groupwin(plugId, householdId, houseId) "
            + ".std:unique(householdId, plugId) "
    //                + ".std:unique(houseId, householdId, plugId) " 
    //                + ".std:size(houseId) "  
    //                + ".std:size(plugId, householdId, houseId) "                
    //                + ".win:expr_batch(newest_event.timestamp != oldest_event.timestamp, false) "
    //        + "group by "
    //                + "plugId "
    //        + "output "                 //  remove from statement (will calculate median only at output time)   
    //                + "after "
    //                + "first "            
    //                + "last "
    //                + "snapshot "
    //                + "every "
    //                + "1  "
    //                + "5  "
    //                + "10 "
    //                + "60 "                                
    //                + "seconds "
    //                + "minutes "
    //                + "hours " 
    //          + "having houseId > max "
    //        + "order by "
    //                + "context.key1 "
    //                + "asc "
    //                + "householdId "
    //                + "asc, "
    //                + "plugId "
    //                + "asc "
    //                + "desc "
    ;
    
    
    public TestStatement(EPAdministrator administrator) {
        AnnotationProcessor.annotateStatement(administrator.createEPL(statement1, statementName));                
    }            
    
}