package com.ac.modulecommon.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    private static MessageSourceAccessor messageSourceAccessor;

    public static String getMessage(String key) {
        if (messageSourceAccessor == null) {
            throw new IllegalStateException("MessageSourceAccessor is not initialized.");
        }
        return messageSourceAccessor.getMessage(key);
    }

    public static String getMessage(String key, Object... params) {
        if (messageSourceAccessor == null) {
            throw new IllegalStateException("MessageSourceAccessor is not initialized.");
        }
        return messageSourceAccessor.getMessage(key, params);
    }

    public static void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        MessageUtils.messageSourceAccessor = messageSourceAccessor;
    }
}
