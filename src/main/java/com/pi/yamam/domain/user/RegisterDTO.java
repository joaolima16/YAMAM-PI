package com.pi.yamam.domain.user;

public record RegisterDTO(String name, String cpf, String email, String password, UserStatus userStatus,UserRoles role) {
    
}
