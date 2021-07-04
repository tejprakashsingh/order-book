package com.order.service;

import java.util.List;

import com.order.model.Ask;
import com.order.model.Transaction;


public interface AskOrderBookingService {
	
	public List<Ask> getAllAskOrders() ;  
	
	public Ask getOrderById(int id)  ; 
	
	public void saveOrUpdateAsk(Ask ask);   
	
	public void delete(int id) ;
	
	public void execute(Transaction txn);
}
