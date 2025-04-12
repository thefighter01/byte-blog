package com.fighter.byteblog.security;

import com.fighter.byteblog.constants.ApplicationConstants;
import com.fighter.byteblog.services.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractToken(request);
            if (null != jwt) {

                UserDetails userDetails = authenticationService.validateToken(jwt);

                UsernamePasswordAuthenticationToken authObject = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authObject);

                if (userDetails instanceof BlogUserDetails) {
                    request.setAttribute("userId", ((BlogUserDetails) userDetails).getId());
                }

            }
        }catch (Exception e){
            // don't throw exception just don't authenticate the user
            log.warn("received invalid auth token " + e.getMessage());
        }

        filterChain.doFilter(request, response);

    }

    private String extractToken (HttpServletRequest request) {
        String jwt = request.getHeader(ApplicationConstants.JWT_HEADER);
        if (null != jwt && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }
        return null;
    }

}
