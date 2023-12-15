package com.robin.springbootbackend.role;

import com.robin.springbootbackend.product.Product;
import com.robin.springbootbackend.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }
    public Optional<Role> getRole(UUID userID) {
        return roleRepository.findById(userID);
    }
}