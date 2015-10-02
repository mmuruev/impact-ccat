package impact.cat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	AnnotationConfigApplicationContext context;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Press any key to exit");
        Scanner scanner = new Scanner(System.in);
        while(!scanner.hasNext()){
            Thread.sleep(100);
        }

        context.close();
    }
}