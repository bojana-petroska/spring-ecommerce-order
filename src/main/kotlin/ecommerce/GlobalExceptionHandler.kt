package ecommerce

import ecommerce.dto.errors.ErrorMessage
import ecommerce.dto.errors.ErrorResponse
import ecommerce.exception.AuthorizationException
import ecommerce.exception.InternalServerErrorException
import ecommerce.exception.NotFoundException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
        println("NotFoundException occurred: " + e.message)
        val error = ErrorMessage("resource", e.message ?: "Resource was not found.")
        val errorResponse = ErrorResponse(listOf(error))
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(InternalServerErrorException::class)
    fun handleInternalServerErrorException(e: InternalServerErrorException): ResponseEntity<ErrorResponse> {
        println("InternalServerErrorException occurred: " + e.message)
        val error = ErrorMessage("server", e.message ?: "Internal Server Error.")
        val errorResponse = ErrorResponse(listOf(error))
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @ExceptionHandler(DataAccessException::class)
    fun handleDataAccessException(e: Exception): ResponseEntity<ErrorResponse> {
        println("DataAccessException occurred: " + e.message)
        val error = ErrorMessage("database", e.message ?: "DataAccess Error.")
        val errorResponse = ErrorResponse(listOf(error))
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handlerIllegalStateException(e: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorMessage("state", e.message ?: "State not found.")
        val errorResponse = ErrorResponse(listOf(error))
        println("IllegalStateException occurred: " + e.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(e: Exception): ResponseEntity<ErrorResponse> {
        println("IllegalArgumentException occurred: " + e.message)
        val error = ErrorMessage("parameter", e.message ?: "An illegal argument was provided.")
        val errorResponse = ErrorResponse(listOf(error))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        println("MethodArgumentNotValidException occurred:" + e.message)
        val errors =
            e.bindingResult.fieldErrors.map { ErrorMessage(it.field, it.defaultMessage) }
        val errorResponse = ErrorResponse(errors)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(AuthorizationException::class)
    fun handleAuthorizationException(e: AuthorizationException): ResponseEntity<ErrorResponse> {
        println("AuthorizationException occurred:" + e.message)
        val error = ErrorMessage("authorization", e.message ?: "Authorization Error.")
        val errorResponse = ErrorResponse(listOf(error))
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }
}
