package org.iii.sample.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
import com.google.common.base.Preconditions;
import ch.qos.logback.classic.Logger;


/**
 * @author yugzan
 *
 */

public class MailDeliver {

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    private Session session;
    private Message message;
    
    private String host = "";
    private int port = 555;
    private String username = "";
    private String password = "";
    
//  public MailDeliver(){
//      
//  }
    public MailDeliver(String host, int port ,String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    public void setRecipients(ArrayList<String> recipientsArray){
        Preconditions.checkNotNull(host);
        Preconditions.checkNotNull(port);
        Preconditions.checkNotNull(username);
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", port);


        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // JDK8 Test

        List<InternetAddress> vers = recipientsArray.stream().map( receipt -> {
            try {
                return new InternetAddress(receipt);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        })
        .filter(address -> address != null)
        .collect(Collectors.toList());
        
        try {
            
            message = new MimeMessage(session); 
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, vers.toArray(new InternetAddress[vers.size()]));
        } catch (MessagingException e) {
            e.printStackTrace();
        } 
        
    }

    public void send(String subject, String content) {
        try {

            message.setSubject(subject);
            message.setText(content);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, username, password);

            Transport.send(message);

        } catch (MessagingException e) {
            logger.info("MailDeliver exception:" + e);
        }
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public static MailDeliverBuilder builder(){
        return new MailDeliverBuilder();
    }
    
    
//   public void sendAttachFile(String subject, String content, String fn){
//   // create and fill the first message part
//   MimeBodyPart mbp1 = new MimeBodyPart();
//   try {
//  
//   message.setSubject(subject);
//   mbp1.setText(content);
//  
//   // create the second message part
//   MimeBodyPart mbp2 = new MimeBodyPart();
//  
//   // attach the file to the message
//   FileDataSource fds = new FileDataSource(Common.ReportPath+"/"+fn);
//   mbp2.setDataHandler(new DataHandler(fds));
//   mbp2.setFileName(fds.getName());
//   Multipart mp = new MimeMultipart();
//   mp.addBodyPart(mbp1);
//   mp.addBodyPart(mbp2);
//   message.setContent(mp);
//  
//   // send the message
//   Transport.send(message);
//  
//   } catch (MessagingException e) {
//       e.printStackTrace();
//   }
//  
//   }
    public static final class MailDeliverBuilder{
        private String host = "";
        private int port = 0;
        private String username = "";
        private String password = "";
        
        public MailDeliverBuilder setHost(String host){
            this.host = host;
            return this;
        }
        
        public MailDeliverBuilder setPort(int port){
            this.port = port;
            return this;
        }
        
        public MailDeliverBuilder setUserName(String username){
            this.username = username;
            return this;
        }
        
        public MailDeliverBuilder setPassword(String password){
            this.password = password;
            return this;
        }
        
        public MailDeliver build(){
            return new MailDeliver(host, port, username, password);
        }
        
    }

}
