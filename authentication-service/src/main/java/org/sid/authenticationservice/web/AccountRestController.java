package org.sid.authenticationservice.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.authenticationservice.Entity.AppRole;
import org.sid.authenticationservice.Entity.AppUser;
import org.sid.authenticationservice.JWTUtil;
import org.sid.authenticationservice.service.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
@RestController
public class AccountRestController {
    private AccountService accountService;
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping(path = "/users")
    @PostAuthorize("hasAuthority('USER')")
   // @PreAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers() {
        return accountService.listUsers();
    }
    @GetMapping(path = "/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    // @PreAuthorize("hasAuthority('USER')")
    public List<AppRole> appRoles() {
        return accountService.listRoles();
    }
    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public AppUser saveUser(@RequestBody AppUser appUser) {
        return accountService.addNewUser(appUser);
    }
    @PostMapping(path = "/roles")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole saveRole(@RequestBody AppRole appRole) {
        return accountService.addNewRole(appRole);
    }
    @PostMapping(path = "/addRoleToUser")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }
    @GetMapping(path = "/refreshToken")
    @PostAuthorize("hasAuthority('USER')")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String authToken=request.getHeader(JWTUtil.AUTH_HEADER);
        if(authToken!=null && authToken.startsWith(JWTUtil.PREFIX)){
            try {
                String jwt=authToken.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm=Algorithm.HMAC256(JWTUtil.SECTET);
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                //decodedJWT contient les claims
                DecodedJWT decodedJWT=jwtVerifier.verify(jwt);
                String username=decodedJWT.getSubject();
                AppUser appUser=accountService.loadUserByUsername(username);
                String jwtAccessToken= JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACCESS_TOKEN))
                        //nom de l'app qui a généréee le token
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                        //signer
                        .sign(algorithm);
                Map<String ,String> idToken= new HashMap<>();
                idToken.put("access-token",jwtAccessToken);
                idToken.put("refresh-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);

            }catch (Exception e){
                throw e;
            }
        }
        else {
           throw new RuntimeException("Refresh Token required !");
        }
    }
    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal){
        return accountService.loadUserByUsername(principal.getName());
    }

}
