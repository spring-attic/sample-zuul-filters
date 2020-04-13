package org.springframework.cloud.samplebackendfoo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@RestController
public class FooApplication {

	@RequestMapping(value = "/foo", method = GET)
	public String fooGet(@RequestParam(value = "ak", required = false) String ak) {
		return "foo from " + getClass().getSimpleName() + ", recieved request param: " + ak;
	}

	@RequestMapping(value = "/foo", method = POST)
	public String foo(@RequestBody String body) {
		return "foo recieved: " + body;
	}

	@RequestMapping(value = "/hello", method = GET)
	public String hello() {
		return "hello from " + getClass().getSimpleName();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(FooApplication.class)
				.properties("server.port=9080", "spring.application.name=foo")
				.run(args);
	}
}
