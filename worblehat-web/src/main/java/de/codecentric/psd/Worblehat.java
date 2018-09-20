package de.codecentric.psd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Worblehat {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Worblehat.class, args);

		String line;
		do {
			line = scan.nextLine();
		} while (!"stop".equals(line));
		applicationContext.close();
	}
}
