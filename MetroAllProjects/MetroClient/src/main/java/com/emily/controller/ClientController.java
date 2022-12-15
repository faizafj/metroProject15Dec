package com.emily.controller;

import java.time.LocalDate;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import com.emily.entity.Station;
import com.emily.entity.StationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.emily.entity.Customer;
import com.emily.service.ClientService;
@Controller
public class ClientController {
	@Autowired
	private ClientService service;


	//First page which is loaded
	// Ask user to inputs their ID to login or they can register a new account.
	@RequestMapping("/")
	public ModelAndView getUserIdPage() {
		return new ModelAndView("signInOrRegisterPage" ,"customer", new Customer());
	}

	@RequestMapping("/addNewCustomer")
	public ModelAndView addNewCustomerController(@ModelAttribute("customer") Customer newCustomer, @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();

		newCustomer.setCustomerDateOfBirth(date);
		Customer newRegisteredCustomer = service.addNewCustomer(newCustomer);


		String message;

		if (newRegisteredCustomer != null) {
			message = "New Account Created";
			modelAndView.setViewName("CustomerBalance");
			int registeredCustomerId = newRegisteredCustomer.getCustomerId();
			session.setAttribute("customerId", registeredCustomerId);
		} else {
			message = "Unfortunately a new account was not created";
			modelAndView.setViewName("registerPage");
		}

		modelAndView.addObject("message", message);

		return modelAndView;

	}

	//View customer details, including their balance and tap in / tap out functions
	@RequestMapping("/viewBalance")
	public ModelAndView accountController(@RequestParam("customerId") int id, HttpSession session) {
		session.getAttribute("registeredCustomerId");

		ModelAndView modelAndView = new ModelAndView();

		Customer customer = service.loginCheck(id);

		if (customer != null) { //If a customer exists
			session.setAttribute("customer", customer); //set customer details to session
			session.setAttribute("customerName", customer.getCustomerFirstName()); //customer name is used to displayed in HTML
			modelAndView.setViewName("viewBalance");
			modelAndView.addObject("stationObj",new Station()); //Used for station drop down list
		} else {
			modelAndView.addObject("message", "No account found with that Id, Please try again");
			modelAndView.setViewName("/signInOrRegisterPage"); //if user not logged in, redirected to login page
		}

		Collection<Station> stationList  = service.getAllStations(); //List of all stations
		modelAndView.addObject("StationList", stationList); //adds the station object to website.
		return modelAndView; //returns everything
	}

	@ModelAttribute("stations") //Creates a DOM object called stations.
	public Collection<Station> getStation (){
		Collection<Station> stationList  = service.getAllStations(); //all stations
		return stationList; //returns the station list
	}


//	// Create a new Customer
//	@RequestMapping("/addNewCustomerPage")
//	public ModelAndView addPageController() {
//
//		return new ModelAndView("InputNewCustomer", "customer", new Customer());
//	}


}
