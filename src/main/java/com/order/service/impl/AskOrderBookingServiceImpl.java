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
import com.order.model.Ask;
import com.order.model.OrderResponse;
import com.order.model.Transaction;
import com.order.repository.AskRepository;
import com.order.service.AskOrderBookingService;

@Service
@ComponentScan({"com.*"})
public class AskOrderBookingServiceImpl implements AskOrderBookingService{
	

	@Autowired
	AskRepository askRepo; 
	
	
	@Autowired
	OrderResponse ordResp;

	Logger logger = LoggerFactory.getLogger(AskOrderBookingServiceImpl.class);
	
	
	
	public void execute(Transaction txn) {
		
		List<Ask> l = new ArrayList<Ask>();
		Ask askObj = new Ask();
		String opderType= txn.getOrderType();
		int txnQty = txn.getQuantity();
		
		l = getAllAskOrders();
		
		l.forEach(x ->logger.info("Reading to Ask  :: " + x.getPrice()));
		
		if(opderType != null && opderType.equals(OrderConstant.MARKET)) {
			askObj = l.get(0);
	  		logger.info("Reading lowest Ask ID : " + askObj.getId());
	  		logger.info("Reading lowest Ask Price : " + askObj.getPrice());
	  		logger.info("Reading lowest Ask Qty : " + askObj.getQuantity());
	  		
	  		checkAndUpdateAskStock( askObj ,  txnQty);
	  	  }else if(opderType != null && opderType.equalsIgnoreCase(OrderConstant.LIMIT)) {
	  		
	  		  // fetch the ASK price from request
	  		double txnPrice= txn.getPrice();
	  		
	  		//looking for the price match 
	  		List<Ask> askMatch = l.stream().filter(x -> txnPrice == x.getPrice()).collect(Collectors.toList());
	  		
	  		if(askMatch != null && askMatch.size()>=1) {
	  			askObj = (Ask)l.get(0);
	  			checkAndUpdateAskStock( askObj , txnQty);
	  		}else {
	  			// Price did not Match, looking for the next best price in order book
	  			
	  			for(int i=0 ; i < l.size();i++) {
	  				askObj = (Ask)l.get(i);
	  				if(askObj.getPrice() > txnPrice) {
	  					checkAndUpdateAskStock( askObj , txnQty);
	  				}
	  			}
	  		}
	  	  }
	}
	
	
	
	public void checkAndUpdateAskStock(Ask b , int txnQty) {
		//Best price match hence adjusting the Order quantity based on the incoming requirement
  		synchronized(this){
	  		if( b.getQuantity() >= txnQty ) {
	  			b.setQuantity(b.getQuantity() - txnQty);
	  		// Updating the Order book with latest stock entry 
		  		saveOrUpdateAsk(b);
	  		}else {
	  			logger.info("Insufficient stock to fullfill your request");
	  			ordResp.setRespStatus(OrderConstant.FAIL);
	  			ordResp.setMsgTxt("No sufficient stock available to fullfill your request");
	  		}
  		}
	}
	
	//getting all Orders records  
	public List<Ask> getAllAskOrders()   
	{  
	
		if(logger.isDebugEnabled())
			logger.info("In getAllAskOrders");
		
			List<Ask> asks = new ArrayList<Ask>();  
			askRepo.findAll().forEach(rec -> asks.add(rec)); 
			
			List<Ask> sortedList = asks.stream()
	  		        .sorted(Comparator.comparingDouble(Ask :: getPrice))
	  		        .collect(Collectors.toList());
		return sortedList;  
	}  
  
	public Ask getOrderById(int id)   
	{  
		 if(logger.isDebugEnabled())
			logger.info("In getOrderById");
			return askRepo.findById(id).get();  
	}  
	
	public void saveOrUpdateAsk(Ask ask)   
	{  
		 if(logger.isDebugEnabled())
			 logger.info("In saveOrUpdateAsk");
		 	 askRepo.save(ask);  
	} 	
	
	//deleting a specific Order {id}
	public void delete(int id) {
		 if(logger.isDebugEnabled())
			logger.info("In delete");
			askRepo.deleteById(id);
	}
}
