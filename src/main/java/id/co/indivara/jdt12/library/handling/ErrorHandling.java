package id.co.indivara.jdt12.library.handling;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
@RestControllerAdvice
public class ErrorHandling {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handlerResponseStatus(ResponseStatusException r){
        return ResponseEntity.status(r.getStatus()).body(r.getReason());
    }
}
