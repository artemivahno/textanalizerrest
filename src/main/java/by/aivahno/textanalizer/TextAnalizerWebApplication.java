package by.aivahno.textanalizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class TextAnalizerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextAnalizerWebApplication.class, args);
	}
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("3000KB");
		factory.setMaxRequestSize("3000KB");
		return factory.createMultipartConfig();
	}
}
