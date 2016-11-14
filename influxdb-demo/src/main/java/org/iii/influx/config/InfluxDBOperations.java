package org.iii.influx.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

/**
 * @author yongzan
 * @date 2016/11/9
 * @Ref https://github.com/miwurster/spring-data-influxdb
 */
public interface InfluxDBOperations<T> {
    /**
     * Ensures that the configured database exists.
     */
    void createDatabase(String dbName);

    /**
     * Write a single measurement to the database.
     * @param <T>
     *
     * @param payload the measurement to write to
     */
    void write(T payload);

    /**
     * Write a set of measurements to the database.
     * @param <T>
     *
     * @param payload the values to write to
     */
    void write(List<T> payload);
    
    QueryResult query(final Query query);
    
    QueryResult query(final String queryString);
    
    QueryResult query(final Query query, final TimeUnit timeUnit);
    /**
     * Drop the database.
     */
    void dropDatabase();
}
