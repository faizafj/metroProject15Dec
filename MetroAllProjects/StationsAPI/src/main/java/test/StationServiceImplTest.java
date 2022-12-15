package test;

import com.maria.entity.Station;
import com.maria.persistence.StationDao;
import com.maria.service.StationServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class StationServiceImplTest {
	
	@InjectMocks
	private StationServiceImpl service;
	@Mock
	private StationDao dao;
	private AutoCloseable autoCloseable;

	
	
	
	@BeforeEach
	void setUp() throws Exception {
		autoCloseable=MockitoAnnotations.openMocks(this);
		
		Station station1 =new Station(101, "Barking");
		Station station2= new Station(102,"King's Cross");
		List<Station> myList = new ArrayList<>();
		myList.add(station1);
		myList.add(station2);
		
	}
	
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	
	
	@Test
	void getAllRecords() {
		
		Station station1 =new Station(101, "Barking");
		Station station2= new Station(102,"King's Cross");
		dao.save(station1);
		dao.save(station2);
		assertTrue(service.getAllStations().size()>0);
	}
	
	
	
	
	

}
