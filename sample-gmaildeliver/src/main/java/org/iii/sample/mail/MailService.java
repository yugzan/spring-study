package org.iii.sample.mail;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ch.qos.logback.classic.Logger;


/**
 * @author yugzan
 *
 */
@Component
public class MailService {
  
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	
	private MailDeliver mailDeliver;
	
	@Autowired
	public MailService(MailDeliverProperty prop) {
	  
      List<String> recipientsArray = new ArrayList<>();
      recipientsArray.add("yugzan@gmail.com");
      
      logger.debug(prop.toString() );
      
//      create MailDeliver handler mail sender
      mailDeliver = MailDeliver.builder()
              .setHost(prop.getHost())
              .setPort(prop.getPort())
              .setUserName(prop.getUsername())
              .setPassword(prop.getPassword())
              .build();
//      setting mail address which is receiver address.
      mailDeliver.setRecipients(recipientsArray);
//      send mail (title , message)
      mailDeliver.send("[important]Check your account is current", "This message is fake.");
    }

}
