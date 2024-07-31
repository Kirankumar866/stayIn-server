package com.kiran.Hotel.service;

import com.kiran.Hotel.exception.RoleAlreadyExistException;
import com.kiran.Hotel.exception.UserAlreadyExistsException;
import com.kiran.Hotel.model.Role;
import com.kiran.Hotel.model.User;
import com.kiran.Hotel.repository.RoleRepository;
import com.kiran.Hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+ theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if(roleRepo.existsByName(role)){
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }
        return roleRepo.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);

    }

    @Override
    public Role findByName(String name) {
        return roleRepo.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Role> role = roleRepo.findById(userId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepo.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");



    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Role> role = roleRepo.findById(userId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(user.get().getFirstName()+" is already assigned to the"+ role.get().getName()+ " role");
        }
        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepo.save(role.get());

        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepo.findById(roleId);
        role.get().removeAllUsersFromRole();

        return roleRepo.save(role.get());
    }
}
