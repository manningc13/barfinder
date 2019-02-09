package com.barfinder.barfinder;

import com.barfinder.barfinder.fetch.MapLocationFetcher;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class BarFinderApplication {

//	private static final Logger logger = LoggerFactory.getLogger(BarFinderApplication.class);

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BarFinderApplication.class, args);
		MapLocationFetcher mapLocationFetcher = applicationContext.getBean(MapLocationFetcher.class);
		System.out.println("Current Lat, Long for Boston, MA: " + Arrays.toString(mapLocationFetcher.getLongAndLat("Boston, MA")));
	}

}

