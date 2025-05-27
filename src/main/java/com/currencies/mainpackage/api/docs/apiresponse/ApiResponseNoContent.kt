package com.currencies.mainpackage.api.docs.apiresponse

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@ApiResponse(responseCode = "204")
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiResponseNoContent
