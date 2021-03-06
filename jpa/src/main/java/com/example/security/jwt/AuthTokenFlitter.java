package com.example.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Service.UserDetailServiceimpls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


public class AuthTokenFlitter extends OncePerRequestFilter{
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired 
    private UserDetailServiceimpls userDetailServiceimpls;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                try{
                    // lay jwt tu request
                    String jwt = parseJwt(request);
                    if(jwt != null && jwtUtils.validateJwttoken(jwt))   {
                        
                        // lay ten user tu request
                        String username =  jwtUtils.getUserNameFromJwtToken(jwt);
                        // lay tt ng dung
                        UserDetails userDetails = userDetailServiceimpls.loadUserByUsername(username);
                        // hop le -> set tt cho security context
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }catch(Exception e) {
                    logger.error("Kh??ng th??? x??c th???c", e);
                }
        filterChain.doFilter(request, response);
    }
    public String parseJwt(HttpServletRequest request)  {
        String headerAuth = request.getHeader("Authorization");
        // ke header authorization co chua jwt?
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) return headerAuth.substring(7, headerAuth.length());
        return null;
    }
}
