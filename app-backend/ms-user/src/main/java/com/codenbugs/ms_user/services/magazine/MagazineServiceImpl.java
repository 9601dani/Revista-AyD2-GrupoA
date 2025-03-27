package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.clients.UploadRestClient;
import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.DocumentResponse;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineWithDocumentsResponse;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.magazine.DocumentRepository;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.repositories.magazine.MagazineRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
    private final DocumentRepository documentRepository;
    private final UploadRestClient uploadRestClient;


    @Override
    @Transactional
    public MagazineResponse saveMagazine(MagazineRequest magazine, MultipartFile path) throws UserNotFoundException {
        Magazine magazineToSave = new Magazine();

        Optional<User> userOptional = this.userRepository.findById(magazine.FK_User());

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("El usuario no existe");
        }

        HashMap<String,String> path_saved = this.uploadRestClient.uploadFile(path);

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
        Magazine savedMagazine = this.magazineRepository.save(magazineToSave);


        docToSave.setPath(path_saved.getOrDefault(path_saved.keySet().iterator().next(), null));
        docToSave.setMagazine(savedMagazine);

        this.documentServiceImpl.saveDocument(docToSave);

        return new MagazineResponse(savedMagazine);
    }

    @Override
    public List<MagazineWithDocumentsResponse> getByUserId(Integer userId) throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("El usuario no existe");
        }

        User user = userOptional.get();

        List<Magazine> allMagazines = magazineRepository.findAll(); //

        return allMagazines.stream()
                .filter(magazine -> magazine.getUser().getId().equals(user.getId()))
                .map(magazine -> {
                    List<Document> documents = documentRepository.findAllByMagazine_Id(magazine.getId());

                    List<DocumentResponse> documentResponses = documents.stream()
                            .map(document -> new DocumentResponse(
                                    document.getId(),
                                    document.getMagazine().getId(),
                                    document.getPath()
                            ))
                            .collect(Collectors.toList());

                    return new MagazineWithDocumentsResponse(
                            magazine,documentResponses
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MagazineResponse> getAllMagazines() throws UserNotFoundException {
       List<Magazine> magazines = this.magazineRepository.findAll();
       return magazines.stream().map(MagazineResponse::new).collect(Collectors.toList());
    }


}
