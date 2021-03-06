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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.util.Random;

@Component
@UIScope
public class SensorForm extends FormLayout {


    TextField sensorId = new TextField("id");
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

        sensorId.setReadOnly(true);

        HorizontalLayout buttons = new HorizontalLayout(saveButton, deleteButton);

        saveButton.getElement().setAttribute("theme","primary");

        add(sensorId,name,status,buttons);

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

        service.save(sensor);
        broadCaster.broadcast(StringHelper.UPDATELIST);
        setSensor(null);
    }


}
