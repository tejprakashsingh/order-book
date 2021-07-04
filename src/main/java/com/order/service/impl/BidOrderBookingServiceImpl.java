package com.order.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.order.constant.OrderConstant;
import com.order.model.Bid;
import com.order.model.OrderResponse;
import com.order.model.Transaction;
import com.order.repository.BidRepository;
import com.order.service.BidOrderBookingService;

@Service
@ComponentScan({"com.*"})
public class BidOrderBookingServiceImpl implements BidOrderBookingService{
	

	@Autowired
	BidRepository bidRepo;
	
	@Autowired
	OrderResponse ordResp;
	
	Logger logger = LoggerFactory.getLogger(BidOrderBookingServiceImpl.class);
	
	
	public void execute(Transaction txn) {
		
		List<Bid> l = new ArrayList<Bid>();
		Bid b = new Bid();
		String opderType = txn.getOrderType();
		int txnQty = txn.getQuantity();
		
		l= getAllBidOrders();
		
		//l.forEach(bid ->logger.info("Reading to Bid : " + bid.getPrice()));
		
		if(opderType != null && opderType.equalsIgnoreCase(OrderConstant.MARKET)) {
	  		b = (Bid)l.get(0);
	  		logger.info("Reading higest Bid ID : " + b.getId());
	  		logger.info("Reading higest Bid Price : " + b.getPrice());
	  		logger.info("Reading higest Bid Qty : " + b.getQuantity());
	  		
	  		checkAndUpdateBidStock( b ,  txnQty);
	  	  }else if(opderType != null && opderType.equalsIgnoreCase(OrderConstant.LIMIT)) {
	  		
	  		  // fetch the BID price from request
	  		double txnPrice= txn.getPrice();
	  		
	  		//looking for the price match 
	  		List<Bid> bidMatch =l.stream().filter(x -> txnPrice == x.getPrice()).collect(Collectors.toList());
	  		
	  		if(bidMatch != null && bidMatch.size()>=1) {
	  			b = (Bid)l.get(0);
	  			checkAndUpdateBidStock( b , txnQty);
	  		}else {
	  			// Price did not Match, looking for the next best price in order book
	  			
	  			for(int i=0 ; i < l.size();i++) {
	  				b = (Bid)l.get(i);
	  				if(txnPrice > b.getPrice()) {
	  					checkAndUpdateBidStock( b , txnQty);
	  				}
	  			}
	  		}
	  	  }
		
	}
	
	
	public void checkAndUpdateBidStock(Bid b , int txnQty) {
		//Best price match hence adjusting the Order quantity based on the incoming requirement
  		synchronized(this){
	  		if( b.getQuantity() >= txnQty ) {
	  			b.setQuantity(b.getQuantity() - txnQty);
	  		// Updating the Order book with latest stock entry 
		  		saveOrUpdateBid(b);
	  		}else {
	  			logger.info("Insufficient stock to fullfill your request");
	  			//TODO : set this message into a response object
	  			ordResp.setRespStatus(OrderConstant.FAIL);
	  			ordResp.setMsgTxt("No sufficient stock available to fullfill your request");
	  		}
  		}
	}
	
	
	//getting all BID Orders in descending Order   
	public List<Bid> getAllBidOrders()   
	{  
	
		if(logger.isDebugEnabled())
			logger.info("In getAllBidOrders");
		
			List<Bid> bids = new ArrayList<Bid>();  
			bidRepo.findAll().forEach(rec -> bids.add(rec));  
			
			List<Bid> sortedList = bids.stream()
	  		        .sorted(Comparator.comparingDouble(Bid :: getPrice).reversed())
	  		        .collect(Collectors.toList());
		return sortedList;  
	}  
  
	public Bid getOrderById(int id)   
	{  
		 if(logger.isDebugEnabled())
			logger.info("In getOrderById");
			return bidRepo.findById(id).get();  
	}  
	
	public void saveOrUpdateBid(Bid bid)   
	{  
		 if(logger.isDebugEnabled())
			 logger.info("In saveOrUpdateBid");
		
		 	 bidRepo.save(bid);  
	} 	
	
	//deleting a specific Order {id}
	public void delete(int id) {
		 if(logger.isDebugEnabled())
			logger.info("In delete");
			bidRepo.deleteById(id);
	}
	
	
}
