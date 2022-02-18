package com.works.glycemic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Configuration
public class AuditAwareConfig implements AuditorAware<String> {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditAwareConfig();
    }


    public List<String> roles() {
        List<String> list = new ArrayList<>();
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority > ls = aut.getAuthorities();
        ls.forEach( item -> {
            list.add(item.getAuthority());
        });
        return list;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return Optional.ofNullable(authentication.getName());
    }

}