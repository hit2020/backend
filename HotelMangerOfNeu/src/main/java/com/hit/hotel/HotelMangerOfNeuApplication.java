package com.hit.hotel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages={"com.hit.hotel.room.mapper"})
public class HotelMangerOfNeuApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelMangerOfNeuApplication.class, args);
	}

}