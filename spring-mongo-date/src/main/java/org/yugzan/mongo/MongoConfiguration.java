package org.yugzan.mongo;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.yugzan.mongo.convert.DateToOffsetDateTimeConverter;
import org.yugzan.mongo.convert.OffsetDateTimeToDateConverter;
import org.yugzan.mongo.convert.StringEnumConverters;
import org.yugzan.mongo.convert.StringToEnumConverterFactory;
import org.yugzan.mongo.repo.UserRepository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.mongodb.MongoClientOptions.Builder;

/**
 * @author yongzan
 * @date 2018/05/11
 * 
 *       By default, @EnableMongoRepositories will scan the current package for
 *       any interfaces that extend one of Spring Data’s repository interfaces.
 * 
 *       Use it’s basePackageClasses=MyRepository.class to safely tell Spring
 *       Data MongoDB to scan a different root package by type
 * 
 *       if your project layout has multiple projects and its not finding your
 *       repositories.
 */
@Configuration
@EnableMongoRepositories(mongoTemplateRef = "mongoDateTemplate", basePackageClasses= UserRepository.class) 
public class MongoConfiguration {

	private final static Logger logger = (Logger) LoggerFactory.getLogger(MongoConfiguration.class);

	public static String sKEY = "1234567890abcdef";

	@Value("${mongo.uri:''}")
	private String uri;

	@Value("${mongo.host:127.0.0.1}")
	private String host;

	@Value("${mongo.port:27017}")
	private int port;

	@Value("${mongo.db.name:mongoDate}")
	private String dbname;

	@Value("${mongo.db.auth:false}")
	private boolean enabledAuth;

	@Autowired
	private ApplicationContext context;

	@Bean
	public MongoDbFactory mongoFactory() throws Exception {
		String mongoUri = (uri.startsWith("mongodb://")) ? uri : String.format("mongodb://%s:%s", host, port);
		logger.info("mongo uri:{}", mongoUri);
	    Builder builder = MongoClientOptions.builder().writeConcern(WriteConcern.ACKNOWLEDGED);
	    MongoClient client = new MongoClient(new MongoClientURI(mongoUri, builder));
		return new SimpleMongoDbFactory(client, dbname);
	}

	@Bean
	public MongoTemplate mongoDateTemplate() throws Exception {
		final MongoDbFactory factory = mongoFactory();
		final MongoMappingContext mongoMappingContext = new MongoMappingContext();
		// mongoMappingContext.setApplicationContext(context);
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		// // Learned from web, prevents Spring from including the _class attribute
		final MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		// MappingMongoConverter converter = new MappingMongoConverter(
		// new DefaultDbRefResolver(mongoFactory()), new MongoMappingContext());
		converter.setCustomConversions(customConversions());
		ConversionService convService = converter.getConversionService();
	    ((GenericConversionService)convService).addConverterFactory(new StringToEnumConverterFactory());
		converter.afterPropertiesSet();
		return new MongoTemplate(factory, converter);
	}

    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        converterList.add(new DateToOffsetDateTimeConverter());
        converterList.add(new OffsetDateTimeToDateConverter());
        converterList.add(new StringEnumConverters.EnumToStringConverter());
        converterList.add(new StringToEnumConverterFactory.StringToEnum<>(null));
        return new CustomConversions(converterList);
    }
}
