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
public class RenameOperation {
    private String oldName;
    private String newName;
    private String place;
    private String type;
// Getter and Setter
    public String getNewName() {
        return newName;
    }

    public String getOldName() {
        return oldName;
    }

    public String getPlace() {
        return place;
    }

    
    public String getType() {
        return type;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    
    
    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
