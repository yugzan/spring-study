package org.iii.influx.test.task;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.iii.influx.config.InfluxDBTemplate;
import org.iii.influx.rest.model.JpyResource;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yongzan
 * @date 2016/11/14 
 * {@link http://tw.rter.info/json.php?t=currency&q=check&iso=JPY}
 */
@Component
public class JpyGetTask {

    @Autowired
    private InfluxDBTemplate dbTemplate;

    @Autowired
    private ObjectMapper mapper;
    
    @Scheduled(cron = "0 0/1 * * * ?")
    public void parser() throws IOException {
        String url = "http://tw.rter.info/json.php?t=currency&q=check&iso=JPY";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        Response resp = client.newCall(request).execute();
        String response = resp.body().string();
        
        JpyResource json = mapper.readValue(response, JpyResource.class);
        
        List<Point> preProcessPoints = json.getData().stream()
        .filter(predicate -> predicate.size()>=5 )
        .map(mapper -> mapper.stream().collect(Collectors.toList()) )
        .collect(Collectors.toList())
        .stream()
        .map(lists-> newPoint(lists))        
        .collect(Collectors.toList());
        
        dbTemplate.write(preProcessPoints);
    }
    private final String regex = "[-+\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？A-z0-9\\s]";
    
    private Point newPoint(List<String> list) {
        return Point.measurement("exchange_rate").time(OffsetDateTime.now().toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
                .tag("BankTag",   list.get(0).replaceAll(regex, "") )
                .addField("BankRaw", list.get(0))
                .addField("BankName",  list.get(0).replaceAll(regex, "") )
                .addField("BuyIn", list.get(1))
                .addField("BuyOut", list.get(2) )
                .addField("LastTime", list.get(3) )
                .addField("other", list.get(4) )
                .build();
    }
}
