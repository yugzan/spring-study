package org.yugzan.schedule;


import org.yugzan.slack.Connection;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;

/**
 * @author yongzan
 * @date 2016/6/8
 */
public class HelloTask {
    
    private Connection connection;
    public HelloTask() {
        connection = new Connection();
    }
    
    public void  sayHello(){
        //get a user
        SlackSession session = connection.start();
//        SlackUser user = session.findUserByUserName("karen");
        SlackChannel  channel = session.findChannelByName("general");
//        session.sendMessage( channel , "123", null, "karen", null);
//        session.sendMessageToUser(user, "YAN不想要  可能跟與好他們借就好 公司好像有", null);
        
        
        ListeningToMessageEvents events  = new ListeningToMessageEvents();
        events.registeringAListener(session);
        events.slackMessagePostedEventContent(session);
        
        
    }

    
    public static void main(String[] args) {
        new HelloTask().sayHello();
    }
}
