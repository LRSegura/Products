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

/**
 *
 * Abstract class for CRUD operations used by web services. This class should be extended to avoid boilerplate code.
 * All the CRUD methods return a Response based on {@link com.manage.product.api.rest.model.response.JsonResponse}
 * interface. See {@link com.manage.product.api.rest.model.JsonData}
 *
 * @author Luis
 */
@Slf4j
public abstract class CrudWebService<T extends JsonData> {


    @GetMapping()
    @CrossOrigin(origins = "*")
    public ResponseEntity<JsonResponse> get(@QueryParam(value = "id") Long id) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            JsonData entity = getCrudRestOperations().restGet(id);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entity);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, getErrorMessageGet());
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "*")
    public ResponseEntity<JsonResponse> getAll() {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            List<? extends JsonData> entities = getCrudRestOperations().restGetAll();
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, getErrorMessageGet());
    }

    @PostMapping()
    @CrossOrigin(origins = "*")
    public ResponseEntity<JsonResponse> save(@RequestBody T json) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restSave(json);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        };
        return executeTryCatch(supplierTry, getErrorMessagePost());
    }

    @PutMapping()
    @CrossOrigin(origins = "*")
    public ResponseEntity<JsonResponse> update(@RequestBody T json) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restUpdate(json);
            return ResponseEntity.accepted().build();
        };
        return executeTryCatch(supplierTry, getErrorMessagePut());
    }

    @DeleteMapping()
    @CrossOrigin(origins = "*")
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
    abstract protected String getNameEntityService();

    protected String getErrorMessageGet() {
        return "Error get entity " + getNameEntityService();
    }

    protected String getErrorMessagePost() {
        return "Error save entity "+ getNameEntityService();
    }

    protected String getErrorMessagePut() {
        return "Error update entity "+ getNameEntityService();
    }

    protected String getErrorMessageDelete() {
        return "Error delete entity "+ getNameEntityService();
    }

}
