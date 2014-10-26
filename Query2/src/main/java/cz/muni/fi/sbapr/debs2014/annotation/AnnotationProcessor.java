package cz.muni.fi.sbapr.debs2014.annotation;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.UpdateListener;
import java.lang.annotation.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class AnnotationProcessor {    
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(AnnotationProcessor.class);
    //private static final String Subscriber = "cz.muni.fi.sbapr.debs2014.subscriber.";    
    
    public static EPStatement annotateStatement(EPStatement statement) {
            
        if (statement == null) 
            throw new NullPointerException("statement");
        
        for (Annotation annotation : statement.getAnnotations()) {
            if (annotation instanceof Subscriber) {

                Subscriber subscriber = (Subscriber) annotation; 
                
                Object obj;
                try {
                    obj = getClassInstance(subscriber.className());
                    statement.setSubscriber(obj);
                } catch (final Exception ex) {
                    LOG.info("Subscriber : " + subscriber.className() + " not found.");                                        
                }             

            } else if (annotation instanceof Listeners) {

                Listeners listeners = (Listeners) annotation;
                for (String className : listeners.classNames()) {
                    Object obj;
                    try {
                        obj = getClassInstance(className);            
                        if (obj instanceof StatementAwareUpdateListener) {
                            statement.addListener((StatementAwareUpdateListener) obj);
                        } else {
                            statement.addListener((UpdateListener) obj);
                        }
                    } catch (final Exception ex) {
                        LOG.info("Listener : " + className + " not found.");                                                
                    }                    
                }
            }
        }
        return statement;
    }
    
    private static Object getClassInstance(String className) throws Exception {                       
        Class clazz = Class.forName(className);
        return clazz.newInstance();              
    }        
}
