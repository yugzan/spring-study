package org.yugzan.db.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yugzan.db.entity.InstanceEntity;
import org.yugzan.db.model.Instance;
import org.yugzan.db.repo.InstanceEntityRepository;

/**
 * @author yongzan
 * @date 2018/10/09
 */
@Service
public class InstanceService {

  @Autowired
  private InstanceEntityRepository repo;

  public InstanceEntity insert(InstanceEntity entity) {
    if (!repo.existsById(entity.getInstanceId())) {
      return repo.save(entity);
    } else {
      throw new RuntimeException("Resource Conflict");
    }
  }

  public InstanceEntity get(String instanceId) {
    return repo.findByInstanceId(instanceId);
  }

  public List<InstanceEntity> list() {
    return repo.findAll();
  }

  public InstanceEntity replace(String instanceId, Instance newInstance) {
    InstanceEntity entity = repo.findByInstanceId(instanceId);
    entity.setInstance(newInstance);
    return repo.save(entity);
  }

  public void delete(String instanceId) {
    InstanceEntity entity = repo.findByInstanceId(instanceId);
    repo.delete(entity);
  }
}
