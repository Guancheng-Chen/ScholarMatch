package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.Message;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageDataTest {

    @Test
    void testGettersReturnConstructorValues() {
        final LocalDateTime sentAt = LocalDateTime.of(2026, 7, 10, 12, 34, 56);
        final MessageData messageData = new MessageData("msg-1", "user-1", "user-2", "Hello!", sentAt);

        assertEquals("msg-1", messageData.getMessageId());
        assertEquals("user-1", messageData.getSenderId());
        assertEquals("user-2", messageData.getReceiverId());
        assertEquals("Hello!", messageData.getContent());
        assertEquals(sentAt, messageData.getSentAt());
    }

    @Test
    void testFromCopiesEveryField() {
        final LocalDateTime sentAt = LocalDateTime.of(2026, 7, 10, 12, 34, 56);
        final Message message = new Message("msg-1", "user-1", "user-2", "Hello!", sentAt);

        final MessageData data = MessageData.from(message);

        assertEquals(message.getMessageId(), data.getMessageId());
        assertEquals(message.getSenderId(), data.getSenderId());
        assertEquals(message.getReceiverId(), data.getReceiverId());
        assertEquals(message.getContent(), data.getContent());
        assertEquals(message.getSentAt(), data.getSentAt());
    }

    @Test
    void testFromAllPreservesOrder() {
        final LocalDateTime sentAt = LocalDateTime.of(2026, 7, 10, 12, 34, 56);
        final Message first = new Message("msg-1", "user-1", "user-2", "Hi", sentAt);
        final Message second = new Message("msg-2", "user-2", "user-1", "Hi back", sentAt.plusMinutes(1));

        final List<MessageData> result = MessageData.fromAll(List.of(first, second));

        assertEquals(2, result.size());
        assertEquals(first.getMessageId(), result.get(0).getMessageId());
        assertEquals(second.getMessageId(), result.get(1).getMessageId());
    }
}
