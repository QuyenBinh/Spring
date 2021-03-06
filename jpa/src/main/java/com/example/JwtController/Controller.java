package com.example.JwtController;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

import javax.validation.Valid;

import com.auth0.net.SignUpRequest;
import com.example.Entity.Users;
import com.example.DTO.UserDTO;
import com.example.DtoMapper.userDtoMapper;
import com.example.Entity.ConfirmationToken;
import com.example.Entity.ERole;
import com.example.Entity.Role;

import com.example.UserSevice.EmailService;
import com.example.UserSevice.UserService;
import com.example.Rebository.ConfirmationTokenRebository;
import com.example.Rebository.RoleRebository;
import com.example.Rebository.UserRebository;
import com.example.Request.LoginRequest;
import com.example.Request.SinupRequest;
import com.example.Response.JwtResponse;
import com.example.Response.MessageResponse;
import com.example.Service.UsersDetailsimpl;
import com.example.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class Controller {
    @Autowired
    private AuthenticationManager authenticator;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRebository userRebository;
    @Autowired  
    private RoleRebository roleRebository;
    @Autowired
    private ConfirmationTokenRebository confirmationTokenRebository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private userDtoMapper userDtoService;
    @PostMapping("/signin")
    public ResponseEntity<?> authencateUser(@Valid @RequestBody LoginRequest loginRequest)   {
            Authentication authentication = authenticator.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UsersDetailsimpl usersDetailsimpl = (UsersDetailsimpl) authentication.getPrincipal();
            if(!usersDetailsimpl.isEnabled()) return ResponseEntity.ok("T??i kho???n ch??a ???????c k??ch ho???t!!");
            else {
                List<String> roles = usersDetailsimpl.getAuthorities().stream()
                                                                   .map(item -> item.getAuthority())
                                                                   .collect(Collectors.toList());
                return ResponseEntity.ok(new JwtResponse(jwt, usersDetailsimpl.getId(),usersDetailsimpl.getUsername(),usersDetailsimpl.getEmail(),roles));
            }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SinupRequest sinupRequest)    {
        if(userRebository.existsByUsername(sinupRequest.getUsername())) {
            return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("L???i: T??n ng?????i d??ng ???? t???n t???i!!!"));
        }
        if(userRebository.existsByEmail(sinupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new  MessageResponse("L???i: Email ???? t???n t???i"));
        }
        UserDTO userDto = new UserDTO(sinupRequest.getUsername(),sinupRequest.getEmail(),passwordEncoder.encode(sinupRequest.getPassword()));   
        Set<String> strRoles = sinupRequest.getRole();
        Set<Role> roles = new HashSet();
        if(strRoles == null)    {
            Role userRole = roleRebository.findByName(ERole.ROLE_USER)
                                         .orElseThrow(() -> new RuntimeException("L???i kh??ng t??m th???y!!")); 
            roles.add(userRole);
             }
        else{
            strRoles.forEach(role->{
                switch(role){
                case "admin":
                    Role adminRole = roleRebository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Kh??ng t??m th???y vai tr??"));
                    roles.add(adminRole);
                    break;
                case "mod":
                    Role modRole = roleRebository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Kh??ng t??m th???y vai tr??"));
                    roles.add(modRole);
                    break;
                default :
                    Role userRole = roleRebository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Kh??ng t??m th???y vai tr??"));
                    roles.add(userRole);
                    break;
                }
            });
        }
        userDto.setRoles(roles);
        Users user = userDtoService.covertDTOtoUserEntity(userDto);
        userService.addUser(user);
        SimpleMailMessage mail = emailService.SetEmail(user);
        emailService.sendEmail(mail);
        return ResponseEntity.ok(new MessageResponse("????ng k?? t??i kho???n user th??nh c??ng!! Vui l??ng k??ch ho???t t??i kho???n"));
    }
    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String confirmToken)   {
            ConfirmationToken confirmationToken = confirmationTokenRebository.findByConfirmationtoken(confirmToken);
            if(confirmationToken != null)   {
                Users user = userRebository.findByEmail(confirmationToken.getUser().getEmail());
                user.setEnabled(true);
                userRebository.save(user);
                return ResponseEntity.ok("T??i kho???n c???a b???n ??ac ???????c k??ch ho???t");
            }
            else {
                return ResponseEntity.ok("Li??n k???t b??? h???ng?");
            }
        }
}   
