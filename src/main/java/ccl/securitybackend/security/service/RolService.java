/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.service;

import ccl.securitybackend.security.dto.RolResponse;
import ccl.securitybackend.security.repository.RolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Moises_F16.7.24
 */
@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public List<RolResponse> list() {
        return RolResponse.fromEntities(rolRepository.findAll());
    }
}
