package org.yugzan.db.rest;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.yugzan.db.entity.InstanceEntity;
import org.yugzan.db.model.Instance;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yongzan
 * @date 2018/10/10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestController {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mvc;

  private InstanceEntity testEntity;
  private final String instanceId = "inst-test-01";
  private final String organizationName = "Develop";
  private final String spaceName = "MySpace";
  private final String organizationId = "bf319ab9-8f7d-4356-8d9e-ad7bebf6204b";
  private final String spaceId = "74fcb07b-03f2-4711-8333-8dde240aea2a";


  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    Instance instance = new Instance();
    instance.setOrganizationGuid(organizationId);
    instance.setSpaceGuid(spaceId);
    instance.setOrganizationName(organizationName);
    instance.setSpaceName(spaceName);
    testEntity = new InstanceEntity();
    testEntity.setInstance(instance);
    testEntity.setInstanceId(instanceId);
    System.out.println("init.");
  }

  @Test
  public void testCURD() throws Exception {
    testPostInstance();
    testGetInstance();
    testUpdateInstance();
    testDeleteInstance();
  }

  private void testPostInstance() throws Exception {

    MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post(InstanceAPI.INSTANCE).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testEntity)))
        .andReturn();

    MockHttpServletResponse response = result.getResponse();
    if (response.getErrorMessage() == null) {
      InstanceEntity entity =
          objectMapper.readValue(response.getContentAsString(), InstanceEntity.class);
      System.out.println(entity);
      assertEquals(testEntity.getInstanceId(), entity.getInstanceId());
      assertEquals(testEntity.getInstance(), entity.getInstance());
    } else {
      System.out.println(response.getErrorMessage());
    }
    int status = response.getStatus();
    assertEquals(HttpStatus.CREATED.value(), status);
    System.out.println(String.format("[%s] %s", status, InstanceAPI.INSTANCE));
  }


  private void testUpdateInstance() throws Exception {
    InstanceEntity newEntity = new InstanceEntity();
    newEntity.setInstanceId(testEntity.getInstanceId());

    Instance instance = new Instance();
    instance.setOrganizationGuid(organizationId + "-update");
    instance.setSpaceGuid(spaceId + "-update");
    instance.setOrganizationName(organizationName + "-update");
    instance.setSpaceName(spaceName + "-update");
    newEntity.setInstance(instance);

    MvcResult update =
        mvc.perform(MockMvcRequestBuilders.put(InstanceAPI.INSTANCE_ID, newEntity.getInstanceId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newEntity.getInstance()))).andReturn();

    assertEquals(HttpStatus.OK.value(), update.getResponse().getStatus());

    MvcResult result =
        mvc.perform(MockMvcRequestBuilders.get(InstanceAPI.INSTANCE_ID, testEntity.getInstanceId())
            .contentType(MediaType.APPLICATION_JSON)).andReturn();

    MockHttpServletResponse response = result.getResponse();

    if (response.getErrorMessage() == null) {
      System.out.println(response.getContentAsString());
      InstanceEntity entity =
          objectMapper.readValue(response.getContentAsString(), InstanceEntity.class);
      System.out.println(entity);

      assertEquals(newEntity.getInstanceId(), entity.getInstanceId());
      assertEquals(newEntity.getInstance(), entity.getInstance());
    } else {
      System.out.println(response.getErrorMessage());
    }

    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  private void testGetInstance() throws Exception {

    MvcResult result =
        mvc.perform(MockMvcRequestBuilders.get(InstanceAPI.INSTANCE_ID, testEntity.getInstanceId())
            .contentType(MediaType.APPLICATION_JSON)).andReturn();
    MockHttpServletResponse response = result.getResponse();

    if (response.getErrorMessage() == null) {
      System.out.println(response.getContentAsString());
      InstanceEntity entity =
          objectMapper.readValue(response.getContentAsString(), InstanceEntity.class);
      System.out.println(entity);
      assertEquals(testEntity.getInstanceId(), entity.getInstanceId());
      assertEquals(testEntity.getInstance(), entity.getInstance());
    } else {
      System.out.println(response.getErrorMessage());
    }

    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  private void testDeleteInstance() throws Exception {
    MvcResult result = mvc
        .perform(MockMvcRequestBuilders.delete(InstanceAPI.INSTANCE_ID, testEntity.getInstanceId())
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
  }
}
