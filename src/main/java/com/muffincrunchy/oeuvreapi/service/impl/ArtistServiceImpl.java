package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateArtistRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ArtistResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Artist;
import com.muffincrunchy.oeuvreapi.model.entity.UserAccount;
import com.muffincrunchy.oeuvreapi.repository.ArtistRepository;
import com.muffincrunchy.oeuvreapi.service.ArtistService;
import com.muffincrunchy.oeuvreapi.service.UserAccountService;
import com.muffincrunchy.oeuvreapi.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final UserAccountService userAccountService;
    private final Validation validation;

    @Override
    public List<ArtistResponse> getAll() {
        List<Artist> artists = artistRepository.getAllArtists();
        return artists.stream().map(this::parseToResponse).toList();
    }

    @Override
    public Artist getById(String id) {
        Optional<Artist> artist = artistRepository.getArtistById(id);
        return artist.orElse(null);
    }

    @Override
    public Artist create(Artist request) {
        validation.validate(request);
        Artist artist = Artist.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .phoneNo(request.getPhoneNo())
                .email(request.getEmail())
                .userAccount(request.getUserAccount())
                .build();
        int res = artistRepository.createArtist(
                artist.getId(),
                artist.getName(),
                artist.getBirthDate(),
                artist.getPhoneNo(),
                artist.getEmail(),
                artist.getUserAccount().getId()
        );
        if (res == 1) {
            return artist;
        }
        return null;
    }

    @Override
    public ArtistResponse update(UpdateArtistRequest request) {
        validation.validate(request);
        Artist artist = getById(request.getId());
        UserAccount userAccount = userAccountService.getByContext();
        if(!userAccount.getId().equals(artist.getUserAccount().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"user not found");
        }

        int res = artistRepository.updateArtistById(
                request.getId(),
                request.getName(),
                Date.valueOf(request.getBirthDate()),
                request.getPhoneNo(),
                request.getEmail()
        );
        if (res == 1) {
            return ArtistResponse.builder()
                    .id(request.getId())
                    .name(request.getName())
                    .birthDate(Date.valueOf(request.getBirthDate()))
                    .phoneNo(request.getPhoneNo())
                    .email(request.getEmail())
                    .userAccountId(artist.getUserAccount().getId())
                    .build();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        artistRepository.deleteArtistById(id);
    }

    @Override
    public Artist getByUserId(String userId) {
        return artistRepository.findByUserAccountId(userId);
    }

    private ArtistResponse parseToResponse(Artist artist){
        String userId;
        if(artist.getUserAccount() == null){
            userId = null;
        } else {
            userId = artist.getUserAccount().getId();
        }
        return ArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .birthDate(artist.getBirthDate())
                .phoneNo(artist.getPhoneNo())
                .email(artist.getEmail())
                .userAccountId(userId)
                .build();
    }

}
