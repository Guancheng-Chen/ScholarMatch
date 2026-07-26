package com.scholarmatch.frameworks.data_access_object.server;

import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessagingGatewayTest {

    private static final String MESSAGE_JSON = """
            {"messageId":"message-1","senderId":"user-1","receiverId":"user-2",
             "content":"Hello","sentAt":"2026-07-26T12:00:00"}
            """;

    private HttpTestServer fakeServer;
    private MessagingGateway gateway;

    @BeforeEach
    void setUp() throws IOException {
        this.fakeServer = new HttpTestServer();
        final CurrentUserProviderInterface session = mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        this.gateway = new MessagingGateway(
                new ServerHttpClient(this.fakeServer.baseUrl(), session));
    }

    @AfterEach
    void tearDown() {
        this.fakeServer.stop();
    }

    @Test
    void testSendMessagePostsBodyAndParsesResponse() {
        this.fakeServer.bodyToReturn().set(MESSAGE_JSON);

        final Message message = this.gateway.sendMessage("user-2", "Hello");

        assertEquals("message-1", message.getMessageId());
        assertEquals("user-1", message.getSenderId());
        assertEquals("user-2", message.getReceiverId());
        assertEquals("Hello", message.getContent());
        assertEquals("POST", this.fakeServer.lastMethod().get());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("user-2"));
    }

    @Test
    void testGetConversationParsesMessageArray() {
        this.fakeServer.bodyToReturn().set("[" + MESSAGE_JSON + "]");

        final List<Message> messages = this.gateway.getConversation("user-2");

        assertEquals(1, messages.size());
        assertEquals("message-1", messages.getFirst().getMessageId());
        assertTrue(this.fakeServer.lastPath().get().contains("/api/messages/user-2"));
    }
}
