package org.yugzan.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.yugzan.db.model.Instance;

@Entity
@Table(name = "instance")
public class InstanceEntity extends BaseEntity {

  @Id
  @Column(name = "instanceId")
  private String instanceId;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private Instance instance;


  public InstanceEntity() {

  }
  
  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public Instance getInstance() {
    return instance;
  }

  public void setInstance(Instance instance) {
    this.instance = instance;
  }

  @Override
  public String toString() {
    return "InstanceEntity [instanceId=" + instanceId + ", instance=" + instance + "]";
  }


}
