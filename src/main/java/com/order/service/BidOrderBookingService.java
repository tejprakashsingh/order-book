package com.order.service;

import java.util.List;

import com.order.model.Bid;
import com.order.model.Transaction;

public interface BidOrderBookingService {
	
	public List<Bid> getAllBidOrders()  ;
  
	public Bid getOrderById(int id);
	
	public void saveOrUpdateBid(Bid bid)   ;
	
	public void delete(int id) ;
	
	public void execute(Transaction txn);
	
}
