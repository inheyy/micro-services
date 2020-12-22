package org.sid.authenticationservice.service;

import org.sid.authenticationservice.Entity.AppRole;
import org.sid.authenticationservice.Entity.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username,String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
    List<AppRole> listRoles();

}
