/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.debs2014.event;

import java.math.BigDecimal;

/**
 *
 * @author LuPa
 */
public class PlugAggregationEvent {
    
    private long houseId;       //  a unique identifier of a house where the household with the plug is located [32 bit unsigned integer value]
    private long householdId;   //  a unique identifier of a household (within a house) where the plug is located [32 bit unsigned integer value]
    private long plugId;        //  a unique identifier (within a household) of the smart plug [32 bit unsigned integer value]
    private BigDecimal value;   //  the measurement [32 bit floating point]

    public PlugAggregationEvent() {
    }

    public PlugAggregationEvent(long houseId, long householdId, long plugId, BigDecimal value) {
        this.houseId = houseId;
        this.householdId = householdId;
        this.plugId = plugId;
        this.value = value;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(long householdId) {
        this.householdId = householdId;
    }

    public long getPlugId() {
        return plugId;
    }

    public void setPlugId(long plugId) {
        this.plugId = plugId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }        

    @Override
    public String toString() {
        return "PlugAggregationEvent{" + "houseId=" + houseId + ", householdId=" + householdId + ", plugId=" + plugId + ", value=" + value + '}';
    }        
}
