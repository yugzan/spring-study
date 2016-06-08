package org.yugzan.slack;

import java.io.IOException;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

/**
 * @author yongzan
 * @date 2016/6/8
 */

public class Connection {
    
    public SlackSession session ; 
    
    public Connection() {
        session = SlackSessionFactory.createWebSocketSlackSession("");
    }
    
    public SlackSession start(){
        try {
            session.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
        
    }
}
