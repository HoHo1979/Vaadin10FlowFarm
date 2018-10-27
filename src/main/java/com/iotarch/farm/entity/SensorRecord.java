package com.iotarch.farm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class SensorRecord implements Serializable,Cloneable{


    long dateTime;
    long temperature;
    long moisture;

    String sensorId;

    LocalDateTime localDateTime;

    public SensorRecord() {

    }

    public SensorRecord(long temperature, long moisture, String sensorId, LocalDateTime localDateTime) {
        this.temperature = temperature;
        this.moisture = moisture;
        this.sensorId = sensorId;
        this.localDateTime = localDateTime;
    }

    public long getDateTime() {
        return (localDateTime!=null)?localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli():null;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    public long getMoisture() {
        return moisture;
    }

    public void setMoisture(long moisture) {
        this.moisture = moisture;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}
