package com.example.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindException;

@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {
   public String message; 
   public void Ex(BindException ex)  {
    String mess = ex.getAllErrors().get(0).getDefaultMessage();
    this.setMessage(mess);
    }
}
