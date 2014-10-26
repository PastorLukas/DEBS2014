package cz.muni.fi.sbapr.debs2014.subscriber;

import java.math.BigDecimal;
import java.math.MathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class GlobalLoadMedianSubscriber {
         
    private static final Logger LOG = 
            LoggerFactory.getLogger(GlobalLoadMedianSubscriber.class);
            
    public void update(double median) {
        
        BigDecimal roundedMedian = new BigDecimal(median, MathContext.DECIMAL32);
        LOG.info("median : {}", roundedMedian); 
    }
}
