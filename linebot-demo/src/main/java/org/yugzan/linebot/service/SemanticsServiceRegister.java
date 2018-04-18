package org.yugzan.linebot.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.yugzan.linebot.service.annotation.EnableSemanticsService;

/**
 * @author yongzan
 * @date 2017/12/1
 */
@Service
public class SemanticsServiceRegister implements InitializingBean {

	@Autowired
	private ApplicationContext context;

	private List<SemanticsService> serviceList = new ArrayList<>();

	private void register(SemanticsService event) {
		serviceList.add(event);
	}

	public List<SemanticsService> getAllService() {
		return serviceList;
	}

//	public Options getOptions() {
//		return options;
//	}
//	
//	public CommandLine parse(String... args) throws ParseException {
//		CommandLineParser parser = new DefaultParser();
//	    return parser.parse( options, args );
//	}

	@Override
	public void afterPropertiesSet() throws Exception {
		context.getBeansWithAnnotation(EnableSemanticsService.class).forEach((name, bean) -> {
			register((SemanticsService) bean);
		});

//		serviceList.stream().forEach(action->{
//			action.initCliOption(options);
//		});
	}

}
