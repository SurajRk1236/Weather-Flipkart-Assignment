package com.flikart.weather.assignment.repository;

import com.flikart.weather.assignment.entity.WeatherDataMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDbRepository extends MongoRepository<WeatherDataMongo, String> {

    @Query(value = "{}", fields = "{_id : 1}")
    List<String> findAllIds();

}
