package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserAccountId(String userAccountId);

    @Modifying
    @Query(value = "UPDATE users SET is_artist = :is_artist WHERE id = :id", nativeQuery = true)
    void updateArtistStatus(@Param("id") String id, @Param("is_artist") Boolean isArtist);

}
