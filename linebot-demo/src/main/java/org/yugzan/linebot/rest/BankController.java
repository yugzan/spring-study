package org.yugzan.linebot.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yugzan.linebot.erate.core.ERateService;
import org.yugzan.linebot.influx.model.BankPoint;

/**
 * @author yongzan
 * @date 2018/04/09 
 */
@RestController
public class BankController {

	@Autowired
	private ERateService service;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/q/{sql}")
	public List<BankPoint> query(@PathVariable("sql") String sql) throws Exception {
		try {
			return service.query(sql);
		} catch (Exception e) {
			throw e;
		}
	}
}
