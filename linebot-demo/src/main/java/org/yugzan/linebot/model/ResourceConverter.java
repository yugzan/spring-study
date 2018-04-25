package org.yugzan.linebot.model;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.influxdb.dto.Point;
import org.yugzan.linebot.ObjectMapperHolder;
import org.yugzan.linebot.erate.core.ERateService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yongzan
 * @date 2016/11/10
 */
public class ResourceConverter {

	private static ObjectMapper objectMapper = ObjectMapperHolder.INSTANCE.get();

	public static Point convert(final PointResource resource) {
		return Point.measurement(resource.getMeasurement()).tag(resource.getTags())
				.time(OffsetDateTime.now().toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
				.fields(resource.getFields()).build();
	}

	// public static List<Point> convert(final UserResource resource) {
	// return resource.getData().stream()
	// .map(data->newBankPoint(new ArrayList<String>(data) , resource.getIso()))
	// .collect(Collectors.toList());
	// }
	/**
	 * Convert UserResource to UserPoint
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */

	public static UserResource convert(final UserPoint point)
			throws JsonParseException, JsonMappingException, IOException {
		UserResource resource = new UserResource();
		resource.setBankName(point.getBankName());
		resource.setLineId(point.getLineId());
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		HashMap<String, String> o = objectMapper.readValue(point.getThreshold(), typeRef);
		resource.setThreshold(o);
		return resource;
	}

	public static Point convert(final UserResource resource) throws JsonParseException, JsonMappingException, IOException {
		return Point.measurement("user_point").tag("bankName", resource.getBankName())
				.time(OffsetDateTime.now().toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
				.addField("lineId", resource.getLineId())
				.addField("threshold", objectMapper.writeValueAsString(resource.getThreshold())).build();
	}

	/**
	 * Convert BankResource to BankPoint
	 */
	public static List<Point> convert(final BankResource resource) {
		return resource.getData().stream().map(data -> newBankPoint(new ArrayList<String>(data), resource.getIso()))
				.collect(Collectors.toList());
	}

	private static Point newBankPoint(List<String> list, String iso) {
		String bankInfo = list.get(0);
		boolean filterFlag = bankInfo.matches(ERateService.REGEX_SWIFT);
		String tag = (filterFlag) ? bankInfo : bankInfo.replaceAll(ERateService.REGEX, "");
		return Point.measurement("bank_point").tag("bankTag", tag).tag("iso", iso)
				.time(OffsetDateTime.now().toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
				.addField("bankInfo", bankInfo).addField("bankName", tag).addField("buyIn", list.get(1))
				.addField("buyOut", list.get(2)).addField("lastTime", list.get(3)).addField("other", list.get(4))
				.build();
	}
}
