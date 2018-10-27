package com.iotarch.farm;

import com.iotarch.farm.entity.Sensor;
import com.iotarch.farm.entity.SensorRecord;
import com.iotarch.farm.entity.User;
import com.iotarch.farm.security.SecurityUtils;
import com.iotarch.farm.service.SensorRecordService;
import com.iotarch.farm.service.SensorService;
import com.iotarch.farm.utility.StringHelper;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import com.iotarch.farm.components.*;

import javax.validation.constraints.Null;
import java.nio.file.AccessDeniedException;
import java.security.Security;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import static com.iotarch.farm.utility.StringHelper.ADDNEWSENSOR;
import static com.iotarch.farm.utility.StringHelper.LOGOUT;


@Push
@Route("")
public class MainView extends VerticalLayout implements BeforeEnterObserver{


    private Grid<Sensor> grid = new Grid<>();
    private Grid<SensorRecord> recordGrid = new Grid<>();

    private final TextField filterText;
    private Button logoutButton = new Button(LOGOUT);

    SensorService sensorService;

    SensorRecordService sensorRecordService;

    Registration registration;
    BroadCaster broadCaster;

    List<SensorRecord> recordList = new ArrayList<>();

//    ListDataProvider<SensorRecord> sensorRecordListDataProvider = new ListDataProvider(recordList);

    @Autowired
    User user;

    SensorForm sensorForm;

    public MainView(@Autowired SensorService sensorService,
                    @Autowired SensorRecordService sensorRecordService,
                    @Autowired BroadCaster broadCaster,
                    @Autowired SensorForm sensorForm){

        this.sensorRecordService = sensorRecordService;
        this.sensorService = sensorService;
        this.broadCaster = broadCaster;
        this.sensorForm = sensorForm;


        filterText = new TextField();
        filterText.setPlaceholder("Filter by Sensor Name");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e->updateList());

        Button clearFilterTextBtn = new Button(
                new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> filterText.clear());


        recordGrid.setSizeFull();

        recordGrid.addColumn(SensorRecord::getMoisture).setHeader("Moisture");
        recordGrid.addColumn(SensorRecord::getTemperature).setHeader("Temperature");
        recordGrid.addColumn(new LocalDateTimeRenderer<>(SensorRecord::getLocalDateTime,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,
                FormatStyle.MEDIUM))).setHeader("Time");

//        recordGrid.setDataProvider(sensorRecordListDataProvider);


        grid.setSizeFull();

        grid.addColumn(Sensor::getSensorId).setHeader("Sensor id");
        grid.addColumn(Sensor::getName).setHeader("Sensor name");
        grid.addColumn(Sensor::getStatus).setHeader("Status");
        grid.addColumn(new LocalDateTimeRenderer<>(Sensor::getLocalDateTime,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,
                FormatStyle.MEDIUM))).setHeader("Time");


        grid.asSingleSelect().addValueChangeListener(c->{

                sensorForm.setSensor(c.getValue());

                //Grid has deselected = null , so condition need to apply in order to prevent NPE
                if(c.getValue()!=null && c.getValue().getSensorId()!=null) {
                    recordList = sensorRecordService.findAllRecordsBySensorId(c.getValue().getSensorId());
                    UI.getCurrent().access(() -> recordGrid.setItems(recordList));
                }
        });

        logoutButton.addClickListener(this::logout);

        Button addSensorBtn = new Button(ADDNEWSENSOR);
        addSensorBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            sensorForm.setSensor(new Sensor());
        });

        HorizontalLayout filterLayout = new HorizontalLayout(filterText,clearFilterTextBtn);


        HorizontalLayout toolbar = new HorizontalLayout(filterLayout,
                addSensorBtn,logoutButton);

        HorizontalLayout main = new HorizontalLayout(grid, sensorForm);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();

        add(toolbar,main,recordGrid);
        setHeight("100vh");
        updateList();

    }

    private void logout(ClickEvent<Button> buttonClickEvent) {

        user.setLogin(false);
        logoutButton.getUI().ifPresent(ui->ui.navigate(MainView.class));

    }

    public void updateList() {


        grid.setItems(sensorService.findAllSensors(filterText.getValue()));


    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {

//        if(!SecurityUtils.isAccessGranted(event.getNavigationTarget())){
//            event.rerouteToError(AccessDeniedException.class);
//        }

        if(user.isLogin()==false){

            event.rerouteTo(StringHelper.LOGIN);

        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {

        UI ui = attachEvent.getUI();
        registration = broadCaster.register(message->{

            if(message.equals(StringHelper.UPDATELIST)){
                ui.access(()-> updateList());
            }
        });


    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
//        super.onDetach(detachEvent);
        registration.remove();
        registration=null;
//        getUI().get().navigate(StringHelper.LOGIN);
    }
}
