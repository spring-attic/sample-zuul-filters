package org.springframework.cloud.samplebackendbar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@RestController
public class BarApplication {

	@RequestMapping(value = "/foo", method = GET)
	public String foo() {
		return "bar from " + getClass().getSimpleName();
	}

	@RequestMapping(value = "/foo", method = POST)
	public String foo(@RequestBody String body) {
		return "bar recieved: " + body;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(BarApplication.class)
				.properties("server.port=7080", "spring.application.name=bar")
				.run(args);
	}
}
