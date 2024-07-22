package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ArtistRepository extends JpaRepository<Artist, String> {

    @Query(value = "SELECT * FROM artists", nativeQuery = true)
    List<Artist> getAllArtists();

    @Query(value = "SELECT * FROM artists WHERE id = :id", nativeQuery = true)
    Optional<Artist> getArtistById(@Param("id") String id);

    @Modifying
    @Query(value = "INSERT INTO artists (id, name, birth_date, phone_no, email, user_account_id) VALUES (:id, :name, :birth_date, :phone_no, :email, :user_account_id)", nativeQuery = true)
    int createArtist(@Param("id") String id, @Param("name") String name, @Param("birth_date") Date birthDate, @Param("phone_no") String phoneNo, @Param("email") String email, @Param("user_account_id") String userAccountId);

    @Modifying
    @Query(value = "UPDATE artists SET name = :name, birth_date = :birth_date, phone_no = :phone_no, email = :email WHERE id = :id", nativeQuery = true)
    int updateArtistById(@Param("id") String id, @Param("name") String name, @Param("birth_date") Date birthDate, @Param("phone_no") String phoneNo, @Param("email") String email);

    @Modifying
    @Query(value = "DELETE FROM artists WHERE id = :id", nativeQuery = true)
    void deleteArtistById(@Param("id") String id);

    Artist findByUserAccountId(String userAccountId);
}
