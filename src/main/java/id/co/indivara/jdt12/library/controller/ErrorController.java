//package id.co.indivara.jdt12.library.controller;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@RestControllerAdvice
//public class ErrorController extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<String> handlerResponseStatus(ResponseStatusException r){
//        return ResponseEntity.status(r.getStatus()).body(r.getReason());
//    }
//}
