package com.iotarch.farm.components;

import com.iotarch.farm.BroadCaster;
import com.iotarch.farm.entity.Sensor;
import com.iotarch.farm.entity.SensorStatus;
import com.iotarch.farm.service.SensorService;
import com.iotarch.farm.utility.StringHelper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
@VaadinSessionScope
public class SensorForm extends FormLayout {

    TextField name = new TextField("Name");
    ComboBox<SensorStatus> status = new ComboBox<>("Status");
    private Binder<Sensor> binder = new Binder<>(Sensor.class);
    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Delete");

    Sensor sensor;

    BroadCaster broadCaster;

    SensorService service;

    public SensorForm(@Autowired SensorService service, @Autowired BroadCaster broadCaster){

        this.service = service;
        this.broadCaster = broadCaster;

        status.setItems(SensorStatus.values());

        saveButton.addClickListener(this::save);
        deleteButton.addClickListener(this::delete);


        HorizontalLayout buttons = new HorizontalLayout(saveButton, deleteButton);

        saveButton.getElement().setAttribute("theme","primary");

        add(name,status,buttons);

        binder.bindInstanceFields(this);

        setSensor(null);

    }

    public void setSensor(Sensor sensor) {

        this.sensor=sensor;
        binder.setBean(sensor);
        boolean enabled = sensor != null;
        saveButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
        if (enabled) {
            name.focus();
        }

    }

    private void delete(ClickEvent<Button> buttonClickEvent) {
    }

    private void save(ClickEvent<Button> buttonClickEvent) {
        sensor.setSensorId("B"+new Random().nextInt(1000));
        sensor.setTimeStamp(Instant.now().toEpochMilli());
        service.save(sensor);
        broadCaster.broadcast(StringHelper.UPDATELIST);
        setSensor(null);
    }


}
