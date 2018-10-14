package com.iotarch.farm.components;

import com.iotarch.farm.MainView;
import com.iotarch.farm.entity.Customer;
import com.iotarch.farm.entity.CustomerService;
import com.iotarch.farm.entity.CustomerStatus;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;

public class CustomerForm extends FormLayout{

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    private Binder<Customer> binder = new Binder<>(Customer.class);
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MainView mainView;

    public CustomerForm(MainView mainView) {

        this.mainView = mainView;

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());

        status.setItems(CustomerStatus.values());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        save.getElement().setAttribute("theme","primary");

        add(firstName,lastName,status,buttons);

        binder.bindInstanceFields(this);

        setCustomer(null);

    }

    public void setCustomer(Customer customer){
        this.customer=customer;
        binder.setBean(customer);
        boolean enabled = customer != null;
        save.setEnabled(enabled);
        delete.setEnabled(enabled);
        if (enabled) {
            firstName.focus();
        }
    }


    private void delete() {
        service.delete(customer);
        mainView.updateList();
        setCustomer(null);
    }

    private void save() {
        service.save(customer);
        mainView.updateList();
        setCustomer(null);
    }

}
