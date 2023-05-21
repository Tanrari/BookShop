package org.example.app.service;

import org.example.web.dto.LoginForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;


@Service
public class LoginService {
    private Logger logger = org.apache.log4j.Logger.getLogger(LoginService.class);
    public boolean authenticate(LoginForm loginForm){
        logger.info("try auth with user-form:"+loginForm);
        return loginForm.getUsername().equals("root")&&loginForm.getPassword().equals("123");
    }
}
