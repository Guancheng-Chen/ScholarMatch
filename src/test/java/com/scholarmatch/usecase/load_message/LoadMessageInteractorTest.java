package com.scholarmatch.usecase.load_message;

import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.exception.ExternalServiceException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoadMessageInteractorTest {

    private LoadMessageDataAccessInterface dataAccessObject;
    private LoadMessageOutputBoundary outputBoundary;
    private LoadMessageInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccessObject = mock(LoadMessageDataAccessInterface.class);
        outputBoundary = mock(LoadMessageOutputBoundary.class);
        interactor = new LoadMessageInteractor(dataAccessObject, outputBoundary);
    }

    @Test
    void testExecuteReturnsTheConversationOldestToNewest() {
        final Message first = new Message("msg-1", "other-1", "current-user", "Hi!", LocalDateTime.now().minusMinutes(5));
        final Message second = new Message("msg-2", "current-user", "other-1", "Hey!", LocalDateTime.now());
        when(dataAccessObject.getConversation("other-1")).thenReturn(List.of(first, second));
        interactor.execute(new LoadMessageInputData("other-1"));
        final ArgumentCaptor<LoadMessageOutputData> captor = ArgumentCaptor.forClass(LoadMessageOutputData.class);
        verify(outputBoundary).prepareSuccessView(captor.capture());
        verify(outputBoundary, never()).prepareFailView(anyString());
        final List<String> messageIds = captor.getValue().getMessages().stream()
                .map(message -> message.getMessageId())
                .toList();
        assertEquals(List.of("msg-1", "msg-2"), messageIds);
    }

    @Test
    void testExecutePassesTheGivenOtherUserIdToTheDataAccessObject() {
        when(dataAccessObject.getConversation("other-2")).thenReturn(List.of());
        interactor.execute(new LoadMessageInputData("other-2"));
        verify(dataAccessObject).getConversation("other-2");
    }

    @Test
    void testExecuteSucceedsWithAnEmptyConversation() {
        when(dataAccessObject.getConversation("other-1")).thenReturn(List.of());
        interactor.execute(new LoadMessageInputData("other-1"));
        final ArgumentCaptor<LoadMessageOutputData> captor = ArgumentCaptor.forClass(LoadMessageOutputData.class);
        verify(outputBoundary).prepareSuccessView(captor.capture());
        assertTrue(captor.getValue().getMessages().isEmpty());
    }

    @Test
    void testExecuteFailsWhenTheDataAccessObjectThrows() {
        when(dataAccessObject.getConversation(anyString()))
                .thenThrow(new ExternalServiceException("The server ran into a problem. Please try again later."));
        interactor.execute(new LoadMessageInputData("other-1"));
        verify(outputBoundary).prepareFailView("The server ran into a problem. Please try again later.");
    }
}
