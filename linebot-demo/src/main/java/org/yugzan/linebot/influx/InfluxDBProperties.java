package org.yugzan.linebot.influx;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.base.MoreObjects;

/**
 * @author yongzan
 * @date 2016/11/9
 * @Ref https://github.com/miwurster/spring-data-influxdb
 */
@ConfigurationProperties("database.influxdb")
public class InfluxDBProperties {
    @NotEmpty
    private String url;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String database;

    @NotEmpty
    private String retentionPolicy;



    public String getUrl() {
        return url;
    }



    public void setUrl(String url) {
        this.url = url;
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



    public String getDatabase() {
        return database;
    }



    public void setDatabase(String database) {
        this.database = database;
    }



    public String getRetentionPolicy() {
        return retentionPolicy;
    }



    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("url", url).add("username", username)
                .add("password", password).add("database", database)
                .add("retentionPolicy", retentionPolicy).toString();
    }
}
