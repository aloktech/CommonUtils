/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.db.utils;

/**
 *
 * @author p
 */
public class ConfigException extends BaseException {

    public ConfigException() {
        super();
    }
    
    public ConfigException(String message) {
        super(message);
    }
    
    public ConfigException(Throwable throwable) {
        super(throwable);
    }
    
    public ConfigException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
