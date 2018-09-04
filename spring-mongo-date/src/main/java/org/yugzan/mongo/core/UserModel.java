package org.yugzan.mongo.core;
/**
 * @author yongzan
 * @date 2018/05/14
 */

import java.time.OffsetDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserModel {

  @Id
  private String id;

  private String name;

  private OffsetDateTime createTime;

  private OffsetDateTime modifyTime;

  private Status status;

  public UserModel() {
    this.createTime = OffsetDateTime.now();
  }

  public UserModel(String name, Status status) {
    super();
    this.name = name;
    this.modifyTime = OffsetDateTime.now();
    this.createTime = OffsetDateTime.now();
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public OffsetDateTime getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(OffsetDateTime modifyTime) {
    this.modifyTime = modifyTime;
  }

}
