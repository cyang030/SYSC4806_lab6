package sysc4806lab.testingweb;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    //@Test
    public void greetingShouldReturnDefaultMessage() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("addressbook");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("buddies");
    }

    //@Test
    public void addBuddyAndAddressBook() throws Exception {
        JSONObject buddyJson = new JSONObject();
        buddyJson.put("name", "Jane Smith");
        buddyJson.put("address", "123 Somewhere road");
        buddyJson.put("phone_number", "123456789");

        // add a new buddy
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>(buddyJson.toString(), headers);
        JSONObject responseJson = new JSONObject(this.restTemplate.postForObject("http://localhost:" + port + "/buddies/", request,
                String.class));
        // check the returned response
        System.out.println(responseJson);
        assertEquals(buddyJson.getString("name"), responseJson.getString("name"));
        assertEquals(buddyJson.getString("address"), responseJson.getString("address"));
        assertEquals(buddyJson.getString("phone_number"), responseJson.getString("phone_number"));

        //fetch all buddies and check it exists
        responseJson = new JSONObject(this.restTemplate.getForObject("http://localhost:" + port + "/buddies/",
                String.class));
        System.out.println(responseJson);
        responseJson = responseJson.getJSONObject("_embedded").getJSONArray("buddies").getJSONObject(0);
        assertEquals(buddyJson.getString("name"), responseJson.getString("name"));
        assertEquals(buddyJson.getString("address"), responseJson.getString("address"));
        assertEquals(buddyJson.getString("phone_number"), responseJson.getString("phone_number"));

        //add an address book
        responseJson = new JSONObject(this.restTemplate.postForObject("http://localhost:" + port + "/addressbook/",
                request, String.class));

        // check the returned response
        responseJson.has("buddies");

        //fetch all address books and check the newly add one exist
        responseJson = new JSONObject(this.restTemplate.getForObject("http://localhost:" + port + "/addressbook/",
                String.class));
        System.out.println(responseJson);
        JSONObject addresbook = responseJson.getJSONObject("_embedded").getJSONArray("addressbook").getJSONObject(0);
        addresbook.has("addressbook");
        addresbook.has("buddies");

        // associate the buddy to address book
        headers.setContentType(new MediaType("text", "uri-list"));
        request = new HttpEntity<>("http://localhost:8080/addressbook/2", headers);
        //TODO: yeah, it starts with two, creating buddy seems to have created a hidden first one
        this.restTemplate.put("http://localhost:" + port + "/buddies/1/addressbook", request);

        // check whether the address book contains this buddy
        responseJson = new JSONObject(this.restTemplate.getForObject("http://localhost:" + port + "/addressbook/2/buddies",
                String.class));
        System.out.println(responseJson);
        responseJson = responseJson.getJSONObject("_embedded").getJSONArray("buddies").getJSONObject(0);
        assertEquals(buddyJson.getString("name"), responseJson.getString("name"));
        assertEquals(buddyJson.getString("address"), responseJson.getString("address"));
        assertEquals(buddyJson.getString("phone_number"), responseJson.getString("phone_number"));

        // delete the address book and buddy
        this.restTemplate.delete("http://localhost:" + port + "/addressbook/2");
        this.restTemplate.delete("http://localhost:" + port + "/buddies/1");
        // address book should not exist
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/addressbook/2",
                String.class);
        System.out.println(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        // buddy should not exist
        responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/buddies/1",
                String.class);
        System.out.println(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}