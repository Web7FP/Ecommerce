package com.springboot.ecommerce.model.userMeta;

public interface UserMetaService {

    UserMeta getUserMeta(Long userMateId);

    UserMeta getUserMetaByCurrentUser(Long currentUserId);
}
