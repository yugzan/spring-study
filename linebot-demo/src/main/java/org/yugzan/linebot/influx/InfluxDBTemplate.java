package org.yugzan.linebot.influx;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yongzan
 * @date 2016/11/9
 * @Ref https://github.com/miwurster/spring-data-influxdb
 */
public class InfluxDBTemplate implements InfluxDBOperations<Point>{
    private static final Logger logger = LoggerFactory.getLogger(InfluxDBTemplate.class);
    
    private InfluxDB influxDB;
    private final InfluxDBProperties properties;

    public InfluxDBTemplate(InfluxDB influxDB, InfluxDBProperties properties) {
        super();
        this.influxDB = influxDB;
        this.properties = properties;
        final String dbName = properties.getDatabase();
        createDatabase(dbName);
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }

    public InfluxDBProperties getProperties() {
        return properties;
    }

    @Override
    public void createDatabase(String dbName) {
        influxDB.createDatabase(dbName);
    }

    @Override
    public void write(final Point point) {
        final String dbName = properties.getDatabase();
        final String policy = properties.getRetentionPolicy();
        final BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy(policy) //"autogen"
                .consistency(ConsistencyLevel.ALL)
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }

    @Override
    public void write(final List<Point> points) {
        logger.debug("Point size:{}", points.size());
        final String dbName = properties.getDatabase();
        final String policy = properties.getRetentionPolicy();
        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy(policy) //"autogen"
                .consistency(ConsistencyLevel.ALL)
                .build();
        for (Point point : points) {
            batchPoints.point(point);
        }
        influxDB.write(batchPoints);
    }

    @Override
    public void dropDatabase() {
        final String dbName = properties.getDatabase();
        influxDB.deleteDatabase(dbName);
    }

    @Override
    public QueryResult query(Query query) {
        return influxDB.query(query);
    }

    @Override
    public QueryResult query(Query query, TimeUnit timeUnit) {
        return influxDB.query(query, timeUnit);
    }

    @Override
    public QueryResult query(String queryString) {
         final String dbName = properties.getDatabase();
        Query query = new Query(queryString , dbName);
        return influxDB.query(query);
    }

	@Override
	public <S> List<S> queryTo(String queryString, Class<S> clazz) {
		Query q = new Query(queryString, properties.getDatabase());
		QueryResult result= influxDB.query(q);
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<S> pointList = resultMapper.toPOJO(result, clazz);
		return pointList;
	}

	@Override
	public <S> void queryTo(String queryString, Class<S> clazz, Consumer<List<S>> onSuccess, Consumer<Throwable> onFailure) {
		try{
			onSuccess.accept( queryTo(queryString, clazz));
		}catch(Exception e){
			onFailure.accept(e);
		}
	}

    
    




}
