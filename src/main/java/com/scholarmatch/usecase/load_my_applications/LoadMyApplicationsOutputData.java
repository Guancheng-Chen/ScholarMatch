package com.scholarmatch.usecase.load_my_applications;

import com.scholarmatch.usecase.dto.PostingApplicationData;
import java.util.List;

public record LoadMyApplicationsOutputData(List<PostingApplicationData> applications) {
    public LoadMyApplicationsOutputData {
        applications = List.copyOf(applications);
    }
}
