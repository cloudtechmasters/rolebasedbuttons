package org.example;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomRoleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String grpCode = request.getHeader("GrpCode");
        String role = null;

        if ("GRP_ADMIN".equalsIgnoreCase(grpCode)) {
            role = "ROLE_ADMIN";
        } else if ("GRP_CARE".equalsIgnoreCase(grpCode)) {
            role = "ROLE_CARE";
        } else if ("GRP_FRONTLINE".equalsIgnoreCase(grpCode)) {
            role = "ROLE_FRONTLINE";
        }

        if (role != null) {
            UserDetailsImpl userDetails = new UserDetailsImpl(role);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
