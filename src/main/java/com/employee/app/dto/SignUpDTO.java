package com.employee.app.dto;

import com.employee.app.annotation.NullOrNotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpDTO {

    @NullOrNotBlank(min=1, max=250, isMandatory = "yes", message = "Please enter First name")
    private String firstName;

    @NullOrNotBlank(min = 1, max = 250, isMandatory = "yes", message = "Please enter Last name")
    private String lastName;

    @NullOrNotBlank(min=5, max = 250, isEmail = "yes", isMandatory = "yes", message = "Please provide email")
    private String email;

    @NullOrNotBlank(min=1, max=250, isMandatory = "no", message = "Address is required")
    private String address;

    @NullOrNotBlank(min=1, max=250, message = "Company name is required")
    private String companyName;


}
