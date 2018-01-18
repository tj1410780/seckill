package com.mengdi.filter;
/**
 * 字符编码转换
 * 注意事项：一定要在web.xml中配置过滤器（我用的注解没有反应）
 * 过滤器实现Filer接口，不需要扫描成为bean
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

public class SeckillFilter implements Filter {
	private FilterConfig config;

    public SeckillFilter() {
       
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;			
		String charSet = config.getInitParameter("charSet");
		if (charSet == null) {
			charSet = "UTF-8";
		} 
		req.setCharacterEncoding(charSet);
		chain.doFilter(req, res);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		config = fConfig;
	}

}
