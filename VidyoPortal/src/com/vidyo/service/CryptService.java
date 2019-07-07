package com.vidyo.service;

import com.vidyo.framework.security.utils.VidyoUtil;
import org.springframework.stereotype.Service;

@Service
public class CryptService {

    public String encrypt(String source) {
        return VidyoUtil.encrypt(source);
    }

    public String decrypt(String source) {
        return VidyoUtil.decrypt(source);
    }
}
