package cz.muni.fi.sbapr.debs2014.statement;

import cz.muni.fi.sbapr.debs2014.annotation.AnnotationProcessor;
import com.espertech.esper.client.EPAdministrator;
import cz.muni.fi.sbapr.debs2014.listener.GlobalLoadMedianListener;
import cz.muni.fi.sbapr.debs2014.subscriber.GlobalLoadAverageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class GlobalLoadMedianStatement extends AnnotationProcessor {
        
    private static final Logger LOG = 
            LoggerFactory.getLogger(GlobalLoadMedianStatement.class);
    private static final String statementName =
            GlobalLoadMedianStatement.class.getSimpleName();
    private static final String listenerName = 
            GlobalLoadMedianListener.class.getName();
    private static final String subscriberName =
            GlobalLoadAverageSubscriber.class.getName();
    
    private static final String statement 
            = "@Name('" + statementName + "') "
            + "@Description('Reports median of value property.') "
            + "@Listeners(classNames={'" + listenerName + "'}) " 
//            + "@Subscriber(className='" + subscriberName + "') "
            + "select "
//                + "*, "
//                + "count(*) as cnt, "
//                + "id, "
//                + "timestamp, "               
//                + "value, "
//                + "median(value) as median "
                + "median(value.doubleValue()) as median "            
            + "from "
                + "SensorEvent(property = true)"
//                + "LastHourWindow "
//                + "LoadSensorStream"
                + ".win:time(5 min) "
//                + ".win:time(1 min) "
//                + ".std:lastevent() "
//                + ".win:expr_batch(newest_event.timestamp != oldest_event.timestamp, false) "
//        + "group by "
//                + "plugId "
//        + "output "                 //  remove from statement (will calculate median only at output time)           
//                + "first "            
//                + "last "
//                + "snapshot "
//                + "every 1 second"
//                + "first every 1 seconds"            
//                + "last  every 1 seconds"            
        ;        
    
    public GlobalLoadMedianStatement(EPAdministrator administrator) {
        AnnotationProcessor.annotateStatement(administrator.createEPL(statement, statementName));        
    }                      
}
