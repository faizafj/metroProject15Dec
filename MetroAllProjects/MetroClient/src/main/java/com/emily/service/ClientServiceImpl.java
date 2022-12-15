package com.emily.service;



import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.emily.entity.Bill;
import com.emily.entity.Customer;
import com.emily.entity.Station;
import com.emily.entity.StationList;


@Service
public class ClientServiceImpl implements ClientService {

	// Import RestTemplate to call Rest API - to connect to customer database
		@Autowired
		private RestTemplate restTemplate;

		@Override
		public Customer loginCheck(int id) {	
			try {
				// Call API to search for customer with inputed Id
				Customer customer = restTemplate.getForObject("http://localhost:8089/customers/"+id, Customer.class);
				return customer;
			} catch(Exception exception) {
				return null;
			}	
		}

		@Override
		public Customer addNewCustomer(Customer customer) {
			try {
				return restTemplate.postForObject("http://localhost:8089/customers", customer, Customer.class);
			} catch(Exception exception) {
				return null;
			}
		}
		
      // Get all Stations
		
		
		//method to change stationId in customer
		public Customer setNewStationId(int customerId, int stationId) {
			
			restTemplate.put("http://localhost:8089/customers/resetStation/"+customerId+ "/" + stationId, Customer.class);
			Customer customer = loginCheck(customerId);
			if(customer.getStationId()==stationId) {
			return customer;
			}
			return null;
		}
		
		
		
		@Override
		public Collection<Station> getAllStations() {
			try {
				StationList allStations = restTemplate.getForObject("http://localhost:8082/allStations", StationList.class);
				Collection<Station> listStations = allStations.getStationList();
				return listStations;
			} catch(Exception exception) {
				return null;
			}
		
		}
		@Override
		public Customer deductCustomerBalance(int customerId, double amount) {
			try {
				Customer customer = loginCheck(customerId);
				restTemplate.put("http://localhost:8089/customers/deduct/"+customerId+ "/" + amount, Customer.class);
				Customer newCustomer = loginCheck(customerId);
				
				if(customer.getCustomerBalance()<newCustomer.getCustomerBalance()) {
					return newCustomer;
				}
			}catch(Exception e) {
			
			}
			return null;
		}
		
		public Station getStation(int stationId) {
			
			
         Collection<Station> allStations = getAllStations();
			
			for(Station s: allStations) {
				if(s.getStationId()==stationId) {
				 return s;
				}
	
			}
			return null;
		}

		//calculate total travel price
		@Override
		public double calculatePrice(int startingPoint, int finishingPoint, double price) {
			return ((finishingPoint - startingPoint)*price);
		}

		@Override
		public Bill tapOut(int stationId, int customerId, double price) {
			
			Customer customer = loginCheck(customerId);
			
			String startingPoint=getStation(customer.getStationId()).getStationName();
			String endPoint=getStation(stationId).getStationName();
			
			double amountToPay = calculatePrice(stationId, customer.getStationId(),price);
			Customer newCustomer = deductCustomerBalance(customerId, amountToPay);
			if(newCustomer !=null) {
			double newBalance = newCustomer.getCustomerBalance();
			Bill bill = new Bill(customer.getCustomerFirstName(), customer.getCustomerSurname(),startingPoint,endPoint , amountToPay, newBalance);
			
			
			return bill;	
			}
			return null;
			}

		
		

	
}
