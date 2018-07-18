/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author Administrator
 */
public class CrModDeOperations {
    private String operation;
    private String name;
    private String place;
    private String type;
// Getter and Setter 
    public String getName() {
        return name;
    }

    public String getOperation() {
        return operation;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
