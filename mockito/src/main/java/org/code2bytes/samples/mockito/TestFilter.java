package org.code2bytes.samples.mockito;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter
public class TestFilter implements Filter {

	private int majorVersion;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		majorVersion = filterConfig.getServletContext().getEffectiveMajorVersion();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setAttribute("major.version", majorVersion);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// empty
	}
}
