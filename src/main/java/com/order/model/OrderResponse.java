package com.order.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
//mark class as an Entity   
@Component
public class OrderResponse {  
  
@Getter
@Setter
private String respStatus;  

  
@Getter
@Setter
private String msgTxt; 


}
