package org.springframework.cloud.samplebackendbar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@RestController
public class BarApplication {

	@RequestMapping("/foo")
	public String foo() {
		return "bar from " + getClass().getSimpleName();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(BarApplication.class)
				.properties("server.port=7080", "spring.application.name=bar")
				.run(args);
	}
}
