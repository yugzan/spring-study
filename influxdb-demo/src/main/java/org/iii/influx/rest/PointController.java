package org.iii.influx.rest;

import org.iii.influx.config.InfluxDBTemplate;
import org.iii.influx.rest.model.PointResource;
import org.iii.influx.rest.model.ResourceConverter;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yongzan
 * @date 2016/11/8
 */

@RestController
public class PointController {

    private static final Logger logger = LoggerFactory.getLogger(PointController.class);

    @Autowired
    private InfluxDBTemplate dbTemplate;

    @RequestMapping(method = RequestMethod.POST, value = "/point")
    public ResponseEntity<PointResource> insert(@RequestBody PointResource resource) {
        dbTemplate.write(ResourceConverter.convert(resource));
        return new ResponseEntity<PointResource>(resource, new HttpHeaders(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/point/{sqlString}")
    public QueryResult insert(@PathVariable String sqlString) {
        logger.info("SQL :{}", sqlString);
        Query query = new Query(sqlString, dbTemplate.getProperties().getDatabase()); // "SELECT idle FROM cpu"
        return dbTemplate.query(query);
    }

}
