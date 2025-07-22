package com.example.auth.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        log.info("User {} logged in successfully", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AuthenticationFailureDisabledEvent failureEvent) {
        log.error("User {} logged in unsuccessfull due to {}",
                failureEvent.getAuthentication().getName(),
                failureEvent.getException().getMessage());
    }
}
