package in.ashokit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegAppExceptionHandler 
{
	@ExceptionHandler(RegAppException.class)
     public ResponseEntity<AppError> handleRegAppException(RegAppException regAppExc){
    	 AppError error=new AppError();
    	 error.setErrorCode("REGAPP101");
    	 error.setErrorMsg(regAppExc.getMessage());
    	 return new ResponseEntity<AppError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
     }

}
