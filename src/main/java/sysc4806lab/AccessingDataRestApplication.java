package sysc4806lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.support.Repositories;

@SpringBootApplication
public class AccessingDataRestApplication {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(AccessingDataRestApplication.class, args);
    }

    public static Repositories getRepositories() {
        Repositories repositories = new Repositories(applicationContext);


        return repositories;
    }
}