package cz.muni.fi.sbapr.debs2014.io;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esperio.SendableBeanEvent;
import com.espertech.esperio.SendableEvent;
import com.espertech.esperio.csv.CSVInputAdapter;

/**
 *
 * @author LuPa
 */
public class CsvSensorEventInputAdapter extends CSVInputAdapter {

    private CsvSensorEventInputAdapterSpec spec;
    
    public CsvSensorEventInputAdapter(EPServiceProvider epService, CsvSensorEventInputAdapterSpec spec) {
        super(epService, spec);
        this.spec = spec;
    }            
}
