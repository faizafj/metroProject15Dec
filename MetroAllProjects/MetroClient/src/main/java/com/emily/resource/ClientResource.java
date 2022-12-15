package com.emily.resource;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.emily.entity.Customer;
import com.emily.entity.Station;
import com.emily.service.ClientServiceImpl;



@RestController
public class ClientResource {
	
    @Autowired
	private ClientServiceImpl service;

    
    
    //get all stations
	@GetMapping(path= "/allStations", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Station> displayAllStationsResource() {
		
		return service.getAllStations();
	}
	
	//get Station by ID
	@GetMapping(path = "stations/{id}")
	public Station getStationNameResource(@PathVariable ("id") int stationId) {
		return service.getStation(stationId);
		
	}
	
  //update customer's starting point 
  	@PutMapping(path = "customers/resetStation/{cId}/{sId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer updateStationResource(@PathVariable ("cId") int customerId, @PathVariable ("sId") int stationId) {
  	return service.setNewStationId(customerId, stationId);
  	
  	}
  	//get customer by Id
  	@GetMapping(path = "customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomerByIdResource(@PathVariable("id") int id) {

		return service.loginCheck(id);
	}
  	
  //add new customer
  	@PostMapping(path = "customers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  	public Customer addNewCustomerResource(@RequestBody Customer customer) {
  		return service.addNewCustomer(customer);

  	}
  	@PutMapping(path = "customers/deduct/{cId}/{amount}")
  	public Customer deductCustomerBalance(@PathVariable ("cId")int customerId,@PathVariable ("amount") double amount) {
  		return service.deductCustomerBalance(customerId, amount);
  	}
  	
  	
  	
}

