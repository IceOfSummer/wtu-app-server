package pers.xds.wtuapp.web.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author DeSen Xu
 * @date 2022-12-14 16:21
 */
public enum PwdEncoder {
    /**
     * BCrypt
     */
    BCrypt(new BCryptPasswordEncoder());

    public final PasswordEncoder passwordEncoder;

    PwdEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(CharSequence sequence) {
        return this.passwordEncoder.encode(sequence);
    }

}
