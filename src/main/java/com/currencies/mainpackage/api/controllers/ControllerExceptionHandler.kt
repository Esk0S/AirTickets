package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.dto.response.ErrorResponse
import jakarta.persistence.EntityNotFoundException
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

//@RestControllerAdvice
//public class ExceptionController {
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(EntityNotFoundException.class)
//    private ErrorResponse notFound(EntityNotFoundException ex) {
//        return new ErrorResponse(ex.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(RuntimeException.class)
//    private ErrorResponse error(RuntimeException ex) {
//        return new ErrorResponse(ex.getMessage());
//    }
//}


@ControllerAdvice
class ControllerExceptionHandler: Logging {

    @ExceptionHandler(Throwable::class)
    fun handleException(e: Throwable):ResponseEntity<ErrorResponse> {
        val httpStatus: HttpStatus
        val message: String?

        when (e) {
            is EntityNotFoundException -> {
                httpStatus = HttpStatus.NOT_FOUND
                message = e.message
            }
            else -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                message = null
            }
        }

        val errorResponse = ErrorResponse(
                status = httpStatus.value(),
                error = httpStatus.reasonPhrase,
                message = message
        )

        message?.let {
            logger.error(it, e)
        } ?: logger.error(e.toString(), e)

        return ResponseEntity(errorResponse, httpStatus)
    }

}