package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;

import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class UserDataTest {

    private User fullUser() {
        final User user = new User(
                "user-1",
                "Ada",
                "Lovelace",
                "ada@example.com",
                "555-0000",
                Institution.UNIVERSITY_OF_CAMBRIDGE,
                AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR,
                "Looking for co-authors",
                "Analytical engines and algorithms",
                8,
                FundingStatus.INSTITUTIONAL_FUNDING,
                "hash");
        user.addResearchInterest("computing");
        user.addEducation(new Education("Cambridge", DegreeType.MASTER, 1840, Month.SEPTEMBER, 1843, Month.JUNE));
        user.addPublication(new Publication("10.1000/example", "Notes on the Analytical Engine", 1843, 100));
        user.sethIndex(5);
        user.setTotalCitations(200);
        return user;
    }

    @Test
    void testFromCopiesEveryField() {
        final User user = fullUser();

        final UserData data = UserData.from(user);

        assertEquals(user.getUserId(), data.getUserId());
        assertEquals(user.getFirstName(), data.getFirstName());
        assertEquals(user.getLastName(), data.getLastName());
        assertEquals(user.getEmail(), data.getEmail());
        assertEquals(user.getPhoneNumber(), data.getPhoneNumber());
        assertEquals(user.getInstitution(), data.getInstitution());
        assertEquals(user.getAcademicLevel(), data.getAcademicLevel());
        assertEquals(user.getResearchField(), data.getResearchField());
        assertEquals(user.getLookingFor(), data.getLookingFor());
        assertEquals(user.getCollaborationDescription(), data.getCollaborationDescription());
        assertEquals(user.getResearchDescription(), data.getResearchDescription());
        assertEquals(user.getWeeklyAvailabilityHours(), data.getWeeklyAvailabilityHours());
        assertEquals(user.getFundingStatus(), data.getFundingStatus());
        assertEquals(user.getResearchInterests(), data.getResearchInterests());
        assertEquals(user.getEducations(), data.getEducations());
        assertEquals(user.getPublications(), data.getPublications());
        assertEquals(user.gethIndex(), data.gethIndex());
        assertEquals(user.getTotalCitations(), data.getTotalCitations());
    }

    @Test
    void testFromAllPreservesOrder() {
        final User first = fullUser();
        final User second = fullUser();

        final List<UserData> result = UserData.fromAll(List.of(first, second));

        assertEquals(2, result.size());
        assertEquals(first.getUserId(), result.get(0).getUserId());
        assertEquals(second.getUserId(), result.get(1).getUserId());
    }

    @Test
    void testGettersReturnDefensiveCopies() {
        final UserData data = UserData.from(fullUser());

        assertNotSame(data.getResearchInterests(), data.getResearchInterests());
        assertNotSame(data.getEducations(), data.getEducations());
        assertNotSame(data.getPublications(), data.getPublications());
    }
}
