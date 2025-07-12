package filter;

import dto.UserAuthorizationDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import util.JwtUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserInfoExtractionFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserAuthorizationDto userAuthorizationDto = extractUserInfo(request);
        List<String> authorities = extractAuthorities(request);
        userAuthorizationDto.getRoles().addAll(authorities);

        List<SimpleGrantedAuthority> grantedAuthorities = userAuthorizationDto.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                (userAuthorizationDto.getUserId(), null, grantedAuthorities);

        authentication.setDetails(userAuthorizationDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UserAuthorizationDto extractUserInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        authHeader = authHeader != null && authHeader.startsWith("Bearer ")
                ? authHeader.substring(7) : null;
        return authHeader != null ? jwtUtils.validateToken(authHeader) : new UserAuthorizationDto();
    }

    private List<String> extractAuthorities(HttpServletRequest request) {
        String authHeader = request.getHeader("X-Authorities");
        return authHeader != null ? jwtUtils.extractAuthorities(authHeader) : new ArrayList<>();
    }
}
