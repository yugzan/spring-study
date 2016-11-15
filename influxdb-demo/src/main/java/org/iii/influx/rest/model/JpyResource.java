package org.iii.influx.rest.model;

import java.util.Collection;
/**
 * @author yongzan
 * @date 2016/11/14
 */
public class JpyResource {
    private Collection<Collection<String>> data;
    
    public JpyResource(){}
    
    public JpyResource(Collection<Collection<String>> data) {
        super();
        this.data = data;
    }

    public Collection<Collection<String>> getData() {
        return data;
    }

    public void setData(Collection<Collection<String>> data) {
        this.data = data;
    }

}
