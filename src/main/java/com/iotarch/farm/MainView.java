package com.iotarch.farm;

import com.iotarch.farm.entity.Customer;
import com.iotarch.farm.entity.CustomerService;
import com.iotarch.farm.entity.Sensor;
import com.iotarch.farm.entity.User;
import com.iotarch.farm.service.SensorService;
import com.iotarch.farm.utility.StringHelper;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import com.iotarch.farm.components.*;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


@Push
@Route("")
public class MainView extends VerticalLayout implements BeforeEnterObserver{

    private CustomerService service = CustomerService.getInstance();
    private Grid<Sensor> grid = new Grid<>();
    private final TextField filterText;
//    private CustomerForm form = new CustomerForm(this);
    private Button logoutButton = new Button("Logout");

    SensorService sensorService;

    Registration registration;
    BroadCaster broadCaster;

    @Autowired
    User user;

    SensorForm sensorForm;

    public MainView(@Autowired SensorService sensorService, @Autowired BroadCaster broadCaster, @Autowired SensorForm sensorForm){

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

        grid.setSizeFull();

        grid.addColumn(Sensor::getName).setHeader("Sensor name");
        grid.addColumn(Sensor::getStatus).setHeader("Status");
        grid.addColumn(new LocalDateTimeRenderer<>(Sensor::getLocalDateTime,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,
                FormatStyle.MEDIUM))).setHeader("Time");


//        grid.addColumn(Customer::getStatus).setHeader("Status");

        grid.asSingleSelect().addValueChangeListener(c->sensorForm.setSensor(c.getValue()));

        logoutButton.addClickListener(this::logout);

        Button addSensorBtn = new Button("Add new Sensor");
        addSensorBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            //form.setCustomer(new Customer());
            sensorForm.setSensor(new Sensor());
        });

        HorizontalLayout filterLayout = new HorizontalLayout(filterText,clearFilterTextBtn);


        HorizontalLayout toolbar = new HorizontalLayout(filterLayout,
                addSensorBtn,logoutButton);

        HorizontalLayout main = new HorizontalLayout(grid, sensorForm);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();

        add(toolbar,main);
        setHeight("100vh");
        updateList();

    }

    private void logout(ClickEvent<Button> buttonClickEvent) {

        user.setLogin(false);
        logoutButton.getUI().ifPresent(ui->ui.navigate(MainView.class));

    }

    public void updateList() {


        grid.setItems(sensorService.findAllSensors(filterText.getValue()));

       // grid.setItems(service.findAll(filterText.getValue()));

    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        if(user.isLogin()==false){

            event.rerouteTo(StringHelper.LOGIN);

        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UI ui = attachEvent.getUI();
        registration = broadCaster.register(message->{

            if(message.equals(StringHelper.UPDATELIST)){
                ui.access(()-> updateList());
            }
        });


    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        registration.remove();
        registration=null;
    }
}
