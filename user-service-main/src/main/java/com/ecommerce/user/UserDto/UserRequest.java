package com.ecommerce.user.UserDto;

import com.ecommerce.user.Model.Address;
import lombok.Data;


@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;
}
