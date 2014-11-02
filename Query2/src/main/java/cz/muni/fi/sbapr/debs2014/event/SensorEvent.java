package cz.muni.fi.sbapr.debs2014.event;

import java.math.BigDecimal;

/*
    For the DEBS 2014 Grand Challenge we assume a hierarchical structure with a 
    house, identified by a unique house id, being the topmost entity. Every 
    house contains one or more households, identified by a unique household id 
    (within a house). Every household contains one or more smart plugs, each 
    identified by a unique plug id (within a household). Every smart plug 
    contains exactly two sensors:
        (1) load sensor measuring current load with Watt as unit 
        (2) work sensor measuring total accumulated work since 
            the start (or reset) of the sensor with kWh as unit.

    First event timestamp : 1377986401
*/

/**
 *
 * @author Lukáš Pástor
 */
public class SensorEvent {                              
                                //  The schema of the base stream   :
    private long id;            //  a unique identifier of the measurement [32 bit unsigned integer value]
    private long timestamp;     //  timestamp of measurement (epoch) [32 bit unsigned integer value]
    private BigDecimal value;   //  the measurement [32 bit floating point]
    private boolean property;   //  type of the measurement: 0 for work or 1 for load [boolean]
    private long plugId;        //  a unique identifier (within a household) of the smart plug [32 bit unsigned integer value]
    private long householdId;   //  a unique identifier of a household (within a house) where the plug is located [32 bit unsigned integer value]
    private long houseId;       //  a unique identifier of a house where the household with the plug is located [32 bit unsigned integer value]
        
    public SensorEvent() {
    }
            
    public SensorEvent(long id, long timestamp, BigDecimal value, boolean property, long plugId, long householdId, long houseId) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.property = property;
        this.plugId = plugId;
        this.householdId = householdId;
        this.houseId = houseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }   

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value2) {
        this.value = value2;
    }
    
    public boolean getProperty() {
        return property;
    }

    public void setProperty(boolean property) {
        this.property = property;
    }

    public long getPlugId() {
        return plugId;
    }

    public void setPlugId(long plugId) {
        this.plugId = plugId;
    }

    public long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(long householdId) {
        this.householdId = householdId;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }      

    @Override
    public String toString() {               
        return "SensorEvent{" + "id=" + id + ", timestamp=" + timestamp + ", value=" + value + ", property=" + property + ", plugId=" + plugId + ", householdId=" + householdId + ", houseId=" + houseId + '}';
    }
}
