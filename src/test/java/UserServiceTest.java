import main.java.com.tsystems.superrailroad.model.dto.UserDto;
import main.java.com.tsystems.superrailroad.model.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "main/webapp/WEB-INF/applicationContext.xml")
public class UserServiceTest {
    @Autowired
    @Qualifier("userService")
    UserService userService;

    @Test
    public void testUserService(){
        UserDto userDto = new UserDto();
        userDto.setLogin("admin");
        assertTrue(userService.userExists(userDto));
    }
}
