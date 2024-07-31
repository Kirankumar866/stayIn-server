package com.kiran.Hotel.controller;

import com.kiran.Hotel.exception.RoleAlreadyExistException;
import com.kiran.Hotel.model.Role;
import com.kiran.Hotel.model.User;
import com.kiran.Hotel.service.IRoleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getRoles(), FOUND);

    }


    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole){
        try{
            roleService.createRole(theRole);
            return ResponseEntity.ok("New role created successfully!");


        }catch(RoleAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        }

    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId){
        return roleService.removeAllUsersFromRole(roleId);

    }
    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(@RequestParam("roleId") Long roleId, @RequestParam("userId") Long userId){
        return roleService.removeUserFromRole(userId,roleId);

    }

    @PostMapping("/assign-user-to-role")
    public User assignRoleToUser(@RequestParam("roleId") Long roleId, @RequestParam("userId") Long userId){
        return roleService.assignRoleToUser(userId,roleId);

    }




}
