package org.yugzan.mongo.convert;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @author yongzan
 * @date 2018/05/14
 */
public class DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {
	@Override
	public OffsetDateTime convert(Date source) {
		return source == null ? null : OffsetDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
	}
}
