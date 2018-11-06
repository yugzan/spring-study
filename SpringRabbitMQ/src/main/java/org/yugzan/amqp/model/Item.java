package org.yugzan.amqp.model;

import java.util.UUID;

/**
 * @author yongzan
 * @date 2018/11/05
 */
public class Item {

  private UUID id = UUID.randomUUID();
  private String body;

  public Item() {}

  public Item(String body) {
    super();
    this.body = body;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "Item [id=" + id + ", body=" + body + "]";
  }


}
