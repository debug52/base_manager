package com.debug52.spring.security.security;

import com.debug52.utils.util.DecryptUtils;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {
    public DefaultPasswordEncoder() {
        this(-1);
    }

    public DefaultPasswordEncoder(int strength) {
    }

    @SneakyThrows
    @Override
    public String encode(CharSequence charSequence) {
        return DecryptUtils.encryption(charSequence.toString());

    }

    @SneakyThrows
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        System.out.println(DecryptUtils.encryption(charSequence.toString()));
        return s.equals(DecryptUtils.encryption(charSequence.toString()));

    }
}
