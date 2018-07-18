/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author ELCOT
 */class FileProperties   {
	private String parentName;
	private String parentObj;
	// private Long size;
	private String name;
	private String lastModified;

	////////////// getter() and Setter()/////////
	void setParentObj(String obj){
		this.parentObj = obj;
	}
	void setParentName(String name){
		this.parentName = name;
	}

	void setLastModified(String time){
		this.lastModified = time;
	}

	void setName(String name){
		this.name = name;
	}

	String getLastModified(){
		return this.lastModified;
	}
	String getParentName(){
		return this.parentName;
	}

	String getName(){
		return this.name;
	}
	String getParentObj(){
		return this.parentObj;
	}
}
