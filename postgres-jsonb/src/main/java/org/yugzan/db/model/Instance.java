package org.yugzan.db.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author yongzan
 * @date 2018/10/10
 */
public class Instance implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("organization_guid")
  private String organizationGuid;

  @JsonProperty("organization_name")
  private String organizationName;

  @JsonProperty("space_guid")
  private String spaceGuid;

  @JsonProperty("space_name")
  private String spaceName;

  public Instance() {

  }

  public String getOrganizationGuid() {
    return organizationGuid;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public String getSpaceGuid() {
    return spaceGuid;
  }

  public String getSpaceName() {
    return spaceName;
  }

  public void setOrganizationGuid(String organizationGuid) {
    this.organizationGuid = organizationGuid;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public void setSpaceGuid(String spaceGuid) {
    this.spaceGuid = spaceGuid;
  }

  public void setSpaceName(String spaceName) {
    this.spaceName = spaceName;
  }

  @Override
  public String toString() {
    return "Instance [organizationGuid=" + organizationGuid + ", organizationName="
        + organizationName + ", spaceGuid=" + spaceGuid + ", spaceName=" + spaceName + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((organizationGuid == null) ? 0 : organizationGuid.hashCode());
    result = prime * result + ((organizationName == null) ? 0 : organizationName.hashCode());
    result = prime * result + ((spaceGuid == null) ? 0 : spaceGuid.hashCode());
    result = prime * result + ((spaceName == null) ? 0 : spaceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Instance other = (Instance) obj;
    if (organizationGuid == null) {
      if (other.organizationGuid != null)
        return false;
    } else if (!organizationGuid.equals(other.organizationGuid))
      return false;
    if (organizationName == null) {
      if (other.organizationName != null)
        return false;
    } else if (!organizationName.equals(other.organizationName))
      return false;
    if (spaceGuid == null) {
      if (other.spaceGuid != null)
        return false;
    } else if (!spaceGuid.equals(other.spaceGuid))
      return false;
    if (spaceName == null) {
      if (other.spaceName != null)
        return false;
    } else if (!spaceName.equals(other.spaceName))
      return false;
    return true;
  }


}
