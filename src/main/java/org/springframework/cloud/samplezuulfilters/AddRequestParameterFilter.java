package org.springframework.cloud.samplezuulfilters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.util.MultiValueMap;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * @author dengliming
 */
public class AddRequestParameterFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = getCurrentContext();
        return context.getRequest().getParameter("service") != null;
    }

    @Override
    public Object run() {
        RequestContext context = getCurrentContext();
        ProxyRequestHelper requestHelper = new ProxyRequestHelper();
        MultiValueMap<String, String> params = requestHelper.buildZuulRequestQueryParams(context.getRequest());
        // add a param
        params.add("ak", "Test Add Param");
        context.setRequestQueryParams(params);
        return null;
    }
}
