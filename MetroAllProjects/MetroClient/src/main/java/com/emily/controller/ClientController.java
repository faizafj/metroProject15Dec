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

    // User inputs ID to login
    @RequestMapping("/")
    public ModelAndView getUserIdPage() {
        return new ModelAndView("loginPage");
    }

    // Customer Account page
    @RequestMapping("/viewBalance")
    public ModelAndView accountController(@RequestParam("customerId") int id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        Customer customer = service.loginCheck(id);

        if (customer != null) {
            session.setAttribute("customer", customer);
            session.setAttribute("customerName", customer.getCustomerFirstName());
            modelAndView.setViewName("viewBalance");
            modelAndView.addObject("stationObj",new Station());
        } else {
            modelAndView.addObject("message", "No account found with that Id, Please try again");
            modelAndView.setViewName("loginPage");
        }

        Collection<Station> stationList = service.getAllStations(); //List of all stations
        modelAndView.addObject("StationList", stationList); //adds the station object to website.
        return modelAndView; //returns everything
    }

    @ModelAttribute("stations") //Creates a DOM object called stations.
    public Collection<Station> getStation() {
        Collection<Station> stationList = service.getAllStations(); //all stations
        return stationList; //returns the station list
    }



    // Create a new Customer
    @RequestMapping("/addNewCustomerPage")
    public ModelAndView addPageController() {

        return new ModelAndView("registerPage", "customer", new Customer());
    }

//
//    @RequestMapping("/addNewCustomer")
//    public ModelAndView addNewCustomerController(@ModelAttribute("customer") Customer newCustomer, @RequestParam("customerDateOfBirth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        ModelAndView modelAndView = new ModelAndView();
//        newCustomer.setCustomerDateOfBirth(date);
//        String message;
//
//        if (service.addNewCustomer(newCustomer) != null) {
//            message = "New Account Created";
//            modelAndView.setViewName("loginPage");
//        } else {
//            message = "Unfortunately a new account was not created";
//            modelAndView.setViewName("registerPage");
//        }
//
//        modelAndView.addObject("message", message);
//
//        return modelAndView;
//
//    }

    @RequestMapping("/addNewCustomer")
    public ModelAndView addNewCustomerController(@ModelAttribute("customer") Customer newCustomer, @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        newCustomer.setCustomerDateOfBirth(date);
        Customer newRegisteredCustomer = service.addNewCustomer(newCustomer);

        String message;

        if (newRegisteredCustomer != null) {
            message = "New Account Created";
            modelAndView.setViewName("loginPage");
            int registeredCustomerId = newRegisteredCustomer.getCustomerId();
            session.setAttribute("customerId", registeredCustomerId);
        } else {
            message = "You must be over 11 to register for a new account";
            modelAndView.setViewName("registerPage");
        }

        modelAndView.addObject("message", message);

        return modelAndView;

    }

}