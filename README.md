# Sample Zuul Filters

Run the 3 application classes as spring boot applications.

- `ZuulGatewayApplication`: runs zuul on port 8080
- `FooApplication`: hosts a `/foo` service on port 9080
- `BarApplication`: hosts a `/foo` service with a different implementation on port 7080


There are a number of filters are implemented:

- `AddResponseHeaderFilter`: adds a random `X-Foo` header to the response
- `ModifyResponseBodyFilter`: Adds a prefix to the response using `RequestContext.setResponseBody(String)`. Only runs if `service` query parameter is NOT set.
- `ModifyResponseDataStreamFilter`: Adds a prefix to the response using `RequestContext.setResponseDataStream(InputStream)`. Only runs if `service` query parameter is set.
- `PrefixRequestEntityFilter`: Adds a prefix to the request body by using a request wrapper and `RequestContext.setRequest()`. Only runs if `service` query parameter is set.
- `QueryParamPortPreFilter`: sets the port of the `RequestContext.getRouteHost` to the value of the `port` query parameter. Only runs if `port` query parameter is set.
- `QueryParamServiceIdPreFilter`: sets the `serviceId` to the value of the `service` query parameter.
- `UppercaseRequestEntityFilter`: Upper cases the request body by setting `RequestContext.setRequest("requestEntity")`. Only runs if `service` query parameter is NOT set.

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

```bash

$ echo -n "hello" | http POST :8080/foo
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Wed, 22 Mar 2017 21:48:46 GMT
Transfer-Encoding: chunked
X-Application-Context: application
X-Foo: 69866ea1-db9a-4275-a8f1-5e20d87e5065

Modified via setResponseBody(): foo recieved: HELLO
```

`UppercaseRequestEntityFilter` was run because `foo recieved: HELLO` was in the response body.

```bash

$ echo -n "hello" | http POST ":8080/foo?service=bar"
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Date: Wed, 22 Mar 2017 21:49:02 GMT
Transfer-Encoding: chunked
X-Application-Context: application
X-Foo: 97aebe27-6845-4dc3-9ef6-80ccdf14059d

Modified via setResponseDataStream(): bar recieved: request body modified via request wrapper: hello
```

`PrefixRequestEntityFilter` was run because `foo recieved: HELLO` was in the response body.

```bash
$ http :8080/hello
HTTP/1.1 500 
Connection: close
Content-Type: application/json;charset=UTF-8
Date: Fri, 24 Mar 2017 21:05:04 GMT
Transfer-Encoding: chunked
X-Application-Context: application

{
    "error": "Internal Server Error",
    "exception": "org.apache.http.conn.HttpHostConnectException",
    "message": "Connect to localhost:80 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1, localhost/fe80:0:0:0:0:0:0:1%1] failed: Connection refused",
    "status": 500,
    "timestamp": 1490389504014
}
```
Uses a bad port, `QueryParamPortPreFilter` not run.

```bash
$ http ":8080/hello?port=9080"
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Date: Fri, 24 Mar 2017 21:07:07 GMT
Transfer-Encoding: chunked
X-Application-Context: application
X-Foo: 50774952-b947-47cd-902b-2889d02feece

Modified via setResponseBody(): hello from FooApplication$$EnhancerBySpringCGLIB$$82229332

```

`QueryParamPortPreFilter` was run because response contains `hello from FooApplication`.