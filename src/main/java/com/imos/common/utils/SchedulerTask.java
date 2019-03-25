/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 *
 * @author Pintu
 * @param <T>
 */
public class SchedulerTask<T> extends TimerTask {

    private final Consumer<T> consumer;
    private final T object;
    private Timer timer;

    public SchedulerTask(Consumer<T> consumer, T object) {
        this.consumer = consumer;
        this.object = object;
    }

    public SchedulerTask(Timer timer, Consumer<T> consumer, T object) {
        this.consumer = consumer;
        this.object = object;
        this.timer = timer;
    }

    @Override
    public void run() {
        consumer.accept(object);

        if (timer != null) {
            timer.cancel();
        }
    }
}
