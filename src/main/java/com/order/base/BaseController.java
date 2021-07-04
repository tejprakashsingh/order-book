/**
 * 
 */
package com.order.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tej Prakash Singh
 *
 */
@RestController

public class BaseController {
	Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/*
	 * To authorize and sanatize each request
	 * 
	 */
	
	public @ResponseBody boolean isAuthorize() {
		
		if(logger.isDebugEnabled())
			logger.info("Calling to isAuthorize");
		// TODO : 
		 return true;
	}
	
	
	
}
