package com.iotarch.farm.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class Sensor implements Serializable, Cloneable{

    String sensorId=null;

    String name;

    SensorStatus status;

    long timeStamp=-1;

    long updateTimeStamp;

    LocalDateTime localDateTime;

    public Sensor(String sensorId, String name, SensorStatus status, long timeStamp) {
        this.sensorId = sensorId;
        this.name = name;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public LocalDateTime getLocalDateTime() {
        if(timeStamp!=-1){
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneOffset.UTC);
        }
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }



    public Sensor() {
    }

    public long getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(long updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorStatus getStatus() {
        return status;
    }

    public void setStatus(SensorStatus status) {
        this.status = status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


}
