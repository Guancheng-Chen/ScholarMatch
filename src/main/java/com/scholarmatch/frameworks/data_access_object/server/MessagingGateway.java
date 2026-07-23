package com.scholarmatch.frameworks.data_access_object.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP implementation of sending and loading chat messages between matched users — the
 * "messaging" actor's slice of what used to be ServerRepository.
 */
public final class MessagingGateway implements SendMessageDataAccessInterface, LoadMessageDataAccessInterface {

    private final ServerHttpClient http;

    public MessagingGateway(final ServerHttpClient http) {
        this.http = http;
    }

    @Override
    public Message sendMessage(final String receiverId, final String content) {
        final String body = this.http.toJson(Map.of("receiverId", receiverId, "content", content));
        return messageFromJson(this.http.post("/api/messages", body, true));
    }

    @Override
    public List<Message> getConversation(final String otherUserId) {
        final JsonNode array = this.http.get("/api/messages/" + otherUserId);
        final List<Message> result = new ArrayList<>();
        for (final JsonNode node : array) {
            result.add(messageFromJson(node));
        }
        return result;
    }

    private Message messageFromJson(final JsonNode node) {
        return new Message(
                node.get("messageId").asText(),
                node.get("senderId").asText(),
                node.get("receiverId").asText(),
                node.get("content").asText(),
                LocalDateTime.parse(node.get("sentAt").asText()));
    }
}
