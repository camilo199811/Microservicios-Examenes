package com.project.commons.commonsmicroservice.controllers;


import com.project.commons.commonsmicroservice.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//E entity, S service
//@CrossOrigin({"http://localhost:4200"})
public class CommonController<E,S extends CommonService<E>> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected S service;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok().body(service.findAll());
    }
    //Para hacer paginacion
    @GetMapping("/pagina")
    public ResponseEntity<?> listar(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id){
        Optional<E> o=service.findById(id);
        if(!o.isPresent()){
          return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(o.get());
    }
//BindignResulyt, comprobar si lo que se esta creando esta validado
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result){
        //Validar antes de guardar
        if(result.hasErrors()){
            return this.validar(result);
        }
        E entityDb= service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    //Posibles resultados de validacion
    protected ResponseEntity<?> validar(BindingResult result){
        Map<String,Object> errores=new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(),"El campo "+err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
