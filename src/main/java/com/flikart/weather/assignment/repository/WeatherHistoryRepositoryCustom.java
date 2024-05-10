package com.flikart.weather.assignment.repository;

import com.flikart.weather.assignment.entity.WeatherDataMongoHistory;
import com.flikart.weather.assignment.entity.WeatherDataMongoHistory.Fields;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.flikart.weather.assignment.constants.Constant.VALID_SORT_FIELDS;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WeatherHistoryRepositoryCustom {
    private final MongoTemplate mongoTemplate;


    public List<WeatherDataMongoHistory> getWeatherByCityWithSorting(String city, String sortBy, String order, Integer pageNo, Integer pageSize) {
        Query query = getQuery(city)
                .with(Sort.by(DESC.name().equalsIgnoreCase(order)
                                ? DESC : ASC,
                        getSortValue(sortBy)))
                .skip((long) pageNo * pageSize).limit(pageSize);
        return mongoTemplate.find(query, WeatherDataMongoHistory.class);
    }

    private String getSortValue(String sortBy) {
        return VALID_SORT_FIELDS.contains(sortBy) ? sortBy : Fields.city;
    }

    private Query getQuery(String city) {
        return new Query(Criteria.where(Fields.city).is(city));
    }

    public List<WeatherDataMongoHistory> getHistoricalWeatherData(String city, LocalDate startDate, LocalDate endDate, Integer pageNo, Integer pageSize) {
        Query query = getQuery(city, startDate, endDate)
                .skip((long) pageNo * pageSize).limit(pageSize);
        return mongoTemplate.find(query, WeatherDataMongoHistory.class);
    }

    private Query getQuery(String city, LocalDate startDate, LocalDate endDate) {
        return new Query(Criteria.where(Fields.city).is(city)
                .and(Fields.createdAt).gte(startDate).lte(endDate));

    }
}
