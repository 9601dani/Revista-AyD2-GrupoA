package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.repositories.magazine.MagazineRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class MagazineServiceImpl implements MagazineService {

    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;
    private final DocumentServiceImpl documentServiceImpl;


    @Override
    @Transactional
    public MagazineResponse saveMagazine(MagazineRequest magazine, String path) throws UserNotFoundException {
        Magazine magazineToSave = new Magazine();

        Optional<User> userOptional = this.userRepository.findById(magazine.FK_User());

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("El usuario no existe");
        }

        User user = userOptional.get();

        magazineToSave.setUser(user);

        magazineToSave.setName(magazine.name());
        magazineToSave.setDescription(magazine.description());
        magazineToSave.setCanComment(magazine.canComment());
        magazineToSave.setCanLike(magazine.canLike());
        magazineToSave.setCanLike(magazine.canLike());
        magazineToSave.setType(magazine.type());
        magazineToSave.setPrice(magazine.price());
        magazineToSave.setEnabled(magazine.isEnabled());

        Document docToSave = new Document();

        docToSave.setPath(path);
        docToSave.setMagazine(magazineToSave);

        this.documentServiceImpl.saveDocument(docToSave);

        Magazine savedMagazine = this.magazineRepository.save(magazineToSave);

        return new MagazineResponse(savedMagazine);
    }

    @Override
    public List<MagazineResponse> findAll() {

        List<Magazine> magazines = this.magazineRepository.findAll();

        return magazines.stream().map(MagazineResponse::new).toList();
    }

}
