package sysc4806lab.testingweb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sysc4806lab.ctrl.ShowAddressBookController;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ShowAddressBookController controller;

    //@Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}