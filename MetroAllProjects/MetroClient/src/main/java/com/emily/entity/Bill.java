package com.emily.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bill {

	
	private String customerFirstName;
	private String customerSurname;
	private String startingStation;
	private String endStation;
	private double totalPrice;
	private double newBalance;
}

