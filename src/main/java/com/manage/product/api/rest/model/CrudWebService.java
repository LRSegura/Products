package com.manage.product.api.rest.model;


import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.api.rest.model.response.JsonDataResponse;
import com.manage.product.api.rest.model.response.JsonResponse;
import com.manage.product.api.rest.model.response.JsonSimpleResponse;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public abstract class CrudWebService<T extends JsonData> {

    @GetMapping()
    public ResponseEntity<JsonResponse> get() {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            List<? extends JsonData> entities = getCrudRestOperations().restGet();
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, getErrorMessageGet());
    }

    @PostMapping()
    public ResponseEntity<JsonResponse> save(@RequestBody T json) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restSave(json);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        };
        return executeTryCatch(supplierTry, getErrorMessagePost());
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> update(@RequestBody T json) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restUpdate(json);
            return ResponseEntity.accepted().build();
        };
        return executeTryCatch(supplierTry, getErrorMessagePut());
    }

    @DeleteMapping()
    public ResponseEntity<JsonResponse> delete(@QueryParam(value = "id") Long id) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restDelete(id);
            return ResponseEntity.noContent().build();
        };
        return executeTryCatch(supplierTry, getErrorMessageDelete());
    }

    protected ResponseEntity<JsonResponse> executeTryCatch(Supplier<ResponseEntity<JsonResponse>> supplier, String exceptionMessage) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            String errorMessage = exception.getClass().getName() + "::" + exception.getMessage();
            log.error(errorMessage);
            String responseMessage = exception instanceof ApplicationBusinessException ? exception.getMessage() : exceptionMessage;
            JsonSimpleResponse response = new JsonSimpleResponse(responseMessage);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    abstract protected CrudRestOperations<T> getCrudRestOperations();

    protected String getErrorMessageGet() {
        return "Error get entities";
    }

    protected String getErrorMessagePost() {
        return "Error save entity";
    }

    protected String getErrorMessagePut() {
        return "Error update entity";
    }

    protected String getErrorMessageDelete() {
        return "Error delete entity";
    }

}
