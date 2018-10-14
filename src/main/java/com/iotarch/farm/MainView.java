package com.iotarch.farm;

import com.iotarch.farm.entity.Customer;
import com.iotarch.farm.entity.CustomerService;
import com.iotarch.farm.entity.User;
import com.iotarch.farm.utility.StringHelper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.iotarch.farm.components.*;


@Route("")
public class MainView extends VerticalLayout implements BeforeEnterObserver{

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>();
    private final TextField filterText;
    private CustomerForm form = new CustomerForm(this);
    private Button logoutButton = new Button("Logout");

    @Autowired
    User user;

    public MainView(){

        filterText = new TextField();
        filterText.setPlaceholder("Filter by Name");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e->updateList());

        Button clearFilterTextBtn = new Button(
                new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        grid.setSizeFull();

        grid.addColumn(Customer::getFirstName).setHeader("First name");
        grid.addColumn(Customer::getLastName).setHeader("Last name");
        grid.addColumn(Customer::getStatus).setHeader("Status");

        grid.asSingleSelect().addValueChangeListener(c->form.setCustomer(c.getValue()));

        logoutButton.addClickListener(this::logout);

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        HorizontalLayout filterLayout = new HorizontalLayout(filterText,clearFilterTextBtn);


        HorizontalLayout toolbar = new HorizontalLayout(filterLayout,
                addCustomerBtn,logoutButton);

        HorizontalLayout main = new HorizontalLayout(grid, form);
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

        grid.setItems(service.findAll(filterText.getValue()));
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        if(user.isLogin()==false){

            event.rerouteTo(StringHelper.LOGIN);

        }
    }

}
