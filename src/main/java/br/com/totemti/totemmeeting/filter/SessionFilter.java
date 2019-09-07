package br.com.totemti.totemmeeting.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import br.com.totemti.totemmeeting.util.TokenUtil;
import io.jsonwebtoken.SignatureException;

@Component
public class SessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if (isValidToken(httpRequest)) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			httpResponse.setStatus(401);
		}
		
	}
	
	private boolean isValidToken(HttpServletRequest httpRequest) {
		
		if (httpRequest.getRequestURI().startsWith("/sessions")) {
			return true;
		}
		
		String token = httpRequest.getHeader("Authorization");
		
		if (token == null) {
			return false;
		}
		
		try {
			TokenUtil.getUserIdFromSubject(token);
		} catch(IllegalArgumentException | SignatureException e) {
			return false;
		}
		
		return true;
	}
}
