package com.solta.global.util;

public interface EncryptUtils {

    String encrypt(String plain);
    boolean isMatch(String plain, String crypto);
}
