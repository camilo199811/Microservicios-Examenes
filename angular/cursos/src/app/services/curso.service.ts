import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CommonService } from './common.service';
import { Alumno } from '../models/alumno';
import { Curso } from '../models/curso';
import { BASE_ENDPOINT } from '../config/app';
import { Observable } from 'rxjs';
import { Examen } from '../models/examen';

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {
  protected override baseEndpoint=BASE_ENDPOINT + '/cursos';
  constructor(http: HttpClient) {
    super(http);
   }

   //Asignar alumnos al curso
   asignarAlumnos(curso:Curso,alumnos:Alumno[]):Observable<Curso>{
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/asignar-alumnos`,alumnos, {headers:this.cabeceras})
   }

   //Elimianr alumno del curso
   eliminarAlumno(curso:Curso,alumno:Alumno):Observable<Curso>{
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/eliminar-alumno`,
    alumno,
    {headers:this.cabeceras});
   }

   //Asignar examenes al curso
   asignarExamenes(curso:Curso,examenes:Examen[]):Observable<Curso>{
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/asignar-examenes`,
    examenes,
    {headers:this.cabeceras});
   }

   //eliminar examen del curso
   eliminarExamen(curso:Curso,examen:Examen):Observable<Curso>{
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/eliminar-examen`,
    examen,
    {headers:this.cabeceras});
   }

   //Buscar a que curso pertenece el alumno para responder examen
   obtenerCursoPorAlumnoId(alumno:Alumno):Observable<Curso>{
    return this.http.get<Curso>(`${this.baseEndpoint}/alumno/${alumno.id}`);
   }
}
