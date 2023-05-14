package com.springboot.ecommerce.model.userMeta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMetaServiceImpl implements UserMetaService{


    private final UserMetaRepository userMetaRepository;

    @Override
    public UserMeta getUserMetaByCurrentUser(Long currentUserId) {
        return userMetaRepository.getUserMetaByUser_Id(currentUserId);
    }


    public void saveUserMeta(UserMeta userMeta){
        userMetaRepository.save(userMeta);
    }

    @Override
    public UserMeta getUserMeta(Long userMateId) {
        Optional<UserMeta> userMetaOptional = userMetaRepository.findById(userMateId);
        if (userMetaOptional.isPresent()){
            return userMetaOptional.get();
        } else {
            throw new IllegalStateException("User meta not found for : " + userMateId);
        }
    }
}
