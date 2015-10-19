/**
 * 
 */
package org.yugzan.xls.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value  = HttpStatus.BAD_REQUEST)
public class ServiceException extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public ServiceException(String msg) {
    super(msg);
  }
  
  public ServiceException(Throwable cause) {
    super(cause);
  }
  
  public ServiceException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
