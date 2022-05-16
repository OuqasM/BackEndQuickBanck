package backEndQuickBank.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtBankAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader("Authorization");
		System.out.println(request.getServletPath());

		if(request.getServletPath().equals("/login")) 
			{
				System.out.println("hnaaaaaaaa");
				filterChain.doFilter(request, response);	
			}
		else {
			if(jwt != null && jwt.startsWith("Bearer ")) {
				try {
					System.out.println("inside if jwt ");
					jwt = jwt.substring(7);
					Algorithm algorithm = Algorithm.HMAC256("mysecreteHahaha");
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT claims =	verifier.verify(jwt);
					String username = claims.getSubject();
					String[] roles = claims.getClaim("role").asArray(String.class);
					Collection<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
					for(String role:roles) {
						ga.add(new SimpleGrantedAuthority(role));
					}
					System.out.println("sssss");
					UsernamePasswordAuthenticationToken user =
							new UsernamePasswordAuthenticationToken(username,null,ga);	
					SecurityContextHolder.getContext().setAuthentication(user);
					System.out.println("authentifier : -->  ! "+SecurityContextHolder.getContext().getAuthentication().getName());
					filterChain.doFilter(request, response);
					} catch (Exception e) {
						response.setHeader("Error", e.getMessage());
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
					}
				}else {
					System.out.println("header required bearer");
				}
		}
		
	}

}
