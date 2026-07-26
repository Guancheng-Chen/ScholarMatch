package com.scholarmatch.usecase;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.usecase.connect.ConnectOutputData;
import com.scholarmatch.usecase.dto.MessageData;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.load_profile.LoadProfileOutputData;
import com.scholarmatch.usecase.login.LoginOutputData;
import com.scholarmatch.usecase.logout.LogoutInputData;
import com.scholarmatch.usecase.logout.LogoutOutputData;
import com.scholarmatch.usecase.register.RegisterOutputData;
import com.scholarmatch.usecase.send_message.SendMessageOutputData;
import com.scholarmatch.usecase.update_profile.UpdateProfileOutputData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class UseCaseOutputDataTest {

    @Test
    void testUserOutputDataExposesUserSnapshot() {
        final UserData user = userData();

        assertSame(user, new ConnectOutputData(user).getMatchedUser());
        assertSame(user, new LoadProfileOutputData(user).getUser());
    }

    @Test
    void testLoginAndRegistrationOutputDataExposeIdentity() {
        final LoginOutputData login = new LoginOutputData("user-1", "Ada Lovelace");
        final RegisterOutputData registration =
                new RegisterOutputData("user-2", "Grace Hopper");

        assertEquals("user-1", login.getUserId());
        assertEquals("Ada Lovelace", login.getFullName());
        assertEquals("user-2", registration.getUserId());
        assertEquals("Grace Hopper", registration.getName());
    }

    @Test
    void testMessageAndProfileUpdateOutputDataExposePayload() {
        final MessageData message = new MessageData(
                "message-1", "sender-1", "receiver-1", "Hello",
                LocalDateTime.of(2026, 7, 26, 12, 0));

        assertSame(message, new SendMessageOutputData(message).getMessage());
        assertEquals("user-1", new UpdateProfileOutputData("user-1").getUserId());
    }

    @Test
    void testEmptyLogoutDataCanBeCreated() {
        assertNotNull(new LogoutInputData());
        assertNotNull(new LogoutOutputData());
    }

    private UserData userData() {
        return new UserData(
                "user-1", "Ada", "Lovelace", "ada@example.edu", "123",
                Institution.MIT, AcademicLevel.FACULTY, ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, "Collaboration", "Research", 10,
                FundingStatus.INSTITUTIONAL_FUNDING, List.of(), List.of(), List.of(),
                5, 100);
    }
}
