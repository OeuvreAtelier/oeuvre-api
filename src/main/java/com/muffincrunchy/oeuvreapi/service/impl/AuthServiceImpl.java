package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.UserRole;
import com.muffincrunchy.oeuvreapi.model.dto.request.AuthRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.RegisterRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.LoginResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.RegisterResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Artist;
import com.muffincrunchy.oeuvreapi.model.entity.Role;
import com.muffincrunchy.oeuvreapi.model.entity.UserAccount;
import com.muffincrunchy.oeuvreapi.repository.UserAccountRepository;
import com.muffincrunchy.oeuvreapi.service.ArtistService;
import com.muffincrunchy.oeuvreapi.service.AuthService;
import com.muffincrunchy.oeuvreapi.service.JwtService;
import com.muffincrunchy.oeuvreapi.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ArtistService artistService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Value("${booth-art.admin.username}")
    private String adminUsername;

    @Value("${booth-art.admin.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        Optional<UserAccount> currentUser = userAccountRepository.findByUsername(adminUsername);
        if (currentUser.isPresent()) {
            return;
        }
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role artist = roleService.getOrSave(UserRole.ROLE_ARTIST);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        UserAccount adminAccount = UserAccount.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles(List.of(admin, artist, customer))
                .isEnable(true)
                .build();
        userAccountRepository.save(adminAccount);

        Artist artistAdmin = Artist.builder()
                .name("Admin")
                .birthDate(Date.valueOf("2001-01-01"))
                .email("admin@gmail.com")
                .phoneNo("089876543210")
                .userAccount(adminAccount)
                .build();
        artistService.create(artistAdmin);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Role role = roleService.getOrSave(UserRole.ROLE_ARTIST);
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hashedPassword)
                .roles(List.of(role))
                .isEnable(true)
                .build();
        userAccountRepository.saveAndFlush(userAccount);
        Artist artist = Artist.builder()
                .name(request.getName())
                .userAccount(userAccount)
                .build();
        artistService.create(artist);
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .name(artist.getName())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role artist = roleService.getOrSave(UserRole.ROLE_ARTIST);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        return null;

    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticated = authenticationManager.authenticate(authentication);
        UserAccount userAccount = (UserAccount) authenticated.getPrincipal();
        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .id(userAccount.getId())
                .token(token)
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

}
