# Sample Zuul Filters

Run the 3 application classes as spring boot applications.

- `ZuulGatewayApplication`: runs zuul on port 8080
- `FooApplication`: hosts a `/foo` service on port 9080
- `BarApplication`: hosts a `/foo` service with a different implementation on port 7080


There are a number of filters are implemented:

- `AddResponseHeaderFilter`: adds a random `X-Foo` header to the response
- `ModifyResponseBodyFilter`: Adds a prefix to the response using `RequestContext.setResponseBody(String)`. Only runs if `service` query parameter is NOT set.
- `ModifyResponseDataStreamFilter`: Adds a prefix to the response using `RequestContext.setResponseDataStream(InputStream)`. Only runs if `service` query parameter is set.
- `QueryParamServiceIdPreFilter`: sets the `serviceId` to the value of the `service` query parameter.

## Examples

All examples use [HTTPie](https://httpie.org/)(A better curl replacement).

```bash
$ http :8080/foo 
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Date: Wed, 22 Mar 2017 20:06:45 GMT
Transfer-Encoding: chunked
X-Application-Context: application
X-Foo: 41e04412-84e8-48ac-8d71-cec209eda9ca

Modified via setResponseBody(): foo from FooApplication$$EnhancerBySpringCGLIB$$82229332
```

The application is routed to `FooApplication` because the response contains `foo from FooApplication`.

The `AddResponseHeaderFilter` ran because of the `X-Foo: 41e04412-84e8-48ac-8d71-cec209eda9ca` header was added to the response.

The `ModifyResponseBodyFilter` was run because the body was prefixed with `Modified via setResponseBody():`.

```bash
$ http ":8080/foo?service=bar"
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Date: Wed, 22 Mar 2017 20:09:23 GMT
Transfer-Encoding: chunked
X-Application-Context: application
X-Foo: 9dc2e5fa-a43f-445d-a289-d3e62319413a

Modified via setResponseDataStream(): bar from BarApplication$$EnhancerBySpringCGLIB$$a78c1598

```

The application is routed to `BarApplication` because the response contains `bar from BarApplication`.

The `AddResponseHeaderFilter` ran because of the `X-Foo: 9dc2e5fa-a43f-445d-a289-d3e62319413a` header was added to the response.

The `ModifyResponseDataStreamFilter` was run because the body was prefixed with `Modified via setResponseDataStream():`.