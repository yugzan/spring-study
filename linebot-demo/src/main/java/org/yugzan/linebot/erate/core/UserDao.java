package org.yugzan.linebot.erate.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.influxdb.DefaultInfluxDBTemplate;
import org.springframework.stereotype.Repository;
import org.yugzan.linebot.model.ResourceConverter;
import org.yugzan.linebot.model.UserPoint;
import org.yugzan.linebot.model.UserResource;

/**
 * @author yongzan
 * @date 2018/04/18 
 */
@Repository
@ConditionalOnExpression("${spring.influxdb.enable}")
public class UserDao {
	
	@Autowired
	private DefaultInfluxDBTemplate dbTemplate;
	
	public final static String SQL_LISTUSER=
			"SELECT * FROM \"%s\" WHERE \"lineId\"='%s' ORDER BY time DESC LIMIT 1";
	
	public final static String SQL_GETUSER_BY_ID=
			"SELECT * FROM \"%s\" WHERE \"lineId\"='%s' ORDER BY time DESC LIMIT 1";
	
	public void create(UserResource userResource) throws Exception{
		dbTemplate.write( ResourceConverter.convert(userResource));
	}
	
	public void update(String lineId, UserResource newer) throws Exception {
		UserResource replace = new UserResource();
		Optional<UserResource> old = read(lineId);
		if(old.isPresent()) {
			// set Id
			replace.setLineId(old.get().getLineId() );
			// replace bank name
			replace.setBankName( (newer.getBankName() != null)? newer.getBankName():old.get().getBankName() );
			// replace threshold
			HashMap<String, String> threshold = new HashMap<>();
			if(old.get().getThreshold() != null) {
				threshold.putAll( old.get().getThreshold());
			}
			if(newer.getThreshold() != null) {
				threshold.putAll(newer.getThreshold());	
			}
			replace.setThreshold(threshold );
			dbTemplate.write( ResourceConverter.convert(replace));
		}
	}
	
	public Optional<UserResource> read(String lineId){
		try {
			System.out.println( String.format( SQL_GETUSER_BY_ID, "user_point", lineId) );
			List<UserPoint> points = dbTemplate.queryAs( String.format( SQL_GETUSER_BY_ID, "user_point", lineId) , UserPoint.class);
			return (points.isEmpty())?
					Optional.empty():
					Optional.of(ResourceConverter.convert(points.get(0)));
		} catch (IOException| org.influxdb.InfluxDBException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
