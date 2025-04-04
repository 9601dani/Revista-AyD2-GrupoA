package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.clients.UploadRestClient;
import com.codenbugs.ms_user.dtos.request.MagazineRequest;
import com.codenbugs.ms_user.dtos.response.AllMagazineResponse;
import com.codenbugs.ms_user.dtos.response.DocumentResponse;
import com.codenbugs.ms_user.dtos.response.MagazineResponse;
import com.codenbugs.ms_user.dtos.response.MagazineWithDocumentsResponse;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.magazine.MagazineHasCategory;
import com.codenbugs.ms_user.models.magazine.MagazineHasLabel;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.magazine.*;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.services.labels.UserHasLabelServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private final CategoryService categoryService;
    private final CategoryService categoryServiceImpl;
    private final UserHasLabelServiceImpl userHasLabelService;
    private final MagazineHasCategoryRepository magazineHasCategory;
    private final MagazineHasLabelRepository magazineHasLabel;


    @Override
    @Transactional
    public MagazineResponse saveMagazine(MagazineRequest magazine) throws UserNotFoundException {
        Magazine magazineToSave = new Magazine();

        Optional<User> userOptional = this.userRepository.findById(magazine.FK_User());

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("El usuario no existe");
        }

        MultipartFile file = magazine.file();

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío o no se envió.");
        }

        HashMap<String,String> path_saved = this.uploadRestClient.uploadFile(magazine.file());

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


        if(magazine.categories() != null){
            magazine.categories().forEach( category -> {
                MagazineHasCategory magazineHasCategory = new MagazineHasCategory();
                category.setId(this.categoryService.findByName(category.getName().toLowerCase()).getId());
                magazineHasCategory.setCategory(category);
                magazineHasCategory.setMagazine(magazineToSave);

                this.magazineHasCategory.save(magazineHasCategory);
            });

        }

        if(magazine.labels() != null){
            magazine.labels().forEach(label -> {
                MagazineHasLabel magazineHasLabel = new MagazineHasLabel();
                label.setId(this.userHasLabelService.findByName(label.getName()).getId());
                magazineHasLabel.setLabel(label);
                magazineHasLabel.setMagazine(magazineToSave);

                this.magazineHasLabel.save(magazineHasLabel);
            });
        }




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

        List<Magazine> userMagazines = magazineRepository.findByUserId(user.getId());

        return userMagazines.stream()
                .map(MagazineWithDocumentsResponse::new)
                .toList();
    }

    @Override
    public List<AllMagazineResponse> getAllMagazines(){
       List<Magazine> magazines = this.magazineRepository.findAll();
       return magazines.stream().map(AllMagazineResponse::new).collect(Collectors.toList());
    }

    @Override
    public AllMagazineResponse getMagazineById(Integer magazineId) {
        return new AllMagazineResponse(this.magazineRepository.findById(magazineId));
    }

    @Override
    @Transactional
    public AllMagazineResponse updateMagazine(MagazineRequest magazine) throws UserNotFoundException {
        Magazine magazineToUpdate = this.magazineRepository.findById(magazine.id());

        Optional<User> userOptional = this.userRepository.findById(magazine.FK_User());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("El usuario no existe");
        }

        magazineToUpdate.setName(magazine.name());
        magazineToUpdate.setDescription(magazine.description());
        magazineToUpdate.setCanComment(magazine.canComment());
        magazineToUpdate.setCanLike(magazine.canLike());
        magazineToUpdate.setCanSubscribe(magazine.canSubscribe());
        magazineToUpdate.setType(magazine.type());
        magazineToUpdate.setPrice(magazine.price());
        magazineToUpdate.setEnabled(magazine.isEnabled());

        this.magazineHasCategory.deleteByMagazineId(magazine.id());
        this.magazineHasLabel.deleteByMagazineId(magazine.id());

        if(magazine.categories() != null){
            magazine.categories().forEach(category -> {
                MagazineHasCategory mhc = new MagazineHasCategory();
                category.setId(this.categoryService.findByName(category.getName().toLowerCase()).getId());
                mhc.setCategory(category);
                mhc.setMagazine(magazineToUpdate);
                this.magazineHasCategory.save(mhc);
            });
        }

       if(magazine.labels() != null){
           magazine.labels().forEach(label -> {
               MagazineHasLabel mhl = new MagazineHasLabel();
               label.setId(this.userHasLabelService.findByName(label.getName()).getId());
               mhl.setLabel(label);
               mhl.setMagazine(magazineToUpdate);
               this.magazineHasLabel.save(mhl);
           });
       }

        MultipartFile file = magazine.file();
        if (file != null && !file.isEmpty()) {
            HashMap<String, String> pathSaved = this.uploadRestClient.uploadFile(file);

            Document document = new Document();
            document.setPath(pathSaved.getOrDefault(pathSaved.keySet().iterator().next(), null));
            document.setMagazine(magazineToUpdate);

            this.documentServiceImpl.saveDocument(document);
        }

        this.magazineRepository.save(magazineToUpdate);

        return new AllMagazineResponse(magazineToUpdate);
    }


}
