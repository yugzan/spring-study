package org.yugzan.linebot.influx.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.influxdb.dto.Point;
import org.yugzan.linebot.erate.core.ERateService;

/**
 * @author yongzan
 * @date 2016/11/10
 */
public class ResourceConverter {

    public static Point convert(final PointResource resource) {
        return 
                Point.measurement(resource.getMeasurement()).tag(resource.getTags())
                        .time(OffsetDateTime.now().toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
                        .fields(resource.getFields()).build();
    }
    
    public static List<Point> convert(final BankResource resource) {
    	return resource.getData().stream()
    	.map(data->newPoint(new ArrayList<String>(data) , resource.getIso()))
    	.collect(Collectors.toList());
    }
    
    private static Point newPoint(List<String> list, String iso) {
    	String bankInfo = list.get(0);
        boolean filterFlag = bankInfo.matches(ERateService.REGEX_SWIFT);
        String tag = (filterFlag) ? bankInfo : bankInfo.replaceAll(ERateService.REGEX, "");
        return Point.measurement("bank_point")
                .tag("bankTag",  tag  )
                .tag("iso",   iso  )
        		.time(OffsetDateTime.now().toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
                .addField("bankInfo", bankInfo)
                .addField("bankName",  tag )
                .addField("buyIn", list.get(1))
                .addField("buyOut", list.get(2) )
                .addField("lastTime", list.get(3) )
                .addField("other", list.get(4) )
                .build();
    }
}
