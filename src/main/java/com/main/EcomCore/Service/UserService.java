package com.main.EcomCore.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.main.EcomCore.Model.Address;
import com.main.EcomCore.Model.User;
import com.main.EcomCore.Repostitory.UserRepository;
import com.main.EcomCore.app.dto.AddressDto;
import com.main.EcomCore.app.dto.UserRequest;
import com.main.EcomCore.app.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public List<UserResponse> fetchAllUser () {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void AddUser (UserRequest userRequest) {

        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    private void updateUserFromRequest (User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }


    }


    public Optional<UserResponse> fetchUser (Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);

    }

    public boolean updateUser (Long id, UserRequest updateuser) {
        return userRepository
                .findById(id)
                .map(
                        existinguser -> {
                            updateUserFromRequest(existinguser, updateuser);
                            userRepository.save(existinguser);
                            return true;
                        })
                .orElse(false);
    }

    private UserResponse mapToUserResponse (User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(String.valueOf(user.getFirstName()));
        response.setLastName(String.valueOf(user.getLastName()));
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDto);
        }
        return response;
    }
}
