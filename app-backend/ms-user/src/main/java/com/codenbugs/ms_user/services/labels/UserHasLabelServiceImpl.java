package com.codenbugs.ms_user.services.labels;

import com.codenbugs.ms_user.dtos.label.LabelRequestDto;
import com.codenbugs.ms_user.dtos.label.LabelRequestUserDto;
import com.codenbugs.ms_user.dtos.label.LabelResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.labels.UserHasLabel;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.label.LabelRepository;
import com.codenbugs.ms_user.repositories.label.UserHasLabelRepository;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
@Transactional
public class UserHasLabelServiceImpl implements UserHasLabelService {

    private final UserHasLabelRepository userHasLabelRepository;

    private final LabelRepository labelRepository;

    private final UserRepository userRepository;

    @Override
    public Map<String, String> saveLabelsForUser(LabelRequestUserDto request) throws UserNotFoundException {

        Map<String, String> response = new HashMap<>();

        Optional<User> user = this.userRepository.findById(request.fkUser());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        this.userHasLabelRepository.deleteLabelsByUserId(request.fkUser());

        List<UserHasLabel> uhl = new ArrayList<>();
        List<LabelRequestDto> labels = request.labels();

        for (LabelRequestDto label : labels) {
            UserHasLabel userHasLabel = new UserHasLabel();
            Optional<Label> optLabel = this.labelRepository.findLabelByName(label.name());
            if (optLabel.isPresent()) {
                userHasLabel.setLabel(optLabel.get());
            } else {
                Label lbl = new Label();
                lbl.setName(label.name());
                Label savedLabel = this.labelRepository.save(lbl);
                userHasLabel.setLabel(savedLabel);
            }
            userHasLabel.setUser(user.get());
            uhl.add(userHasLabel);
        }

        this.userHasLabelRepository.saveAll(uhl);

        response.put("message", "Cambios guardados correctamente");
        return response;
    }

    @Override
    public List<LabelResponseDto> getLabelsForUser(Integer fkUser) {

        List<LabelResponseDto> labels = this.userHasLabelRepository.findLabelsByUserId(fkUser);

        return labels;
    }

    @Override
    public List<LabelResponseDto> getAllLabels() {

        List<Label> response = labelRepository.findAll();
        List<LabelResponseDto> responseDtos = new ArrayList<>();
        for (Label label : response) {
            responseDtos.add(new LabelResponseDto(label));
        }
        return responseDtos;
    }
}
