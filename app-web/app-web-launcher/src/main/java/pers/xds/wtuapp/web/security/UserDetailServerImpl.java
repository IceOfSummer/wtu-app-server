package pers.xds.wtuapp.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.mapper.UserMapper;

/**
 * @author DeSen Xu
 * @date 2022-08-31 16:24
 */
@Service
public class UserDetailServerImpl implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("no such user found");
        }
        return new UserPrincipal(username, user.getPassword(), user.getRole(), user.getUserId(), user.getWtuUsername());
    }

}
