package com.project.cursos.cursosmicroservice.Controllers;

import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.project.commons.commonsmicroservice.controllers.CommonController;
import com.project.commonsexamenes.models.entity.Examen;
import com.project.cursos.cursosmicroservice.Service.CursoService;
import com.project.cursos.cursosmicroservice.models.entity.Curso;
import com.project.cursos.cursosmicroservice.models.entity.CursoAlumno;
import com.project.cursos.cursosmicroservice.models.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {
    @Value("${config.balanceador.test}")
    private String balanceadorTest;

    //eliminar alumno del curso cuando se elimina el registro del alumno
    @DeleteMapping("eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id){
        service.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }

   //Se agrega override porque se esta sobreescribiendo el metodo, ya esta en commonController
    @GetMapping
    @Override
    public ResponseEntity<?> listar() {

        List<Curso> cursos= ((List<Curso>) service.findAll()).stream().map(c->{
            c.getCursoAlumnos().forEach(ca->{
                Alumno alumno=new Alumno();
                alumno.setId(ca.getAlumnoId());
                c.addAlumnos(alumno);
            });
            return  c;
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(cursos);
    }
    //Se agrega override porque se esta sobreescribiendo el metodo, ya esta en commonController

    @GetMapping({"/{id}"})
    @Override
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        if (!o.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso curso=o.get();
        //Preguntar si el curso tiene alumnos
        if(curso.getCursoAlumnos().isEmpty()==false){
            List<Long> ids=curso.getCursoAlumnos().stream().map(ca->
                 ca.getAlumnoId()).collect(Collectors.toList());
            List<Alumno> alumnos= (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
            curso.setAlumnos(alumnos);
        }
        return ResponseEntity.ok().body(curso);
    }
    //Se agrega override porque se esta sobreescribiendo el metodo, ya esta en commonController
    @GetMapping({"/pagina"})
    @Override
    public ResponseEntity<?> listar(Pageable pageable) {
        Page<Curso> cursos=service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(ca->{
                Alumno alumno=new Alumno();
                alumno.setId(ca.getAlumnoId());
                curso.addAlumnos(alumno);
            });
            return curso;
        });
        return ResponseEntity.ok().body(cursos);
    }

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {
        Map<String,Object> response=new HashMap<String,Object>();
        response.put("balanceador",balanceadorTest);
        response.put("cursos",service.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return  this.validar(result);
        }
        //optional porque se debe buscar en base de datos
        Optional<Curso> o= this.service.findById(id);
        if(!o.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso=o.get();
        dbCurso.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));

    }
    //Asignar Alumno
    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id){
        //optional porque se debe buscar en base de datos
        Optional<Curso> o= this.service.findById(id);
        if(!o.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso=o.get();

        alumnos.forEach(a->{
            //Obteniendo el ide para pasarlo a la tabla cursoalumno, para la relacion de bases de datos diferentes
            CursoAlumno cursoAlumno=new CursoAlumno();
            cursoAlumno.setAlumnoId(a.getId());
            //Asignando el curso
            cursoAlumno.setCurso(dbCurso);
            dbCurso.addCursoAlumnos(cursoAlumno);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    //Eliminar Alumno
    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id){
        //optional porque se debe buscar en base de datos
        Optional<Curso> o= this.service.findById(id);
        if(!o.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso=o.get();
        CursoAlumno cursoAlumno=new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());


        dbCurso.removeCursoAlumnos(cursoAlumno);
        System.out.println(dbCurso.getAlumnos().size());
        System.out.println(dbCurso.getAlumnos());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }
    //Buscar a que curso pertenece el alumno
    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId( @PathVariable Long id){
        Curso curso=service.findCursoByAlumnoId(id);
        //Buscar examenes que han sido respondidos en el curso
        if(curso!=null){
            List<Long> examenesIds= (List<Long>) service.obtenerExamenesIdsConRespuestasAlumnos(id);
            if(examenesIds !=null && examenesIds.size()>0) {
                //Asignar a una nueva lista los examenes que han sido respondidos
                List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                    if (examenesIds.contains(examen.getId())) {
                        examen.setRespondido(true);
                    }
                    return examen;
                }).collect(Collectors.toList());
                //Se actualiza la lista original con los examenes que han sido respondidos o no por el alumno
                curso.setExamenes(examenes);
            }

        }
        return ResponseEntity.ok(curso);
    }

    //Asignar Examen
    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id){
        //optional porque se debe buscar en base de datos
        Optional<Curso> o= this.service.findById(id);
        if(!o.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso=o.get();

        examenes.forEach(e->{
            dbCurso.addExamen(e);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    //Eliminar examen
    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id){
        //optional porque se debe buscar en base de datos
        Optional<Curso> o= this.service.findById(id);
        if(!o.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso=o.get();

        dbCurso.removeExamen(examen);
        System.out.println(dbCurso.getAlumnos().size());
        System.out.println(dbCurso.getAlumnos());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

}
