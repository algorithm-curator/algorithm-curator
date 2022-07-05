package com.ac.moduleapi.security;

import com.ac.moduleapi.controller.auth.AuthDto;
import com.ac.moduleapi.service.user.UserService;
import com.ac.modulecommon.entity.user.Role;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.jwt.Jwt;
import com.ac.modulecommon.jwt.JwtAuthentication;
import com.ac.modulecommon.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final UserService userService;

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return createUserAuthentication(authenticationToken.getAuthenticationId());
    }

    private Authentication createUserAuthentication(Long authenticationId) {
        try {
            User user = userService.getUserByOauthId(authenticationId);
            JwtAuthentication jwtAuthentication = JwtAuthentication.from(user);
            JwtAuthenticationToken authenticationToken =
                    JwtAuthenticationToken.of(jwtAuthentication, createAuthorityList(Role.USER.value()));

            String apiToken = jwt.createApiToken(user, new String[]{Role.USER.value()});
            authenticationToken.setDetails(AuthDto.AuthResponse.of(apiToken, user));

            return authenticationToken;
        } catch (ApiException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }
}
