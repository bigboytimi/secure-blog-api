package com.moonshade.week10secureblogapi.security;

import com.moonshade.week10secureblogapi.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 This class is responsible for intercepting every request for verification and authentication
 before passing them onto the DispatcherServlet. First we confirm if the Header is not null
 and if it starts with "Bearer". If it is null and does not start with "Bearer", we move to the next filter chain.
 Otherwise, we move to retrieving the token needed for verification. We then call the JWTService to confirm the username
 is in the database by passing in the token, if found, it returns the username OR details
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final CustomUserDetailsService  customUserDetailsService;



    @Override
    protected void doFilterInternal(final @NotNull HttpServletRequest request,
                                    final @NotNull HttpServletResponse response,
                                    final @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/auth/signup") ) {
            filterChain.doFilter(request, response);
        }

        //look for bearer auth header
        final String header = request.getHeader("Authorization");

        String username = null;
        String token = null;
        if(header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtTokenService.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token");
            }
        }else{
            log.warn("JWT token does not belong to Bearer String");

        }

        /*
        Check if the username is not null and to check if there is an authenticated user.
        If user is authenticated, we can bypass verifying and updating the security context.
        Then let the already authenticated user access the dispatcherServlets.
         */

        /*
        In this case, the user is not authenticated, so we must perform the necessary checks.
         */
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            CustomUserDetails customUserDetails = this.customUserDetailsService.loadUserByUsername(username); //find if the user exists

            //if token is valid configure spring security to manually set authentication
            if(jwtTokenService.validateToken(token, customUserDetails)){//check if the existing user is the same as the one passed in with the token
                //if yes, set the token and pass in the necessary details and authorities
                UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(customUserDetails,
                        null, customUserDetails.getAuthorities());
                //set the details of the request to the authenticated token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //after setting the authentication in the context, specify that the current user
                //is authenticated so that it passes the spring security configurations check.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
