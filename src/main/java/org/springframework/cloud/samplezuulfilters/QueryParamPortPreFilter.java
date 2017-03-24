package org.springframework.cloud.samplezuulfilters;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * @author Spencer Gibb
 */
public class QueryParamPortPreFilter extends ZuulFilter {

	public int filterOrder() {
		// run after PreDecorationFilter
		return 5 + 1;
	}

	public String filterType() {
		return "pre";
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = getCurrentContext();
		return ctx.getRequest().getParameter("port") != null;
	}

	public Object run() {
		RequestContext ctx = getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		// put the serviceId in `RequestContext`
		String port = request.getParameter("port");
		try {
			URL url = UriComponentsBuilder.fromUri(ctx.getRouteHost().toURI())
					.port(new Integer(port))
					.build().toUri().toURL();
			ctx.setRouteHost(url);
		} catch (Exception e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}
		return null;
	}
}
