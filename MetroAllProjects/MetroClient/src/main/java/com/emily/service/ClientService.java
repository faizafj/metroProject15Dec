package com.emily.service;

import java.util.Collection;

//import java.util.List;

import com.emily.entity.Bill;
import com.emily.entity.Customer;
import com.emily.entity.Station;
import com.emily.entity.StationList;
//import com.emily.entity.Station;


public interface ClientService {
	
	Customer loginCheck(int id);
	Customer addNewCustomer(Customer customer);
	Collection<Station> getAllStations();
	double calculatePrice(int startingPoint, int finishingPoint,double price);
	Bill tapOut(int stationId, int customerId,double price);
	Customer setNewStationId(int customerId, int stationId);
	Customer deductCustomerBalance(int customerId, double amount);
	Station getStation(int stationId);
	
}
