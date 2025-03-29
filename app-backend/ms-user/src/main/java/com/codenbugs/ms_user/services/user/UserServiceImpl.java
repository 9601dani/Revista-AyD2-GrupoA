package com.codenbugs.ms_user.services.user;

import com.codenbugs.ms_user.dtos.module.ModuleResponseDto;
import com.codenbugs.ms_user.dtos.user.LoginRequestDto;
import com.codenbugs.ms_user.dtos.user.UserReponseDto;
import com.codenbugs.ms_user.dtos.user.UserRequestDto;
import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.exceptions.UserNotAllowedException;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.page.Page;
import com.codenbugs.ms_user.models.role.Role;
import com.codenbugs.ms_user.models.module.Module;
import com.codenbugs.ms_user.models.role_has_page.RoleHasPage;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.models.user_has_role.UserHasRole;
import com.codenbugs.ms_user.models.user_information.UserHasInformation;
import com.codenbugs.ms_user.repositories.modules.ModuleRepository;
import com.codenbugs.ms_user.repositories.role_has_page.RoleHasPageRepository;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.repositories.user_has_role.UserHasRoleRepository;
import com.codenbugs.ms_user.repositories.user_information.UserHasInformationRepository;
import com.codenbugs.ms_user.services.token.TokenService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserHasInformationRepository userHasInformationRepository;
    private final UserHasRoleRepository userHasRoleRepository;
    private final ModuleRepository moduleRepository;
    private final RoleHasPageRepository roleHasPageRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public UserReponseDto register(UserRequestDto userRequestDto) throws UserNotCreatedException {
        Optional<User> userExists = userRepository.findByEmail(userRequestDto.email());
        if (userExists.isPresent()) {
            throw new UserNotCreatedException("El email esta duplicado");
        }

        userExists = userRepository.findByUsername(userRequestDto.username());
        if (userExists.isPresent()) {
            throw new UserNotCreatedException("El username esta duplicado");
        }

        User newUser = new User();
        newUser.setUsername(userRequestDto.username());
        newUser.setEmail(userRequestDto.email());
        newUser.setPassword(passwordEncoder.encode(userRequestDto.password()));

        User createdUser = userRepository.save(newUser);

        UserHasInformation userHasInformation = new UserHasInformation();
        userHasInformation.setUser(createdUser);
        userHasInformation.setPhoto_path("");
        userHasInformation.setName("");
        userHasInformation.setDescription("");
        userHasInformation.setAge(0);
        userHasInformation.setCurrent_balance(BigDecimal.valueOf(0));

        this.userHasInformationRepository.save(userHasInformation);

        Role role = new Role();
        role.setId(2);

        UserHasRole userHasRole = new UserHasRole();
        userHasRole.setRole(role);
        userHasRole.setUser(createdUser);

        this.userHasRoleRepository.save(userHasRole);

        return new UserReponseDto(createdUser);
    }

    @Override
    public UserReponseDto login(LoginRequestDto request) throws SettingNotFoundException, UserNotAllowedException, UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findByUsernameOrEmail(request.usernameOrEmail(), request.usernameOrEmail());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("El usuario no existe");
        }

        User user = userOptional.get();
        
        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UserNotAllowedException("La constraseña es incorrecta");
        }

        String token = tokenService.getToken(user);
        user.setAuthToken(token);

        this.userRepository.save(user);
        return new UserReponseDto(user);

    }

    public List<ModuleResponseDto> getPages(Integer id) {
        List<Module> modules = this.moduleRepository.findModulesByUserId(id);
        System.out.println(modules);
        List<RoleHasPage> rolePages = this.roleHasPageRepository.findRolePageByUserId(id);

        for(Module module: modules) {
            List<Page> pages = rolePages.stream().map(RoleHasPage::getPage)
                    .filter(p -> p.getModule().getId() == module.getId())
                    .toList();

            module.setPages(pages);
        }

        List<ModuleResponseDto> moduleResponses = modules.stream()
                .map(ModuleResponseDto::new).toList();
        return moduleResponses;
    }

}
