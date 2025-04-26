/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsw.sigconbackend.service;

import dsw.sigconbackend.dto.UsuarioLoginRequest;
import dsw.sigconbackend.dto.UsuarioResponse;
import dsw.sigconbackend.repository.UsuarioRepository;
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

    public UsuarioResponse searchForCredentials(UsuarioLoginRequest request) {
        return UsuarioResponse.fromEntity(
                usuarioRepository.getUserForCredentials(
                        request.getCuenta(),
                        request.getClave()));
    }
}
