package cz.muni.fi.sbapr.debs2014.io;

import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapterSpec;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lukáš Pástor
 */
public class CsvSensorEventInputAdapterSpec extends CSVInputAdapterSpec {
    
    private File file;

        public CsvSensorEventInputAdapterSpec(File file) {
            super(new AdapterInputSource(file), "SensorEvent");
            this.file = file;

            String[] sensorEventPropertyOrder = new String[]{"id", "timestamp", "value", "property", "plugId", "householdId", "houseId"};

            
//            Map<String, Object> sensorEventPropertyTypes = new HashMap<String, Object>();
//
//            sensorEventPropertyTypes.put("id", Long.class);
//            sensorEventPropertyTypes.put("timestamp", Long.class);
//            sensorEventPropertyTypes.put("value", BigDecimal.class);
//            sensorEventPropertyTypes.put("property", Boolean.class);
//            sensorEventPropertyTypes.put("plugId", Long.class);
//            sensorEventPropertyTypes.put("householdId", Long.class);
//            sensorEventPropertyTypes.put("houseId", Long.class);

            setPropertyOrder(sensorEventPropertyOrder);
//            setPropertyTypes(sensorEventPropertyTypes);

            setTimestampColumn("timestamp");
            setUsingExternalTimer(true);
        }
        
        public File getFile() {
            
                return this.file;
        }
    
}
