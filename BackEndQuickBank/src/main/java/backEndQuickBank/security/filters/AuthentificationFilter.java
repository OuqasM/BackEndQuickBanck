package backEndQuickBank.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import backEndQuickBank.requests.LoginRequest;

public class AuthentificationFilter extends UsernamePasswordAuthenticationFilter{

	
	private AuthenticationManager authenticationManager;
	
	public AuthentificationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
			
		try {
			LoginRequest lr = new ObjectMapper().readValue(request.getInputStream(),LoginRequest.class);
			System.out.println(lr.getPassword() + "----"+ lr.getUsername());
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
					lr.getUsername(), lr.getPassword(),new ArrayList<>());
			System.out.println("an hnaa");
			Authentication authResult = authenticationManager.authenticate(upat);
			System.out.println("-----------> "+authResult);
			return authResult;
		} catch (Exception e) {
			System.out.println("err auth : "+e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		System.out.println("authentication success ! ");
		User user = (User)authResult.getPrincipal();
		List<String> authorities = user.getAuthorities().stream().map(a ->a.getAuthority()).collect(Collectors.toList());
		
		Algorithm algorithm = Algorithm.HMAC256("mysecreteHahaha");
		String accessToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+60*24*60*1000))
				.withIssuer(request.getRequestURI())
				.withClaim("role",authorities)
				.sign(algorithm);
				
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access-token", accessToken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(),tokens);
	
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {		// TODO Auto-generated method stub

		System.out.println("authentication not success ! ");

	}
	
	
	
	
}
