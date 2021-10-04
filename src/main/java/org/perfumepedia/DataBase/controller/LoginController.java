package org.perfumepedia.DataBase.controller;

import org.perfumepedia.DataBase.component.BaseUrlGenerator;
import org.perfumepedia.DataBase.component.MessageGenerator;
import org.perfumepedia.DataBase.component.PasswordGenerator;
import org.perfumepedia.DataBase.dto.EditUserDto;
import org.perfumepedia.DataBase.dto.RememberPasswordDto;
import org.perfumepedia.DataBase.dto.UserDto;
import org.perfumepedia.DataBase.model.User;
import org.perfumepedia.DataBase.model.VerificationToken;
import org.perfumepedia.DataBase.repository.UserRepository;
import org.perfumepedia.DataBase.repository.VerificationTokenRepository;
import org.perfumepedia.DataBase.service.MyEmailService;
import org.perfumepedia.DataBase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {
    private static final String ERRORS_LOGIN_IS_TAKEN = "errors.login.is.taken";
    private static final String ERRORS_EMAIL_IS_TAKEN = "errors.email.is.taken";
    private static final String ERRORS_PASSWORD_NOT_MACH = "errors.password.not.mach";
    private static final String ERRORS_EMAIL_IS_NOT_EXIST = "errors.email.is.not.exist";
    private static final String REQUEST_TO_NEW_PASSWORD = "request.to.new.password";
    private static final String WE_SENT_EMAIL_WITH_INSTRUCTION = "we.sent.email.with.instruction";
    private static final String TOKEN_HAS_EXPIRED = "token.has.expired";
    private static final String EMAIL_HAS_BEEN_VERIFIED = "email.has.been.verified";
    private static final String INVALID_TOKEN = "invalid.token";
    private static final String CHANGES_SAVED = "changes.saved";
    private static final String USER_DTO = "userDto";
    private static final String EDIT_USER_DTO = "editUserDto";
    private static final String REMEMBER_PASSWORD_DTO = "rememberPasswordDto";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String CPASSWORD = "cpassword";
    private static final String VERIFY_ERROR = "verifyError";
    private static final String VERIFY_INFO = "verifyInfo";
    private static final String USER_LOGIN = "userLogin";
    private static final String USER_EMAIL = "userEmail";
    private static final String SUCCESS = "success";
    private static final String VERIFY = "/verify/";
    private static final String URL = "url";
    private static final String BASEURL = "baseurl";
    private static final String VERIFY_TOKEN = "/verify-token/";
    public static final String WE_SEND_NEW_PASSWORD = "we.send.new.password";
    public static final String YOUR_NEW_PASSWORD = "your.new.password";
    public static final String NEW_PASSWORD = "newPassword";

    @Autowired
    private UserService userService;
    @Autowired
    private MessageGenerator messageGenerator;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MyEmailService myEmailService;
    @Autowired
    private PasswordGenerator passwordGenerator;
    @Value("${lengthRandomPassword}")
    private Integer lengthRandomPassword;
    @Autowired
    private BaseUrlGenerator baseUrlGenerator;

    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserDto userDto = new UserDto();
        modelAndView.addObject(USER_DTO, userDto);
        modelAndView.setViewName("user/registration");
        return modelAndView;
    }

    @GetMapping(value="/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid UserDto userDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByLogin(userDto.getLogin());
        User emailExists = userService.findUserByEmail(userDto.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue(LOGIN, "error.userDto", messageGenerator.getMessage(ERRORS_LOGIN_IS_TAKEN));
        }
        if (emailExists != null) {
            bindingResult
                    .rejectValue(EMAIL, "error.userDto", messageGenerator.getMessage(ERRORS_EMAIL_IS_TAKEN));
        }
        if (!userDto.getPassword().equals(userDto.getCpassword())) {
            bindingResult
                    .rejectValue(CPASSWORD, "error.userDto", messageGenerator.getMessage(ERRORS_PASSWORD_NOT_MACH));
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/registration");
        } else {
            User user = User.builder()
                    .login(userDto.getLogin())
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .agreeRegulation(userDto.isAgreeRegulation())
                    .build();
            userService.saveUser(user);
            modelAndView.setViewName("user/registration-success");
        }
        return modelAndView;
    }

    @GetMapping(value="/admin/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        modelAndView.addObject("login", "Welcome " + user.getLogin() + "/"  + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @GetMapping("/verify/{token}")
    public String verifyEmail(@PathVariable(value = "token", required = false) String token, RedirectAttributes redirectAttrs) {
        Optional<VerificationToken> verificationTokens = verificationTokenRepository.findByToken(token);
        if (!verificationTokens.isPresent()) {
            redirectAttrs.addFlashAttribute(VERIFY_ERROR, messageGenerator.getMessage(INVALID_TOKEN));
            return "redirect:/login";
        }

        VerificationToken verificationToken = verificationTokens.get();
        if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now()) || verificationToken.getIsConsumed()) {
            redirectAttrs.addFlashAttribute(VERIFY_ERROR, messageGenerator.getMessage(TOKEN_HAS_EXPIRED));
            return "redirect:/login";
        }

        verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setIsConsumed(Boolean.TRUE);
        verificationToken.getUser().setConfirmed(true);
        verificationTokenRepository.save(verificationToken);
        redirectAttrs.addFlashAttribute(VERIFY_INFO, messageGenerator.getMessage(EMAIL_HAS_BEEN_VERIFIED));
        return "redirect:/login";
    }

    @GetMapping(value = "/verify-token/{token}")
    public ModelAndView verifyTokenToChangePassword(@PathVariable(value = "token", required = false) String token, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<VerificationToken> verificationTokens = verificationTokenRepository.findByToken(token);
        if (!verificationTokens.isPresent()) {
            modelAndView.addObject(VERIFY_ERROR, messageGenerator.getMessage(INVALID_TOKEN));
            modelAndView.setViewName("user/error-user");
            return modelAndView;
        }

        VerificationToken verificationToken = verificationTokens.get();
        if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now()) || verificationToken.getIsConsumed()) {
            modelAndView.addObject(VERIFY_ERROR, messageGenerator.getMessage(TOKEN_HAS_EXPIRED));
            modelAndView.setViewName("user/error-user");
            return modelAndView;
        }

        verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setIsConsumed(Boolean.TRUE);
        String newPasswordToSend = passwordGenerator.getRandomPassword(lengthRandomPassword);
        verificationToken.getUser().setPassword(passwordEncoder.encode(newPasswordToSend));
        verificationTokenRepository.save(verificationToken);
        //Sending new password
        Context context = new Context();
        context.setVariable(BASEURL, baseUrlGenerator.getUrl());
        context.setVariable(NEW_PASSWORD, newPasswordToSend);
        String body = templateEngine.process("email-templates/send-new-password", context);
        myEmailService.sendEmail(verificationToken.getUser().getEmail(), messageGenerator.getMessage(YOUR_NEW_PASSWORD), body);
        
        modelAndView.addObject(VERIFY_INFO, messageGenerator.getMessage(WE_SEND_NEW_PASSWORD));
        modelAndView.setViewName("user/info-user");
        return modelAndView;
    }

    @GetMapping(value="/edit-user")
    public ModelAndView editUser(RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        if (user == null)  {
            modelAndView.setViewName("redirect:/logout");
            return modelAndView;
        }
        EditUserDto editUserDTO = new EditUserDto();
        modelAndView.addObject(EDIT_USER_DTO, editUserDTO);
        modelAndView.addObject(USER_LOGIN, user.getLogin());
        modelAndView.addObject(USER_EMAIL, user.getEmail());
        modelAndView.setViewName("user/edit-user");
        return modelAndView;
    }

    @PostMapping(value="/edit-user")
    public ModelAndView saveEditUser(@Valid EditUserDto editUserDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EDIT_USER_DTO, editUserDto);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        User userExists = userService.findUserByLogin(editUserDto.getLogin());
        User emailExists = userService.findUserByEmail(editUserDto.getEmail());
        if (userExists != null && !user.getLogin().equals(editUserDto.getLogin())) {
            bindingResult.rejectValue(LOGIN, "error.editUserDto", messageGenerator.getMessage(ERRORS_LOGIN_IS_TAKEN));
            modelAndView.addObject(USER_LOGIN, editUserDto.getLogin());
        } else {
            modelAndView.addObject(USER_LOGIN, user.getLogin());
        }
        if (emailExists != null && !user.getEmail().equals(editUserDto.getEmail())) {
            bindingResult.rejectValue(EMAIL, "error.editUserDto", messageGenerator.getMessage(ERRORS_EMAIL_IS_TAKEN));
            modelAndView.addObject(USER_EMAIL, editUserDto.getEmail());
        } else {
            modelAndView.addObject(USER_EMAIL, user.getEmail());
        }
        if (!bindingResult.hasErrors()) {
            modelAndView.addObject(USER_LOGIN, editUserDto.getLogin());
            modelAndView.addObject(USER_EMAIL, editUserDto.getEmail());
            user.setLogin(editUserDto.getLogin());
            user.setEmail(editUserDto.getEmail());
            userRepository.save(user);
            modelAndView.addObject(SUCCESS, messageGenerator.getMessage(CHANGES_SAVED));
            Collection<SimpleGrantedAuthority> nowAuthorities =(Collection<SimpleGrantedAuthority>)SecurityContextHolder
                    .getContext().getAuthentication().getAuthorities();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(editUserDto.getLogin(),passwordEncoder.encode(user.getPassword()), nowAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        modelAndView.setViewName("user/edit-user");
        return modelAndView;
    }

    @GetMapping(value = "/remember-password")
    public ModelAndView showFormRememberPassword() {
        ModelAndView modelAndView = new ModelAndView();
        RememberPasswordDto rememberPasswordDto = new RememberPasswordDto();
        modelAndView.addObject(REMEMBER_PASSWORD_DTO, rememberPasswordDto);
        modelAndView.setViewName("user/remember-password");
        return modelAndView;
    }

    @PostMapping(value = "/remember-password")
    public ModelAndView sendRememberPassword(
            @Valid RememberPasswordDto rememberPasswordDto,
            HttpServletRequest request,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(USER_EMAIL, rememberPasswordDto.getEmail());
        } else {
            User userExists = userRepository.findByEmail(rememberPasswordDto.getEmail());
            if (userExists == null) {
                modelAndView.addObject(VERIFY_ERROR, messageGenerator.getMessage(ERRORS_EMAIL_IS_NOT_EXIST));
            } else {
                List<VerificationToken> tokenExist = verificationTokenRepository.findByUser(userExists);
                Optional<VerificationToken> tokenToSend = tokenExist.stream()
                        .filter(token -> !token.getIsConsumed())
                        .filter(token -> !token.getExpiredDateTime().isBefore(LocalDateTime.now()))
                        .findFirst();
                Context context = new Context();
                String conformUrlInEmail;
                if (!tokenToSend.isPresent()) {
                    VerificationToken token = new VerificationToken();
                    token.setUser(userExists);
                    verificationTokenRepository.save(token);
                    conformUrlInEmail = baseUrlGenerator.getUrl() + VERIFY_TOKEN + token.getToken();
                } else {
                    conformUrlInEmail = baseUrlGenerator.getUrl() + VERIFY_TOKEN + tokenToSend.get().getToken();
                }
                context.setVariable(URL, conformUrlInEmail);
                context.setVariable(BASEURL, baseUrlGenerator.getUrl());
                String body = templateEngine.process("email-templates/confirm-send-password", context);
                myEmailService.sendEmail(userExists.getEmail(), messageGenerator.getMessage(REQUEST_TO_NEW_PASSWORD), body);
                modelAndView.addObject(VERIFY_INFO, messageGenerator.getMessage(WE_SENT_EMAIL_WITH_INSTRUCTION));
            }
        }
        modelAndView.setViewName("user/remember-password");
        return modelAndView;
    }

}