package com.orderhub.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderhub.backend.model.BusinessModel;
import com.orderhub.backend.model.UserModel;


@Repository
public interface BusinessRepository extends JpaRepository<BusinessModel, Long> {

    List<BusinessModel> findByOwner(UserModel owner);

    boolean existsByOwner(UserModel owner);
}
