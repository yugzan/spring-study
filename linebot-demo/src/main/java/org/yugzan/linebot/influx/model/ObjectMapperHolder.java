package org.yugzan.linebot.influx.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author yongzan
 * @date 2018/04/17 
 */
public enum ObjectMapperHolder {
	  INSTANCE;

	  private final ObjectMapper mapper;

	  private ObjectMapperHolder() {
	    this.mapper = create();
	  }

	  public ObjectMapper get() {
	    return this.mapper;
	  }

	  private static ObjectMapper create() {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
	                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
	                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
	                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
	                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
	                .findAndRegisterModules();
	    return mapper;
	  }
}
