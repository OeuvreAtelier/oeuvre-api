package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findByUserAccountId(String userAccountId);

    @Modifying
    @Query(value = "UPDATE users SET is_artist = :is_artist WHERE id = :id", nativeQuery = true)
    void updateArtistStatus(@Param("id") String id, @Param("is_artist") Boolean isArtist);

    @Modifying
    @Query(value = "UPDATE users SET image_picture_id = :image_picture_id WHERE id = :id", nativeQuery = true)
    void updateImagePictureId(@Param("id") String id, @Param("image_picture_id") String imagePictureId);

    @Modifying
    @Query(value = "UPDATE users SET image_banner_id = :image_banner_id WHERE id = :id", nativeQuery = true)
    void updateImageBannerId(@Param("id") String id, @Param("image_banner_id") String imageBannerId);

    @Modifying
    @Query(value = "UPDATE users SET store_id = :store_id WHERE id = :id", nativeQuery = true)
    void updateStore(@Param("id") String id, @Param("store_id") String storeId);

}