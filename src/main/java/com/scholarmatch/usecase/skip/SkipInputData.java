package com.scholarmatch.usecase.skip;

public final class SkipInputData {

    private final String candidateUserId;

    public SkipInputData(final String candidateUserId) {
        this.candidateUserId = candidateUserId;
    }

    public String getCandidateUserId() {
        return candidateUserId;
    }
}
