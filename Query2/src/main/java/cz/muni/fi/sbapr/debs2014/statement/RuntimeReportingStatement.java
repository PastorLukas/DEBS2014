package cz.muni.fi.sbapr.debs2014.statement;

import cz.muni.fi.sbapr.debs2014.annotation.AnnotationProcessor;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPAdministratorIsolated;
import cz.muni.fi.sbapr.debs2014.listener.RuntimeReportingListener;
import cz.muni.fi.sbapr.debs2014.subscriber.RuntimeReportingSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class RuntimeReportingStatement extends AnnotationProcessor {
       
    private static final Logger LOG = 
            LoggerFactory.getLogger(RuntimeReportingStatement.class);
    private static final String statementName =
            RuntimeReportingStatement.class.getSimpleName();
    private static final String listenerName = 
            RuntimeReportingListener.class.getName();
    private static final String subscriberName =
            RuntimeReportingSubscriber.class.getName();    
    
    private static final String statement 
            = "@Name('" + statementName + "') "
            + "@Description('Periodicly reports engine runtime.') "
//            + "@Listeners(classNames={'" + listenerName + "'}) "              
            + "@Subscriber(className='" + subscriberName + "') "
            + "select current_timestamp() as runtime "
            + "from pattern [every timer:interval(1 sec)] "
            ;
                               
    public RuntimeReportingStatement(EPAdministrator administrator) {
        AnnotationProcessor.annotateStatement(administrator.createEPL(statement, statementName));        
    }    
}