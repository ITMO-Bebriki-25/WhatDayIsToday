package org.bebriki.gateway.presentation.dto.login;

import java.util.Date;

public record LoginUserResponse(String token, Date expires) {
}