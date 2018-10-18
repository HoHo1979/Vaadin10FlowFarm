package com.iotarch.farm;

import com.vaadin.flow.shared.Registration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
@ApplicationScope
public class BroadCaster {

    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<String>> listeners = new LinkedList<>();

        public synchronized Registration register(
                Consumer<String> listener) {
            listeners.add(listener);

            return () -> {
                synchronized (BroadCaster.class) {
                    listeners.remove(listener);
                }
            };
        }

        public synchronized void broadcast(String message) {
            for (Consumer<String> listener : listeners) {
                executor.execute(() -> listener.accept(message));
            }
        }
}
