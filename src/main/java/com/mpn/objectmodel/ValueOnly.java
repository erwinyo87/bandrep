/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpn.objectmodel;

/**
 *
 * @author ErwinYo
 */
public class ValueOnly {
    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ValueOnly(int value) {
        this.value = value;
    }
    
    
}
