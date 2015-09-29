package info.smartkit.eip.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * @author yangboz
 * @see https://spring.io/guides/gs/rest-service-cors/
 */
@Component
public class SimpleCORSFilter implements Filter
{

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
        ServletException
    {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,OPTIONS, DELETE,PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        // @see:
        // http://stackoverflow.com/questions/25727306/request-header-field-access-control-allow-headers-is-not-allowed-by-access-contr
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        // @see:
        // http://stackoverflow.com/questions/10548883/request-header-field-authorization-is-not-allowed-error-tastypie
        response.addHeader("Access-Control-Allow-Headers", "Authorization");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig)
    {
    }

    @Override
    public void destroy()
    {
    }

}
