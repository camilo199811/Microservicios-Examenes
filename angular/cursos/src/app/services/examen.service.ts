import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Examen } from '../models/examen';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BASE_ENDPOINT } from '../config/app';
import { Observable } from 'rxjs';
import { Asignatura } from '../models/asignatura';
@Injectable({
  providedIn: 'root'
})
export class ExamenService extends CommonService<Examen>{

  protected override baseEndpoint=BASE_ENDPOINT + '/examenes';
  constructor(http: HttpClient) {
    super(http);
   }

   //obtener las asignaturas
   public findAllAsignatura():Observable<Asignatura[]>{
    return this.http.get<Asignatura[]>(`${this.baseEndpoint}/asignaturas`);
   }

   public flitrarPorNombre(nombre:string):Observable<Examen[]>{
    return this.http.get<Examen[]>(`${this.baseEndpoint}/filtrar/${nombre}`);
   }
}
