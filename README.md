# order-book
@ auther : Tej Prakash Singh 
Date : 4th Jul 2021
 
Swagger URL : http://localhost:8083/swagger-ui.html

Designed and implemented a price-time priority order matching engine in Java.

Central part of a matching engine is an order book that keeps track of all bid (buy) and ask (sell) orders. There is exactly one order book per security (stock).

Each order is characterized by transaction type (Buy or Sell), order type (Market, Limit), quantity, price (for Limit orders) and entry timestamp. Market orders are instructions to buy / sell at best possible price. Limit orders are instructions to buy at a predefined price x or lower, or to sell at a predefined price x or higher.

The bids are sorted in descending order and asks are sorted in ascending order in terms of price. Hence, the top row of an order book always shows orders with the best price: the highest bid and the lowest ask.

In every matching cycle orders in an order book get ‘matched’ / executed. First matching/execution priority is price, i.e. orders with the best price get matched first. If two orders have the same price, execution priority is given to the first arrived order.

Order book will support the following operations: 
Fetch Order :../v1/ask/allAskOrder

NEW ORDER , AMEND ORDER : ../v1/ask/saveAskOrder?id=6&price=54.0&quantity=11

Market Order :

To place an BID Order : ...../v1/bid/placeOrder?orderType=MARKET&price=&quantity=500&txnType=buy

To place an ASK Order : ...../v1/bid/placeOrder?orderType=MARKET&price=&quantity=500&txnType=sell

Limit Order :

To place an BID Order : ...../v1/bid/placeOrder?orderType=Limit&price=3&quantity=500&txnType=buy

To place an ASK Order : ...../v1/bid/placeOrder?orderType=Limit&price=3&quantity=500&txnType=sell


Additionally, the system will support querying the current state of an order book at any given time

Note : Order Book Controller is used for populating the H2 database before Market Order Controller gets triggered.

---------------------------------------------INPUT JSON --------------------------------------------

Input Json for placeOrder API:

1] MARKET/buy :
 {
    "inTime": null,
    "price": null,
    "quantity": 10,
	   "orderType" :MARKET,
	   "txnType":"buy"
  }
  
  2] MARKET/sell :
{
    "inTime": null,
    "price": null,
    "quantity": 10,
	    "orderType" :MARKET,
	    "txnType":"sell"
  }
      
  3] LIMIT/sell :
{
    "inTime": null,
    "price": 22.50,
    "quantity": 10,
	   "orderType" :LIMIT,
	   "txnType":"sell"
  }
  
  4] LIMIT/buy :
{
    "inTime": null,
    "price": 22.50,
    "quantity": 10,
	   "orderType" :LIMIT,
	   "txnType":"buy"
  }
