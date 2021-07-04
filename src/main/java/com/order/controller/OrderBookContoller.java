/**
 * 
 */
package com.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.order.model.Ask;
import com.order.model.Bid;
import com.order.service.AskOrderBookingService;
import com.order.service.BidOrderBookingService;

/**
 * @author Tej Prakash Singh
 *
 */
@RestController

public class OrderBookContoller {
	Logger logger = LoggerFactory.getLogger(OrderBookContoller.class);
	
	@Autowired
	BidOrderBookingService bidService;
	
	@Autowired
	AskOrderBookingService askService;
	
	// Swagger URL : http://localhost:8083/swagger-ui.html
	
	@RequestMapping(value="/v1/bid/allOrderBook" ,method=RequestMethod.GET)
	public @ResponseBody List<Bid> getAllOrderBook() {
		
		if(logger.isDebugEnabled())
			logger.info("Calling to getBidOrder");
		
		 return bidService.getAllBidOrders();
	}
	
	@RequestMapping(value="/v1/bid/populateBidEntity" ,method=RequestMethod.POST)
	public @ResponseBody int saveBidOrderBook(Bid bid) {
		
	        if(logger.isDebugEnabled())
	        	logger.info("Calling to saveBidOrder");
	        	bidService.saveOrUpdateBid(bid);
		 return bid.getId(); 
	}
	
	@RequestMapping(value="/v1/bid/order" ,method=RequestMethod.POST)
	public @ResponseBody Bid getOrderById(int bidId) {
		
	    Bid bid = new Bid();   
	        if(logger.isDebugEnabled())
	        	logger.info("Calling to getOrderById");
	        bid = bidService.getOrderById(bidId);
		 return bid; 
	}
	
	
	@RequestMapping(value="/v1/bid/delOrder" ,method=RequestMethod.POST)
	public @ResponseBody void deleteOrderById(int bidId) {
	        if(logger.isDebugEnabled())
	        	logger.info("Calling to deleteOrderById");
	        
	        bidService.delete(bidId);
	}
	
	/*-----------------------------------------------------------
	 * 
	 * 			Ask REST API
	 * 
	 * ----------------------------------------------------------
	 */
	
	
	
	@RequestMapping(value="/v1/ask/allOrderBook" ,method=RequestMethod.GET)
	public @ResponseBody List<Ask> getAllOrders() {
		
		if(logger.isDebugEnabled())
			logger.info("Calling to getAskOrder");
		
		 return askService.getAllAskOrders();
	}
	
	@RequestMapping(value="/v1/ask/populateOrderBookEntity" ,method=RequestMethod.POST)
	public @ResponseBody int saveOrderBook(Ask ask) {
		
	        if(logger.isDebugEnabled())
	        	logger.info("Calling to saveAskOrder");
	        askService.saveOrUpdateAsk(ask);
	        //Please ignore this error , this is due to eclips class path update issue 
		 return ask.getId(); 
	}
	
	
	@RequestMapping(value="/v1/ask/delAskOrder" ,method=RequestMethod.POST)
	public @ResponseBody void deleteAskOrderById(int bidId) {
	        if(logger.isDebugEnabled())
	        	logger.info("Calling to deleteOrderById");
	        
	        askService.delete(bidId);
	}

	
	
	
}
