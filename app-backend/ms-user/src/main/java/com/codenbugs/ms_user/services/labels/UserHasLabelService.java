package com.codenbugs.ms_user.services.labels;


import com.codenbugs.ms_user.dtos.label.LabelRequestUserDto;
import com.codenbugs.ms_user.dtos.label.LabelResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.user.User;

import java.util.List;
import java.util.Map;

public interface UserHasLabelService {

    Map<String, String> saveLabelsForUser(LabelRequestUserDto request) throws UserNotFoundException;

    List<LabelResponseDto> getLabelsForUser(Integer fkUser);

    List<LabelResponseDto> getAllLabels();
}
