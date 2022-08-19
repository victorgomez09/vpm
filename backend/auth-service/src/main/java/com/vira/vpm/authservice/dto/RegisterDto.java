
package com.vira.vpm.authservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDto {

  private String email;
  private String password;
  private String fullname;
  private String image;

  public RegisterDto(String email) {
    this.email = email;
  }
}
