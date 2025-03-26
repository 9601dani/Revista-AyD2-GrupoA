package com.codenbugs.ms_user.repositories.user_information;

import com.codenbugs.ms_user.models.user_information.UserHasInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHasInformationRepository extends JpaRepository<UserHasInformation, Integer> {

    Optional<UserHasInformation> findByUser_Id(Integer id);


}
