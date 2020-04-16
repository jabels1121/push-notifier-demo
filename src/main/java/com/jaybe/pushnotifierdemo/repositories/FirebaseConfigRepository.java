package com.jaybe.pushnotifierdemo.repositories;

import com.jaybe.pushnotifierdemo.domain.fcm.FcmInitConfigEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FirebaseConfigRepository extends CrudRepository<FcmInitConfigEntity, String> {

    Optional<FcmInitConfigEntity> findByAppName(String appName);

}
