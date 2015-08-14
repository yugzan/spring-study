package org.iii.sample.mail;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;


/**
 * @author yugzan
 *
 */
@Service("mailservice")
public class MailService {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MailDeliverProperty prop;
	
	private MailDeliver mailDeliver;
	
	public void Init(){
		ArrayList<String> recipientsArray = new ArrayList<String>();
		recipientsArray.add("yugzan@gmail.com");
		
		logger.debug(prop.toString() );
		
        mailDeliver = MailDeliver.builder()
                .setHost(prop.getHost())
                .setPort(prop.getPort())
                .setUserName(prop.getUsername())
                .setPassword(prop.getPassword())
                .build();
        mailDeliver.setRecipients(recipientsArray);
        mailDeliver.send("[important]Check your account is current", "This message is fake.");
	}
}
