package com.noahhendrickson.persistence.sync;

import com.noahhendrickson.kernel.sync.DiscordEntitySnapshot;
import com.noahhendrickson.persistence.entity.UserEntity;
import com.noahhendrickson.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSyncService {

    private final UserRepository userRepository;

    public UserSyncService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity sync(DiscordEntitySnapshot snapshot) {
        return userRepository.findById(snapshot.userId())
                .map(user -> {
                    user.setUsername(snapshot.username());
                    user.setGlobalName(snapshot.globalName());
                    user.setAvatarHash(snapshot.avatarHash());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    UserEntity user = new UserEntity();
                    user.setId(snapshot.userId());
                    user.setUsername(snapshot.username());
                    user.setGlobalName(snapshot.globalName());
                    user.setAvatarHash(snapshot.avatarHash());
                    return userRepository.save(user);
                });
    }
}
