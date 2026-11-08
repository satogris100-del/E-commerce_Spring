package com.booot.pos_system_project.service.implement;

import com.booot.pos_system_project.config.CloudService;
import com.booot.pos_system_project.dto.request.auth.LoginRequest;
import com.booot.pos_system_project.dto.request.auth.RegisterRequest;
import com.booot.pos_system_project.dto.request.auth.UserRequest;
import com.booot.pos_system_project.dto.response.LoginResponse;
import com.booot.pos_system_project.dto.response.auth.MessageResponse;
import com.booot.pos_system_project.dto.response.auth.UserResponse;
import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.exception.BadRequestException;
import com.booot.pos_system_project.exception.ResourceNotFoundException;
import com.booot.pos_system_project.mapper.UserMapper;
import com.booot.pos_system_project.model.Role;
import com.booot.pos_system_project.model.User;
import com.booot.pos_system_project.repository.RoleRepository;
import com.booot.pos_system_project.repository.UserRepository;
import com.booot.pos_system_project.security.CustomUserDetailService;
import com.booot.pos_system_project.security.JwtService;
import com.booot.pos_system_project.security.TokenBlacklistService;
import com.booot.pos_system_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImplement  implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository  roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final TokenBlacklistService tokenBlacklistService;
    private final CloudService  cloudService;

    public UserServiceImplement(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailService customUserDetailService,
            TokenBlacklistService tokenBlacklistService,
            CloudService cloudService
    ){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
        this.tokenBlacklistService = tokenBlacklistService;
        this.cloudService = cloudService;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if(userRequest.getPassword() == null || userRequest.getPassword().isBlank()
        || userRequest.getPassword().length() < 8) {
            throw new BadRequestException("Pass word length should be greater than 8 characters!");
        }
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("Email already exists! in use!");
        }
        User user = UserMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(findOrCreateRole(RoleUser.CUSTOMER));

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
        }catch (AuthenticationException error){
            throw new BadCredentialsException("Invalid username or password!", error);
        }

        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtService.generateToken(userDetails);
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new LoginResponse(userDetails.getUsername(), token, roles);
    }

    @Override
    public MessageResponse registerUser(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username already exists!");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email already exists!");
        }
        Role customerRole = findOrCreateRole(RoleUser.CUSTOMER);
        User user = new  User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.getRoles().add(customerRole);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }
    @Override
    public UserResponse getUserById(Long id) {
        return UserMapper.toResponse(finUser(id));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id,UserRequest userRequest) {
        User user = finUser(id);
        if(!user.getEmail().equalsIgnoreCase(userRequest.getEmail())
        && !userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("Email doesn't exists!");
        }
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        if(userRequest.getPassword() != null && !userRequest.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserRole(Long id, RoleUser role) {
        User user = finUser(id);
        user.getRoles().clear();
        user.getRoles().add(findOrCreateRole(role));

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse setUserEnabled(Long id, boolean enabled) {
        User user = finUser(id);
        user.setEnabled(enabled);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse unlockUser(Long id) {
        User user = finUser(id);
        user.setLockTime(null);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateProfile(Long id, UserRequest userRequest) {
        User user = finUser(id);
        if(!user.getEmail().equalsIgnoreCase(userRequest.getEmail())
        && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("Email already exists!");
        }
        user.setDisplayName(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateProfilePicture(Long id, MultipartFile file) {
        if(file.isEmpty() || file == null) {
            throw new BadRequestException("File is empty!");
        }
        String contentType = file.getContentType();
        if(!contentType.equalsIgnoreCase("image/jpeg") ||
                !contentType.equalsIgnoreCase("image/jpg") || contentType == null) {
            throw new BadRequestException("File type not supported!");
        }
        if(file.getSize() > 5L * 1024 * 1024) {
            throw new BadRequestException("File is too large!");
        }
        User user = finUser(id);
        Map<?,?> upload = cloudService.uploadFile(file, "POS_System_project/users");
        String imageUrl =uploadValue(upload, "secure_url");
        String publicId = uploadValue(upload, "secure_public_id");
        if(imageUrl == null || imageUrl.isEmpty()||  publicId == null || publicId.isEmpty()|| imageUrl.isBlank()) {
            throw new BadRequestException("File is empty!");
        }
        String previousPublicId = user.getProfilePicturePublicId();
        user.setProfilePictureUrl(imageUrl);
        user.setProfilePicturePublicId(publicId);
        UserResponse userResponse = UserMapper.toResponse(userRepository.save(user));
        if(previousPublicId != null && !previousPublicId.isBlank()){
            try{
                cloudService.delete(previousPublicId);
            }catch (RuntimeException error){
                log.warn("Error while saving user profile picture!", error);
            }
        }
        return userResponse;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void logout(String authorizationHeader) {
        if (authorizationHeader == null && !authorizationHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Authorization header is invalid!");
        }
        String token = authorizationHeader.substring(7);
        tokenBlacklistService.blacklist(token, jwtService.extractExpiration(token));
        SecurityContextHolder.clearContext();
    }
    // ----- Helper ------- //
    private User finUser(Long id){
        return userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found!")
        );
    }
    private Role findOrCreateRole(RoleUser name ){
        return roleRepository.findByName(name).orElseGet(
                () -> roleRepository.save(new Role(name))
        );
    }
    private String uploadValue(Map<?,?> upload, String key){
        Object value = upload.get(key);
        return value instanceof String text ? text:null;
    }
}
