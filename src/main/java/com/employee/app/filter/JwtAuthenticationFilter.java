package com.employee.app.filter;

import com.employee.app.common.EncryptAndDecrypt;
import com.employee.app.config.DatasourcePropertyCondition;
import com.employee.app.config.jwt.JwtService;
import com.employee.app.utilservice.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Conditional(DatasourcePropertyCondition.class)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (StringUtils.isEmpty(authHeader)|| !StringUtils.startsWith(authHeader,"Bearer ")){
            request.setAttribute("token", "token Missing");
            filterChain.doFilter(request, response);
            return;
        }

        jwt=authHeader.substring(7);
        try{
            userEmail = EncryptAndDecrypt.decrypt(jwtService.extractUsername(jwt));
            if(StringUtils.isNoneEmpty(userEmail)&& SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else {
                    request.setAttribute("token", "invalid token");
                }
            }
        }catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e){
            request.setAttribute("token", "token missing");
        }catch (ExpiredJwtException ex){
            request.setAttribute("token", "token expired");
        }catch (Exception e){
            request.setAttribute("token", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
