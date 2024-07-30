package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.ImageUrl;
import com.muffincrunchy.oeuvreapi.model.constant.UserGender;
import com.muffincrunchy.oeuvreapi.model.dto.request.*;
import com.muffincrunchy.oeuvreapi.model.dto.response.UserResponse;
import com.muffincrunchy.oeuvreapi.model.entity.*;
import com.muffincrunchy.oeuvreapi.repository.UserRepository;
import com.muffincrunchy.oeuvreapi.service.*;
import com.muffincrunchy.oeuvreapi.utils.parsing.ToResponse;
import com.muffincrunchy.oeuvreapi.utils.validation.Validation;
import com.muffincrunchy.oeuvreapi.utils.specification.UserSpecification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

import static com.muffincrunchy.oeuvreapi.model.constant.ImageUrl.USER_BANNER;
import static com.muffincrunchy.oeuvreapi.model.constant.ImageUrl.USER_PICTURE;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

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
        return users.stream().map(ToResponse::parseUser).toList();
    }

    @Override
    public Page<UserResponse> searchArtist(PagingRequest pagingRequest, SearchArtistRequest request) {
        if (pagingRequest.getPage() <= 0) {
            pagingRequest.setPage(1);
        }
        Sort sort = Sort.by(Sort.Direction.fromString(pagingRequest.getDirection()), pagingRequest.getSortBy());
        Pageable pageable = PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize(), sort);
        Specification<User> specification = UserSpecification.getSpecification(request);
        List<User> users = userRepository.findAll(specification);
        List<UserResponse> userResponses = users.stream().map(ToResponse::parseUser).toList();
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
            return ToResponse.parseUser(user);
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
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDisplayName(request.getDisplayName());
        user.setEmail(request.getEmail());
        user.setGender(genderService.getOrSave(UserGender.valueOf(request.getGender())));
        user.setBirthDate(request.getBirthDate());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUpdatedAt(new Date());
        userRepository.saveAndFlush(user);
        return ToResponse.parseUser(user);
    }

    @Override
    public void delete(String id) {
        userRepository.delete(getById(id));
    }

    @Override
    public UserResponse getByUserAccountId(String userAccountId) {
        User user = userRepository.findByUserAccountId(userAccountId).orElse(null);
        if (user != null) {
            return ToResponse.parseUser(user);
        }
        return null;
    }

    @Override
    public void updateArtistStatusById(String id, Boolean isArtist) {
        getById(id);
        userRepository.updateArtistStatus(id, isArtist);
    }

    @Override
    public void updateUserPicture(UpdateUserPictureRequest request) {
        User user = getById(request.getUserId());
        Image image;
        if (user.getImagePicture() != null) {
            String oldImageId = user.getImagePicture().getId();
            image = imageService.create(request.getImage(), USER_PICTURE);
            userRepository.updateImagePictureId(request.getUserId(), image.getId());
            imageService.deleteById(oldImageId);
        } else {
            image = imageService.create(request.getImage(), USER_PICTURE);
            userRepository.updateImagePictureId(request.getUserId(), image.getId());
        }

    }

    @Override
    public void updateUserBanner(UpdateUserBannerRequest request) {
        User user = getById(request.getUserId());
        Image image;
        if (user.getImageBanner() != null) {
            String oldImageId = user.getImageBanner().getId();
            image = imageService.create(request.getImage(), USER_BANNER);
            userRepository.updateImageBannerId(request.getUserId(), image.getId());
            imageService.deleteById(oldImageId);
        } else {
            image = imageService.create(request.getImage(), USER_BANNER);
            userRepository.updateImageBannerId(request.getUserId(), image.getId());
        }
    }

    @Override
    public void saveStore(String id, String storeId) {
        getById(id);
        userRepository.updateStore(id, storeId);
    }
}