package com.iotarch.farm.repository;

import com.iotarch.farm.entity.Sensor;
import com.iotarch.farm.entity.SensorStatus;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

//@Repository
@Component
@ApplicationScope
public class SensorRepositoryImp implements SesnsorRepository {

//    List<Sensor> sensors = Collections.synchronizedList(new ArrayList<>());
    List<Sensor> sensors = new CopyOnWriteArrayList<Sensor>();

    public SensorRepositoryImp() {

        int x =10;
        String id;

        for (int i = 0; i < x; i++) {
            id=(i+1)+"";

            Instant instant = Instant.now();
            Sensor sensor = new Sensor("A"+id,"A"+id, SensorStatus.On,instant.toEpochMilli());
//            sensor.setLocalDateTime(LocalDateTime.ofInstant(instant,ZoneOffset.UTC));
            sensors.add(sensor);
        }



    }

    @Override
    public List<Sensor> findAll() {
        return sensors;
    }

    @Override
    public Optional<Sensor> findById(String sensorId) {
        return sensors.stream().filter(s->s.getSensorId().equals(sensorId)).findFirst();
    }

    @Override
    public Sensor save(Sensor sensor) {

        boolean matchedSensor=false;

        for (Sensor sensor1 : sensors) {
            if(sensor1.getSensorId().equals(sensor.getSensorId())){
                sensor1.setName(sensor.getName());
                sensor1.setStatus(sensor.getStatus());
                sensor1.setUpdateTimeStamp(Instant.now().toEpochMilli());
                sensor = sensor1;
                matchedSensor=true;
                break;
            }
        }

        if(matchedSensor==false){
            sensors.add(sensor);
        }

        return sensor;
    }
}
