package cz.muni.fi.sbapr.debs2014.statement;

import cz.muni.fi.sbapr.debs2014.annotation.AnnotationProcessor;
import com.espertech.esper.client.EPAdministrator;
import cz.muni.fi.sbapr.debs2014.listener.SensorEventReportingListener;
import cz.muni.fi.sbapr.debs2014.subscriber.SensorEventReportingSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class SensorEventReportingStatement extends AnnotationProcessor {
        
    private static final Logger LOG = 
            LoggerFactory.getLogger(SensorEventReportingStatement.class);
    private static final String statementName =
            SensorEventReportingStatement.class.getSimpleName();
    private static final String listenerName = 
            SensorEventReportingListener.class.getName();
    private static final String subscriberName =
            SensorEventReportingSubscriber.class.getName();        
    
    private static final String statement 
            = "@Name('" + statementName + "') "
            + "@Description('Reports every sensor event send to engine.') "
//            + "@Listeners(classNames={'" + listenerName + "'}) " 
            + "@Subscriber(className='" + subscriberName + "') "
            + "select * from SensorEvent ";        
    
    public SensorEventReportingStatement(EPAdministrator administrator) {
        AnnotationProcessor.annotateStatement(administrator.createEPL(statement, statementName));        
    }
}
