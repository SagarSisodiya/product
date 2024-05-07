package com.invento.product.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DatabseAuditing implements AuditorAware<String>{
	
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String uname = "system";
        if (authentication != null) {
            uname = authentication
                    .getName();
        }
        return Optional.of(uname);
    }
}
