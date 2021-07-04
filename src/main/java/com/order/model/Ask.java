package com.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
//mark class as an Entity   
@Entity  
//defining class name as Table name  
@Table  
public class Ask {  

//mark id as primary key  
@Id  
//defining id as column name  
@Column  
@Getter
@Setter
private int id;  

@Column  
@Getter
@Setter
private double price; 

@Column  
@Getter
@Setter
private int quantity; 


}
