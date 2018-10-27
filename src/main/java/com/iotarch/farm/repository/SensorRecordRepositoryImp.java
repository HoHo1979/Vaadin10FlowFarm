package com.iotarch.farm.repository;

import com.iotarch.farm.entity.Sensor;
import com.iotarch.farm.entity.SensorRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Component
@ApplicationScope
public class SensorRecordRepositoryImp implements SensorRecordRepository {

    List<SensorRecord> sensorRecords = new ArrayList<>();

    SensorRepositoryImp sensorRepository;

    public SensorRecordRepositoryImp(@Autowired SensorRepositoryImp sensorRepository) {

        this.sensorRepository = sensorRepository;

        for (Sensor sensor : sensorRepository.findAll()) {

            String id = sensor.getSensorId();
            Random random = new Random();

            for (int i = 0; i < 30; i++) {

                SensorRecord sensorRecord =
                        new SensorRecord(random.nextLong(),random.nextLong(),id,
                                LocalDateTime.ofInstant(Instant.now().plusMillis(20000), ZoneId.systemDefault()));

                sensorRecords.add(sensorRecord);
            }

        }

    }

    @Override
    public List<SensorRecord> findAllRecordsBySensorId(String sensorId) {
        return sensorRecords.parallelStream().filter(s->s.getSensorId().equals(sensorId)).collect(Collectors.toList());
    }

    @Override
    public List<SensorRecord> findAllRecords() {
        return sensorRecords;
    }


}
