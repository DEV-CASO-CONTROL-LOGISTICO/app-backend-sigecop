/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.service;

import ccl.securitybackend.dto.PaginaResponse;
import ccl.securitybackend.dto.UsuarioRequest;
import ccl.securitybackend.dto.UsuarioResponse;
import ccl.securitybackend.model.Usuario;
import ccl.securitybackend.repository.PaginaRepository;
import ccl.securitybackend.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Moises_F16.7.24
 */
@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PaginaRepository paginaRepository;

    public UsuarioResponse searchForCredentials(UsuarioRequest request) {
        return UsuarioResponse.fromEntity(
                usuarioRepository.getUserForCredentials(
                        request.getCuenta(),
                        request.getClave()));
    }

    public UsuarioResponse searchForId(Integer usuarioId) {
        Optional<Usuario> result = usuarioRepository.findById(usuarioId);
        if (!result.isPresent()) {
            return null;
        }
        UsuarioResponse usuarioResponse = UsuarioResponse.fromEntity(result.get());
        usuarioResponse.setPaginas(PaginaResponse.fromEntities(paginaRepository.listForRol(usuarioResponse.getRol().getId())));
        return usuarioResponse;
    }
}
