package org.yugzan.mongo;

import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.yugzan.mongo.core.Status;
import org.yugzan.mongo.core.UserModel;
import org.yugzan.mongo.repo.UserRepository;

/**
 * @author yongzan
 * @date 2018/09/04 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Ignore
//@RunWith(JUnit4.class)
public class TestMongo {

  @Autowired
  private UserRepository repository;
  
  @Test
  public void testWriteAndQueryUser() {
    UserModel user = new UserModel("test-joy", Status.ACTIVE);
    UserModel response = repository.save( user);
    UserModel query = repository.findOne(response.getId());
    assertEquals("test-joy", query.getName());
    assertEquals(Status.ACTIVE, query.getStatus() );
  }
}
