package com.fashionmall.user.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.jwt.JwtUtil;
import com.fashionmall.common.jwt.LoginRequestDto;
import com.fashionmall.common.jwt.UserRoleEnum;
import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.common.redis.RedisUtil;
import com.fashionmall.common.redis.RefreshToken;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.DeliveryAddressRequestDto;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.request.SignUpRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.dto.response.LoginResponseDto;
import com.fashionmall.user.dto.response.UserInfoResponseDto;
import com.fashionmall.user.entity.DeliveryAddress;
import com.fashionmall.user.entity.User;
import com.fashionmall.user.repository.DeliveryAddressRepository;
import com.fashionmall.user.repository.FavoriteRepository;
import com.fashionmall.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fashionmall.user.entity.Favorite;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j(topic = "favoriteService")
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final FavoriteRepository favoriteRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final ModuleApiUtil moduleApiUtil;

    // User
    @Override
    @Transactional
    public Long signUp (SignUpRequestDto signUpRequestDto) {

        String email = signUpRequestDto.getEmail();
        String nickName = signUpRequestDto.getNickName();
        validateUser(email,nickName);

        String encodePassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        UserRoleEnum role = signUpRequestDto.isAdmin() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;

        User user = User.builder()
                .email(email)
                .password(encodePassword)
                .userName(signUpRequestDto.getUserName())
                .nickName(nickName)
                .contact(signUpRequestDto.getContact())
                .role(role)
                .build();

        userRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional
    public LoginResponseDto login (@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {

        String emails = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();


        User user = userRepository.findByEmail(emails)
                .orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emails, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = user.getUserName();
        UserRoleEnum role = user.getRole();
        Long userId = user.getId();

        String accessToken = jwtUtil.createToken(email, role,userId, jwtUtil.ACCESS_TOKEN_EXPIRATION_TIME);
        String refreshToken = jwtUtil.createToken(email, role, userId, jwtUtil.REFRESH_TOKEN_EXPIRATION_TIME);
        System.out.println("Generated refresh token: " + refreshToken);

        jwtUtil.createTokenToCookie("access_token", accessToken, jwtUtil.ACCESS_TOKEN_EXPIRATION_TIME);
        jwtUtil.createTokenToCookie("refresh_token", refreshToken, jwtUtil.REFRESH_TOKEN_EXPIRATION_TIME);

        RefreshToken redisToken = new RefreshToken(userId, refreshToken);
        redisUtil.set("refreshToken:" + userId, String.valueOf(redisToken), 86400);

        jwtUtil.addCookiesToResponse(response, accessToken, refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);

        return LoginResponseDto.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public Long updateUserInfo (UpdateUserInfoRequestDto updateUserInfoRequestDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_USER_ID));

        if (updateUserInfoRequestDto.getOldPassword() != null && updateUserInfoRequestDto.getOldPassword().equals(user.getPassword())) {
            user.updatePassword(updateUserInfoRequestDto.getNewPassword());
        }

        if (updateUserInfoRequestDto.getNickName() != null) {
            user.updateNickname(updateUserInfoRequestDto.getNickName());
        }
        if (updateUserInfoRequestDto.getContact() != null) {
            user.updateContact(updateUserInfoRequestDto.getContact());
        }

        return user.getId();
    }

    @Override
    @Transactional
    public UserInfoResponseDto userInfo (Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_USER_ID));

        UserRoleEnum role = user.getRole() == UserRoleEnum.ADMIN ? user.getRole() : null;

        // 정보 조회 및 반환
        return UserInfoResponseDto.builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .contact(user.getContact())
                .role(role)
                .build();
    }

    @Override
    @Transactional
    public String getRefreshToken (String refreshToken) {

        Claims info = jwtUtil.getUserInfoFromToken(refreshToken);

        Long userId = Long.valueOf(info.getId());

        // Redis에서 RefreshToken 문자열 가져오기
        String redisTokenStr = (String) redisUtil.get("refreshToken:" + userId);
        if (redisTokenStr == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Refresh token not found in Redis");
        }

        User user = userRepository.findById(Long.valueOf(info.getId()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return jwtUtil.createToken(user.getEmail(), user.getRole(), user.getId(), jwtUtil.ACCESS_TOKEN_EXPIRATION_TIME);
    }

    private void validateUser (String email, String nickName) {

        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorResponseCode.DUPLICATE_EMAIL);
        }

        if (userRepository.existsByNickName(nickName)) {
            throw new CustomException(ErrorResponseCode.DUPLICATE_NICKNAME);
        }
    }

    @Override
    @Transactional
    public Long confirmUserInfoApi (String userName) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_USER_NAME));

        return user.getId();
    }

    // DeliveryAddress
    @Override
    @Transactional
    public Long createDeliveryAddress (DeliveryAddressRequestDto deliveryAddressRequestDto, Long userId) {
        // 회원 인증

        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .userId(userId)
                .zipCode(deliveryAddressRequestDto.getZipcode())
                .roadAddress(deliveryAddressRequestDto.getRoadAddress())
                .build();
        deliveryAddressRepository.save(deliveryAddress);

        return deliveryAddress.getId();
    }

    @Override
    @Transactional
    public List<DeliveryAddressDto> getDeliveryAddress (Long userId) {

        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByUserId(userId);

        List<DeliveryAddressDto> deliveryAddressDtos = new ArrayList<>();

        for (DeliveryAddress deliveryAddress : deliveryAddresses) {
            DeliveryAddressDto deliveryAddressDto = new DeliveryAddressDto(
                    deliveryAddress.getId(),
                    deliveryAddress.getZipCode(),
                    deliveryAddress.getRoadAddress()
            );
            deliveryAddressDtos.add(deliveryAddressDto);
        }

        return deliveryAddressDtos;
    }

    // Favorite
    @Override
    @Transactional
    public FavoriteResponseDto updateFavorite (Long itemId, FavoriteRequestDto favoriteRequestDto, Long userId) {

        // 회원 인증

        List<LikeItemListResponseDto> itemInfos = moduleApiUtil.getItemInfoApi(itemId, userId);
        LikeItemListResponseDto itemInfo = itemInfos.stream()
                .filter(info -> info.getItemInfo().getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

        Favorite existingFavorite = favoriteRepository.findByItemId(itemId);

        if (existingFavorite != null) {  // DB에 itemId가 존재하는 경우
            if (favoriteRequestDto.isSelected()) {
                throw new CustomException(ErrorResponseCode.DUPLICATE_TRUE);
            } else {
                favoriteRepository.delete(existingFavorite);
                return FavoriteResponseDto.from(buildFavorite(itemInfo.getItemInfo().getId(), favoriteRequestDto.isSelected(), userId));
            }
        } else {  // DB에 itemId가 존재하지 않는 경우
            if (favoriteRequestDto.isSelected()) {
                Favorite newFavorite = buildFavorite(itemInfo.getItemInfo().getId(), favoriteRequestDto.isSelected(), userId);
                favoriteRepository.save(newFavorite);
                return FavoriteResponseDto.from(newFavorite);
            } else {
                throw new CustomException(ErrorResponseCode.DUPLICATE_FALSE);
            }
        }
    }

    @Override
    @Transactional
    public PageInfoResponseDto <LikeItemListResponseDto> favoriteList (int pageNo, int size, Long itemId, Long userId) {

        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);
        int totalCount = favoriteRepository.countByUserId(userId);

        List<LikeItemListResponseDto> itemInfo = moduleApiUtil.getItemInfoApi(itemId, userId);

        return PageInfoResponseDto.of(pageRequest, itemInfo, totalCount);

    }

    private Favorite buildFavorite(Long itemId, boolean isSelected, Long userId) {
        return Favorite.builder()
                .itemId(itemId)
                .isSelected(isSelected)
                .userId(userId)
                .build();
    }
}
