package com.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.order.model.Ask;

@Repository
public interface AskRepository extends CrudRepository<Ask, Integer>{

}