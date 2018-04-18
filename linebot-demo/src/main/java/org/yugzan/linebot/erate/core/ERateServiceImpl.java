package org.yugzan.linebot.erate.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yugzan.linebot.influx.InfluxDBTemplate;
import org.yugzan.linebot.influx.model.BankPoint;
import org.yugzan.linebot.influx.model.BankResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yongzan
 * @date 2018/04/02
 */
@Service
public class ERateServiceImpl implements ERateService{
	private static final Logger logger = LoggerFactory.getLogger(ERateServiceImpl.class);
	
	@Autowired(required = false)
	private InfluxDBTemplate dbTemplate;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public BankResource getRealtimeValue(String orderISO) throws Exception {
		BankResource resource = parseResponse(orderISO);
		if(resource.getData().isEmpty()) {
			 throw new  IOException("no data response.");
		}
		resource.setIso(orderISO);
		return resource;
	}

	@Override
	public void getRealtimeValue(String orderISO, Consumer<BankResource> sucess, Consumer<Throwable> error){
		try {
			BankResource resource = parseResponse(orderISO);
			resource.setIso(orderISO);
			if(resource.getData().isEmpty()) {
				error.accept(new  IOException("no data response."));
			}else {
				sucess.accept( resource );
			}
		} catch (IOException e) {
			error.accept(e);
		}
	}
	@Override
	public String getLastValue(String orderISO) throws Exception {
		Objects.requireNonNull(dbTemplate, "DB is not setting.");
		List<BankPoint> results = dbTemplate.queryTo( String.format( SQL_BY_LASTONE, "bank_point", "10m",orderISO) , BankPoint.class);
		logger.info("{} : {}",  String.format( SQL_BY_LASTONE, "bank_point", "10m",orderISO),results.size());
		return results.stream()
				.filter(p->p.getBuyIn()!=null)
				.map( point->{
//			logger.error("{} {}", point.getBankName(), point.getBuyOut());
			return point.getBankName() + point.getBuyOut();
		})
		.collect(Collectors.joining("\n"));
//		return null;
	}
	@Override
	public String toCommonString(BankResource resource) {
		return resource.getData().stream().map(list -> {
			Iterator<String> iterator = list.iterator();
			return String.format("%s: 買入:%s 賣出:%s", filterBankName(iterator.next()), iterator.next(), iterator.next());
		}).collect(Collectors.joining("\n"));
	}
	
	private BankResource parseResponse(String iso) throws IOException {
		String url = "http://tw.rter.info/json.php?t=currency&q=check&iso=";
		OkHttpClient client = new OkHttpClient();
		Response resp = client.newCall(new Request.Builder().url(url + iso).build()).execute();
		String response = resp.body().string();
		BankResource json = mapper.readValue(response, BankResource.class);
		return json;
	}

	private static String filterBankName(String rawBankName) {
		boolean filterFlag = rawBankName.matches(REGEX_SWIFT);
		return (filterFlag) ? rawBankName : rawBankName.replaceAll(REGEX, "");
	}

	@Override
	public List<BankPoint> query(String queryString) throws Exception {
		try {
			return dbTemplate.queryTo(queryString, BankPoint.class);
		}catch(Exception e) {
			throw e;
		}
	}


}
