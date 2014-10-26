package cz.muni.fi.sbapr.debs2014.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Lukáš Pástor
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Listeners {
    
    String[] classNames();
}
