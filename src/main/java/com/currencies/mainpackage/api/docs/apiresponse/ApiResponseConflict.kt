package com.currencies.mainpackage.api.docs.apiresponse

import io.swagger.v3.oas.annotations.responses.ApiResponse

@ApiResponse(responseCode = "409")
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiResponseConflict
