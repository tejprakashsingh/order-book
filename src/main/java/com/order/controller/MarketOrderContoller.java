/**
 * 
 */
package com.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.base.BaseController;
import com.order.constant.OrderConstant;
import com.order.model.OrderResponse;
import com.order.model.Transaction;
import com.order.service.AskOrderBookingService;
import com.order.service.BidOrderBookingService;

/**
 * @author Tej Prakash Singh
 *
 */
@RestController

public class MarketOrderContoller extends BaseController {
	Logger logger = LoggerFactory.getLogger(MarketOrderContoller.class);
	
	@Autowired
	BidOrderBookingService bidService;
	
	@Autowired
	AskOrderBookingService askService;
	
	@Autowired
	OrderResponse ordResp;
	
	// Swagger URL : http://localhost:8083/swagger-ui.html
	
	@RequestMapping(value="/v1/bid/placeOrder" ,method=RequestMethod.POST)
	public ResponseEntity<String> placeOrder(Transaction txn) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		HttpStatus httpStat;
		String msgtxt ;
		try {
		if(isAuthorize()) {
			
		        if(logger.isDebugEnabled())
		        	logger.info("Calling to placeOrder");
		      
		       String txnType= txn.getTxnType();
		       
		       if(txnType !=null && txnType.equalsIgnoreCase(OrderConstant.BUY))
		       {
		    	   bidService.execute(txn);
		       }else  if(txnType !=null && txnType.equalsIgnoreCase(OrderConstant.SELL)) {
		    	   
		    	   askService.execute(txn);
		       }
		        	
			}    
		}catch(Exception ex) {
			ordResp.setRespStatus(OrderConstant.FAIL);
  			ordResp.setMsgTxt("Error in executing the Order match");
		}
			
		if(ordResp != null && ordResp.getRespStatus() != null && OrderConstant.FAIL.equalsIgnoreCase(ordResp.getRespStatus())) {
			 httpStat = HttpStatus.PRECONDITION_FAILED ;
			 msgtxt = ordResp.getMsgTxt();
		}else {
			 httpStat =  HttpStatus.OK ;
			 msgtxt = "Order executed Successfully !!";
		}
		return new ResponseEntity<>(msgtxt,httpStat);
	}
	
	
}
