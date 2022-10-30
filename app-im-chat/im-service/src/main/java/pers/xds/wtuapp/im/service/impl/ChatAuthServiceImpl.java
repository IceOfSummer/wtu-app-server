package pers.xds.wtuapp.im.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.im.service.ChatAuthService;

/**
 * @author DeSen Xu
 * @date 2022-09-05 22:17
 */
@Service
public class ChatAuthServiceImpl implements ChatAuthService {

    private SessionRepository sessionRepository;

    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";


    @Autowired
    public void setSessionRepository(RedisIndexedSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }


    @Override
    public UsernamePasswordAuthenticationToken findUser(String session) {
        Session id = sessionRepository.findById(session);
        if (id == null) {
            return null;
        }
        SecurityContext context = id.getAttribute(SPRING_SECURITY_CONTEXT);
        return (UsernamePasswordAuthenticationToken) context.getAuthentication();
    }

}
