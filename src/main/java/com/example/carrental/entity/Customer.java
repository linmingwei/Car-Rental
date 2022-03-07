package com.example.carrental.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

  private Long id;
  private String username;
  private String password;
  private String mobile;
  private String email;
  private Boolean status;

}
