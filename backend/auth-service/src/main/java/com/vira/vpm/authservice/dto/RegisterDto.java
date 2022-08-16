
package com.vira.vpm.authservice.dto;

import lombok.Data;

@Data
public class RegisterDto {

  private String email;
  private String password;
  private String fullname;
  private String image;
}
