package org.yugzan.mongo.convert;

import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @author yongzan
 * @date 2018/05/14 
 */
public class OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {

    @Override
    public Date convert(OffsetDateTime source) {
        return source == null ? null : Date.from(source.toInstant());
    }

}
