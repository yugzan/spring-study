package org.iii.influx.rest.model;

import java.util.concurrent.TimeUnit;
import org.influxdb.dto.Point;

/**
 * @author yongzan
 * @date 2016/11/10
 */
public class ResourceConverter {

    public static Point convert(final PointResource resource) {
        return 
                Point.measurement(resource.getMeasurement()).tag(resource.getTags())
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .fields(resource.getFields()).build();
    }

}
