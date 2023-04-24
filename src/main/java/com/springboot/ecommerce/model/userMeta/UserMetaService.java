package com.springboot.ecommerce.model.userMeta;

import com.springboot.ecommerce.user.User;

public interface UserMetaService {

    UserMeta getUserMeta(Long userMateId);

    UserMeta getUserMetaByCurrentUser(Long currentUserId);
}
