package ccl.securitybackend.service;

import ccl.securitybackend.dto.RolResponse;
import ccl.securitybackend.dto.TipoDocumentoResponse;
import ccl.securitybackend.repository.RolRepository;
import ccl.securitybackend.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoService {

    @Autowired
    TipoDocumentoRepository tipoDocumentoRepository;

    public List<TipoDocumentoResponse> list() {
        return TipoDocumentoResponse.fromEntities(tipoDocumentoRepository.findAll());
    }
}
