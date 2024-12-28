package com.solta.global.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class SaltEncryption implements EncryptUtils {

    @Override
    public String encrypt(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    @Override
    public boolean isMatch(String plain, String crypto) {
        return BCrypt.checkpw(plain, crypto);
    }
}
