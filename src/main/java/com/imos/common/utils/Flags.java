/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author p
 */
@Setter
@Getter
public class Flags {

    private boolean readInput;
    private String inputMsg = "";
    private boolean readError;
    private String errMsg = "";
}
