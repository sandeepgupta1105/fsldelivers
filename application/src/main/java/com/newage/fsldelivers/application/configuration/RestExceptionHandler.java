package com.newage.fsldelivers.application.configuration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.newage.fsldelivers.application.dto.response.ResponseDTO;
import com.newage.fsldelivers.application.dto.response.ResponseError;
import com.newage.fsldelivers.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ResponseError error = new ResponseError(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), errors);
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.NOT_ACCEPTABLE.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.info(ex.getClass().getName());
        final String errorMessage = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String errorMessage = ex.getRequestPartName() + " part is missing";
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        final String errorMessage = ex.getParameterName() + " parameter is missing";
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info(ex.getClass().getName());
        final String errorMessage = ex.getMessage()+ " body is missing";
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        final String errorMessage = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ BadCredentialsException.class, AccessDeniedException.class})
    public ResponseEntity<Object> handleBadCredentialsException(final BadCredentialsException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        final String errorMessage = ex.getMessage()!=null?ex.getMessage():HttpStatus.UNAUTHORIZED.getReasonPhrase();
        final ResponseError error = new ResponseError(HttpStatus.UNAUTHORIZED.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        log.info(ex.getClass().getName());

        final List<String> errorMessage = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage);
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ServiceException.class })
    public ResponseEntity<Object> handleServiceException(final ServiceException ex, final WebRequest request) {
        log.info(ex.getClass().getName());

        final ResponseError error = new ResponseError(ex.getErrorCode(), List.of(ex.getErrorMessage()));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        final String errorMessage = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ResponseError error = new ResponseError(HttpStatus.NOT_FOUND.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.NOT_FOUND.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.info(ex.getClass().getName());
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        final ResponseError error = new ResponseError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), List.of(builder.toString()));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        final ResponseError error = new ResponseError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), List.of(builder.toString()));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request, HttpServletResponse response) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        final ResponseError error = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), List.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}