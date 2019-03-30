/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.db.utils;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author p
 */
public class BaseException extends Exception {

    @Getter
    private final List<String> exceptions = new ArrayList<>();

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        
        addException(message);
    }

    public BaseException(Throwable throwable) {
        super(throwable);

        addException(throwable);
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);

        addException(throwable);
    }

    private void addException(String message) {
        exceptions.add(message + " : " + this.getClass().getName());
    }

    private void addException(Throwable th) {
        while (th != null) {
            exceptions.add(th.getMessage() + " : " + th.getClass().getName());
            th = th.getCause();
        }
    }
}
