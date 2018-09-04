package org.yugzan.mongo.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author yongzan
 * @date 2018/09/03
 */
@SuppressWarnings("rawtypes")
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
    Class<?> enumType = targetType;
    while (enumType != null && !enumType.isEnum()) {
      enumType = enumType.getSuperclass();
    }
    if (enumType == null) {
      throw new IllegalArgumentException(
          "The target type " + targetType.getName() + " does not refer to an enum");
    }
    return new StringToEnum(enumType);
  }

  @ReadingConverter
  public static class StringToEnum<T extends Enum> implements Converter<String, Enum> {
    private final Class<T> enumType;

    public StringToEnum(Class<T> enumType) {
      this.enumType = enumType;
    }
    @Override
    public Enum convert(String source) {
      for (T t : enumType.getEnumConstants()) {
        if (t instanceof StringEnumConvertable) {
          if (((StringEnumConvertable) t).value().equals(source)) {
            return t;
          }
        }
      }
      return null;
    }
  }
}
