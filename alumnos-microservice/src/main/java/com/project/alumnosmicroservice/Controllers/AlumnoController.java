package com.project.alumnosmicroservice.Controllers;


import com.project.alumnosmicroservice.services.AlumnoService;
import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.project.commons.commonsmicroservice.controllers.CommonController;
import com.project.commons.commonsmicroservice.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {


    //Listar Alumnos
    @GetMapping("/alumnos-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.findAllById(ids));
    }

    //Metodo para ver foto
    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id){
        Optional<Alumno> o=service.findById(id);
        if(!o.isPresent() || o.get().getFoto() == null){
            return  ResponseEntity.notFound().build();
        }
        Resource imagen= new ByteArrayResource(o.get().getFoto());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){

        if(result.hasErrors()){
            return  this.validar(result);
        }

        Optional<Alumno> o=service.findById(id);
        if(!o.isPresent()){
            return  ResponseEntity.notFound().build();
        }
        Alumno alumnoDb=o.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));

    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term){
        return ResponseEntity.ok(service.finByNombreOrApellido(term));
    }
    //Subir foto
    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
        if(!archivo.isEmpty()){
            alumno.setFoto(archivo.getBytes());
        }
        return super.crear(alumno, result);
    }

    //Editar foto

    @PutMapping("/editar-con-foto/{id}")
    public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id,@RequestParam MultipartFile archivo) throws IOException {

        if(result.hasErrors()){
            return  this.validar(result);
        }

        Optional<Alumno> o=service.findById(id);
        if(!o.isPresent()){
            return  ResponseEntity.notFound().build();
        }
        Alumno alumnoDb=o.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());
        if(!archivo.isEmpty()){
            alumnoDb.setFoto(archivo.getBytes());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));

    }
}
