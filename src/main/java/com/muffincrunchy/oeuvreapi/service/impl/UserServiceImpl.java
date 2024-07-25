package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.UserGender;
import com.muffincrunchy.oeuvreapi.model.dto.request.PagingRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.SearchArtistRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateUserRequest;
import com.muffincrunchy.oeuvreapi.model.dto.response.ImageResponse;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.entity.Image;
import com.muffincrunchy.oeuvreapi.model.entity.User;
import com.muffincrunchy.oeuvreapi.model.entity.UserAccount;
import com.muffincrunchy.oeuvreapi.repository.UserRepository;
import com.muffincrunchy.oeuvreapi.service.GenderService;
import com.muffincrunchy.oeuvreapi.service.ImageService;
import com.muffincrunchy.oeuvreapi.service.UserService;
import com.muffincrunchy.oeuvreapi.service.UserAccountService;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import com.muffincrunchy.oeuvreapi.utils.specification.UserSpecification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.PRODUCT_IMG_URL;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserAccountService userAccountService;
    private final GenderService genderService;
    private final ImageService imageService;
    private final Validation validation;

    @PostConstruct
    public void init() {
        for (UserGender gender : UserGender.values()) {
            genderService.getOrSave(gender);
        }
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::parseToResponse).toList();
    }

    @Override
    public Page<UserResponse> searchArtist(PagingRequest pagingRequest, SearchArtistRequest request) {
        String sortBy = "displayName";
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), sortBy);
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        Specification<User> specification = UserSpecification.getSpecification(request);
        List<User> users = userRepository.findAll(specification);
        List<UserResponse> userResponses = users.stream().map(this::parseToResponse).toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), userResponses.size());
        return new PageImpl<>(userResponses.subList(start, end), pageable, userResponses.size());
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserResponse getResponseById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return parseToResponse(user);
        }
        return null;
    }

    @Override
    public User create(User request) {
        validation.validate(request);
        request.setCreatedAt(new Date());
        request.setUpdatedAt(new Date());
        return userRepository.saveAndFlush(request);
    }

    @Override
    public UserResponse update(UpdateUserRequest request) {
        validation.validate(request);
        User user = getById(request.getId());
        UserAccount userAccount = userAccountService.getByContext();
        if(!userAccount.getId().equals(user.getUserAccount().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"user not found");
        }
        if (request.getImage() != null) {
            if (user.getImage() != null) {
                String oldImageId = user.getImage().getId();
                Image newImage = imageService.create(request.getImage());
                user.setImage(newImage);
                imageService.deleteById(oldImageId);
            } else {
                Image newImage = imageService.create(request.getImage());
                user.setImage(newImage);
            }
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDisplayName(request.getDisplayName());
        user.setEmail(request.getEmail());
        user.setGender(genderService.getOrSave(UserGender.valueOf(request.getGender())));
        user.setBirthDate(request.getBirthDate());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUpdatedAt(new Date());
        userRepository.saveAndFlush(user);
        return parseToResponse(user);
    }

    @Override
    public void delete(String id) {
        userRepository.delete(getById(id));
    }

    @Override
    public User getByUserAccountId(String userAccountId) {
        return userRepository.findByUserAccountId(userAccountId);
    }

    @Override
    public void updateArtistStatusById(String id, Boolean isArtist) {
        getById(id);
        userRepository.updateArtistStatus(id, isArtist);
    }

    private UserResponse parseToResponse(User user){
        String userId = null;
        ImageResponse imageResponse = null;
        if(user.getUserAccount() != null){
            userId = user.getUserAccount().getId();
        }
        if (user.getImage() != null) {
            imageResponse = ImageResponse.builder()
                    .url(PRODUCT_IMG_URL + user.getImage().getId())
                    .path(user.getImage().getPath())
                    .name(user.getImage().getName())
                    .build();
        }
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .isArtist(user.isArtist())
                .userAccountId(userId)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .image(imageResponse)
                .build();
    }
}