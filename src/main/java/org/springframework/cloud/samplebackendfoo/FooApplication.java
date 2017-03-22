package org.springframework.cloud.samplebackendfoo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@RestController
public class FooApplication {

	@RequestMapping("/foo")
	public String foo() {
		return "foo from " + getClass().getSimpleName();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(FooApplication.class)
				.properties("server.port=9080", "spring.application.name=foo")
				.run(args);
	}
}
