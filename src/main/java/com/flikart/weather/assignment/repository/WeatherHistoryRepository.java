package com.flikart.weather.assignment.repository;

import com.flikart.weather.assignment.entity.WeatherDataMongoHistory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherHistoryRepository extends MongoRepository<WeatherDataMongoHistory, ObjectId> {
}
