package tech.ian.user.dto;

import java.util.UUID;

public record CreateUserResponse(UUID id, String name, String email, String password, String document) {
}
