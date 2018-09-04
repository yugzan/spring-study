package org.yugzan.mongo.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * @author yongzan
 * @date 2018/09/03
 */
public class StringEnumConverters {
  @WritingConverter
  public static class EnumToStringConverter implements Converter<Enum<?>, Object> {
    @Override
    public Object convert(Enum<?> source) {
      if (source instanceof StringEnumConvertable) {
        return ((StringEnumConvertable) (source)).value();
      } else {
        return source.name();
      }
    }
  }
}
