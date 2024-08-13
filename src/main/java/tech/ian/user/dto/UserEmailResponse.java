package tech.ian.user.dto;

import java.util.UUID;

public record UserEmailResponse(UUID id, String name, String email) {
}
