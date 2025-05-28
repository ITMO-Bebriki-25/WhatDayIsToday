package org.bebriki.gateway.utils.jwt;

import java.util.Date;

public record TokenDto(String token, Date expires) {
}
