package org.yugzan.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yugzan.db.entity.InstanceEntity;

public interface InstanceEntityRepository extends JpaRepository<InstanceEntity, String> {

  public InstanceEntity findByInstanceId(String instanceId);
}
