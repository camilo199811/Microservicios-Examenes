package com.microservicios.app.commons.commonsmicroservices.controllers;


import com.microservicios.app.commons.commonsmicroservices.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


public class CommonController<E,S extends CommonService<E>> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected S service;

    @GetMapping
    public ResponseEntity<?> listar(){
        return  ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id){
        Optional<E> o=service.findById(id);
        if(!o.isPresent()){
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(o.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody E entity){
        E alumnoDb=  service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar( @PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
