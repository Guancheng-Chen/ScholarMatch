package com.scholarmatch.usecase.send_message;

import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SendMessageInteractorTest {

    private SendMessageDataAccessInterface dataAccessObject;
    private SendMessageOutputBoundary outputBoundary;
    private SendMessageInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccessObject = mock(SendMessageDataAccessInterface.class);
        outputBoundary = mock(SendMessageOutputBoundary.class);
        interactor = new SendMessageInteractor(dataAccessObject, outputBoundary);
    }

    private String captureFailMessage(final SendMessageInputData inputData) {
        interactor.execute(inputData);
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(outputBoundary).prepareFailView(captor.capture());
        return captor.getValue();
    }

    @Test
    void testExecuteSucceedsWithValidMessage() {
        final Message sent = new Message("msg-1", "sender-1", "receiver-1", "Hello!", LocalDateTime.now());
        when(dataAccessObject.sendMessage("receiver-1", "Hello!")).thenReturn(sent);
        interactor.execute(new SendMessageInputData("receiver-1", "Hello!"));
        verify(dataAccessObject).sendMessage("receiver-1", "Hello!");
        verify(outputBoundary).prepareSuccessView(any());
        verify(outputBoundary, never()).prepareFailView(anyString());
    }

    @Test
    void testValidationFailurePreventsDataAccessCall() {
        interactor.execute(new SendMessageInputData("receiver-1", ""));


        verify(dataAccessObject, never()).sendMessage(anyString(), anyString());
        verify(outputBoundary, never()).prepareSuccessView(any());
    }

    @Test
    void testFailsWhenContentBlank() {
        assertTrue(captureFailMessage(new SendMessageInputData("receiver-1", "   "))
                .contains("Message cannot be empty."));
    }

    @Test
    void testFailsWhenContentNull() {
        assertTrue(captureFailMessage(new SendMessageInputData("receiver-1", null))
                .contains("Message cannot be empty."));
    }

    @Test
    void testFailsWhenContentTooLong() {
        final String tooLong = "x".repeat(1001);
        assertTrue(captureFailMessage(new SendMessageInputData("receiver-1", tooLong))
                .contains("Message must be at most 1000 characters (currently 1001)."));
    }

    @Test
    void testSucceedsWithContentAtExactlyMaxLength() {
        final String maxLength = "x".repeat(1000);
        final Message sent = new Message("msg-1", "sender-1", "receiver-1", maxLength, LocalDateTime.now());
        when(dataAccessObject.sendMessage("receiver-1", maxLength)).thenReturn(sent);
        interactor.execute(new SendMessageInputData("receiver-1", maxLength));
        verify(dataAccessObject).sendMessage("receiver-1", maxLength);
        verify(outputBoundary, never()).prepareFailView(anyString());
    }

    @Test
    void testFailsWhenReceiverIdBlank() {
        assertTrue(captureFailMessage(new SendMessageInputData("", "Hello!"))
                .contains("Receiver is required."));
    }

    @Test
    void testExecuteFailsWhenServerRejectsMessage() {
        when(dataAccessObject.sendMessage(anyString(), anyString()))
                .thenThrow(new InvalidRequestException("Users have not mutually matched"));
        interactor.execute(new SendMessageInputData("receiver-1", "Hello!"));
        verify(outputBoundary).prepareFailView("Users have not mutually matched");
    }
}
