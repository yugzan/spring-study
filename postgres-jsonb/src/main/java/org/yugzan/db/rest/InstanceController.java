package org.yugzan.db.rest;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yugzan.db.entity.InstanceEntity;
import org.yugzan.db.model.Instance;
import org.yugzan.db.service.InstanceService;

/**
 * @author yongzan
 * @date 2018/10/09
 */
@RestController
public class InstanceController {

  @Autowired
  private InstanceService service;

  @GetMapping(value = InstanceAPI.INSTANCE)
  public List<InstanceEntity> list() {
    return service.list();
  }

  @GetMapping(value = InstanceAPI.INSTANCE_ID)
  @ResponseStatus(value = HttpStatus.OK)
  public InstanceEntity get(@PathVariable String instanceId) {
    return service.get(instanceId);
  }

  @PostMapping(value = InstanceAPI.INSTANCE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public InstanceEntity insert(@RequestBody InstanceEntity entity) {
    Objects.requireNonNull(entity, "entity is requirement.");
    if (entity.getInstanceId() == null) {
      entity.setInstanceId("inst-" + UUID.randomUUID());
    }

    return service.insert(entity);
  }

  @PutMapping(value = InstanceAPI.INSTANCE_ID)
  @ResponseStatus(value = HttpStatus.OK)
  public InstanceEntity update(@PathVariable String instanceId, @RequestBody Instance instance) {
    return service.replace(instanceId, instance);
  }

  @DeleteMapping(value = InstanceAPI.INSTANCE_ID)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String instanceId) {
    service.delete(instanceId);
  }
}
