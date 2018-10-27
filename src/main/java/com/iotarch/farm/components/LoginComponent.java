package com.iotarch.farm.components;

import com.iotarch.farm.MainView;
import com.iotarch.farm.entity.User;
import com.iotarch.farm.utility.StringHelper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import sun.rmi.runtime.Log;


@Route(StringHelper.LOGIN)
public class LoginComponent extends HorizontalLayout {

    private TextField userName = new TextField("UserName");
    private TextField password = new TextField("Password");
    private Button loginButton = new Button("Login");

    @Autowired
    User user;


    public LoginComponent(){

        
        loginButton.addClickListener(this::clicked);

        add(userName,password,loginButton);

        setMargin(true);

    }


    private void clicked(ClickEvent<Button> buttonClickEvent) {

        checkIfhasAccessPermission();

    }


    public void checkIfhasAccessPermission(){

        if(userName.getValue().equals("abc") && password.getValue().equals("123")){



            user.setUserName("abc");
            user.setLogin(true);
            loginButton.getUI().ifPresent(ui->ui.navigate(MainView.class));


        }else{
            Notification.show("UserName and Password is incorrect",2000, Notification.Position.MIDDLE);

        }
    }


}
