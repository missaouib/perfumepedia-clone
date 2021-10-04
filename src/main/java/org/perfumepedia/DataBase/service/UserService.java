package org.perfumepedia.DataBase.service;

import org.perfumepedia.DataBase.component.MessageGenerator;
import org.perfumepedia.DataBase.model.Role;
import org.perfumepedia.DataBase.model.User;
import org.perfumepedia.DataBase.model.VerificationToken;
import org.perfumepedia.DataBase.repository.RoleRepository;
import org.perfumepedia.DataBase.repository.UserRepository;
import org.perfumepedia.DataBase.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {
    private static final String USER = "USER";
    private static final String VERIFY = "/verify/";
    private static final String URL = "url";
    private static final String BASEURL = "baseurl";
    private static final String CONFIRM_YOUR_REGISTRATION = "confirm.your.registration";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private MyEmailService myEmailService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MessageGenerator messageGenerator;
    @Autowired
    private HttpServletRequest request;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmed(false);
        Role userRole = roleRepository.findByRole(USER);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        VerificationToken token = new VerificationToken();
        token.setUser(user);
        verificationTokenRepository.save(token);
        Context context = new Context();
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String url = baseUrl + VERIFY + token.getToken();
        context.setVariable(URL, url);
        context.setVariable(BASEURL, baseUrl);
        String body = templateEngine.process("email-templates/registration-confirmation", context);
        myEmailService.sendEmail(user.getEmail(), messageGenerator.getMessage(CONFIRM_YOUR_REGISTRATION), body);
        return userRepository.save(user);
    }
}
