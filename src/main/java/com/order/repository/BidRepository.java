package com.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.order.model.Bid;

@Repository
public interface BidRepository extends CrudRepository<Bid, Integer>{

}
