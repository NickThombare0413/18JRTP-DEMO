package in.ashokit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
         int a=10;
         int b=20;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

             System.out.println();
             System.out.println("priority task done");
             System.out.println("addition of and b is :"+(a+b));
              System.out.println("start class ended...!");

	}


   



}
