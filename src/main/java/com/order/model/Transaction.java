package com.order.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Transaction {  
 
@Getter
@Setter
private String txnType;  
 
@Getter
@Setter
private double price; 

@Getter
@Setter
private String orderType; 

@Getter
@Setter
private int quantity; 

java.util.Date date=new java.util.Date(); 

@Getter
@Setter
private Date inTime = date; 
 
}
