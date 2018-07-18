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
public class MoveOperation {
    private String name;
    private String from;
    private String to;
    private String type;
//  Getter and Setter 
    public String getFrom() {
        return from;
    }

    public String getName() {
        return name;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
