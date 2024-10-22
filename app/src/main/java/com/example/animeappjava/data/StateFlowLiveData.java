package com.example.animeappjava.data;

import androidx.lifecycle.LiveData;

import java.util.concurrent.atomic.AtomicReference;

public class StateFlowLiveData<T> extends LiveData<T> {

    private final AtomicReference<T> currentValue = new AtomicReference<>();
    private boolean isActive = false;
    private final ValueChangeListener<T> listener;

    public StateFlowLiveData(T initialValue, ValueChangeListener<T> listener) {
        this.currentValue.set(initialValue);
        this.listener = listener;
    }

    @Override
    protected void onActive() {
        super.onActive();
        isActive = true;

        new Thread(() -> {
            try {
                while (isActive) {
                    T value = currentValue.get();
                    postValue(value);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                // Handle interruption if needed
                e.printStackTrace();
            }
        }).start();

        // Register the listener
        listener.onValueChanged(newValue -> {
            currentValue.set(newValue);
        });
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        isActive = false;
        // Optionally, unregister the listener here if needed
    }

    // Interface for value change listener
    public interface ValueChangeListener<T> {
        void onValueChanged(ValueSetter<T> setter);
    }

    // Interface for setting the value
    public interface ValueSetter<T> {
        void setValue(T value);
    }

    // Getter for the value change listener
    public ValueChangeListener<T> getValueChangeListener() {
        return this.listener;
    }
}