package com.scholarmatch.usecase.change_email;

import com.scholarmatch.usecase.dto.UserData;

/**
 * Output data for a successful account email change.
 *
 * @param user the updated account snapshot
 */
public record ChangeEmailOutputData(UserData user) {
}
