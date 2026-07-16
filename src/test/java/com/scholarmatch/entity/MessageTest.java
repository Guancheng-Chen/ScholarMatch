package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @Test
    void testGettersReturnConstructorValues() {
        final LocalDateTime sentAt = LocalDateTime.of(2026, 7, 10, 12, 34, 56);
        final Message message = new Message("msg-1", "user-1", "user-2", "Hello!", sentAt);

        assertEquals("msg-1", message.getMessageId());
        assertEquals("user-1", message.getSenderId());
        assertEquals("user-2", message.getReceiverId());
        assertEquals("Hello!", message.getContent());
        assertEquals(sentAt, message.getSentAt());
    }
}
