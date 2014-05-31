package org.code2bytes.samples.mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class TestFilterTest {

	private TestFilter filter;

	@Before
	public void setUp() throws Exception {
		filter = new TestFilter();
	}

	@Test
	public void testDoFilter() throws Exception {
		ServletContext context = mock(ServletContext.class);
		FilterConfig filterConfig = mock(FilterConfig.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		when(context.getEffectiveMajorVersion()).thenReturn(3);
		when(filterConfig.getServletContext()).thenReturn(context);
		request.setAttribute("major.version", 3);
		chain.doFilter(request, response);
		reset(chain, request);

		filter.init(filterConfig);
		filter.doFilter(request, response, chain);

		verify(context).getEffectiveMajorVersion();
		verify(filterConfig).getServletContext();
		verify(request).setAttribute("major.version", 3);
		verify(chain).doFilter(request, response);
		verifyNoMoreInteractions(context, filterConfig, request, response, chain);
	}
}
