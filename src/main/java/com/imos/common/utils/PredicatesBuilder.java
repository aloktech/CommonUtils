/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.util.function.Predicate;
import lombok.Getter;

/**
 *
 * @author alok.meher
 * @param <T>
 */
public class PredicatesBuilder<T> {

    @Getter
    private Predicate<T> predicate;

    public PredicatesBuilder and(Predicate<T> newPredicate) {
        if (predicate == null) {
            predicate = newPredicate;
        } else {
            predicate = predicate.and(newPredicate);
        }
        return this;
    }

    public PredicatesBuilder or(Predicate<T> newPredicate) {
        if (predicate == null) {
            predicate = newPredicate;
        } else {
            predicate = predicate.or(newPredicate);
        }
        return this;
    }
}
