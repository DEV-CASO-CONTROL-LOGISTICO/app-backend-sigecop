package ccl.securitybackend.utils.generic;

import ccl.securitybackend.utils.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class ControllerBase<T,F> {

    private final Logger loggerBase = LoggerFactory.getLogger(this.getClass());

    protected final ServiceBase<T,F> service;

    public ControllerBase(ServiceBase<T,F> service) {
        this.service = service;
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody F request) {
        try {
            return ResponseEntity.ok(service.list(request));
        } catch (Exception e) {
            loggerBase.error("Error list "+this.getClass().getName(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody F request) {
        try {
            ObjectResponse<T> resultOperation= service.find(request);
            return resultOperation.getSuccess()?
                    ResponseEntity.ok(resultOperation.getObject()):
                    ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            loggerBase.error("Error find "+this.getClass().getName(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody F request) {
        try {
            System.out.println("-----------------");
            System.out.println(request);
            ObjectResponse<T> resultOperation=service.save(request);
            return resultOperation.getSuccess()?
                    ResponseEntity.ok(resultOperation.getObject()):
                    ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            loggerBase.error("Error save "+this.getClass().getName(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody F request) {
        try {
            ObjectResponse<T> resultOperation=service.delete(request);
            return resultOperation.getSuccess()?
                    ResponseEntity.ok(resultOperation.getObject()):
                    ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            loggerBase.error("Error delete "+this.getClass().getName(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
