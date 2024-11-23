package com.ordermgmt.user_service.service;

import com.ordermgmt.user_service.model.Privilege;
import com.ordermgmt.user_service.model.Profile;
import com.ordermgmt.user_service.model.ProfilePrivilege;
import com.ordermgmt.user_service.model.User;
import com.ordermgmt.user_service.repository.PrivilegeRepository;
import com.ordermgmt.user_service.repository.ProfilePrivilegeRepository;
import com.ordermgmt.user_service.repository.ProfileRepository;
import com.ordermgmt.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private ProfilePrivilegeRepository profilePrivilegeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getProfileById(Long profileId) {
        return profileRepository.findById(profileId);
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }

    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    public ProfilePrivilege assignPrivilegeToProfile(Long profileId, Long privId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        Privilege privilege = privilegeRepository.findById(privId).orElseThrow();

        ProfilePrivilege profilePrivilege = new ProfilePrivilege();
        profilePrivilege.setProfile(profile);
        profilePrivilege.setPrivilege(privilege);

        return profilePrivilegeRepository.save(profilePrivilege);
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public List<ProfilePrivilege> getUserPrivileges(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> profilePrivilegeRepository.findAllByProfileId(value.getProfile().getProfileId())).orElse(null);
    }

    public User addUser(User user) {
        if (!profileRepository.existsById(user.getProfile().getProfileId())) {
            throw new RuntimeException("Invalid profile");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User userInp) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(userInp.getEmail());
        user.setUserFullName(userInp.getUserFullName());
        user.setUserShortName(userInp.getUserShortName());
        user.setPassword(userInp.getPassword());
        user.setLoginId(userInp.getEmail());
        user.setUserId(userInp.getUserId());
        user.setProfile(userInp.getProfile());
        user.setStatus(userInp.getStatus());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
