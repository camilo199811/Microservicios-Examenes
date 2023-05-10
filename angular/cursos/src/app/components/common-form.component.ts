import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2'
import { CommonService } from '../services/common.service';
import { Generic } from '../models/generic';
import { Directive } from '@angular/core';

@Directive()
export abstract class CommonFormComponent<E extends Generic, S extends CommonService<E>> implements OnInit {
  titulo: string
  model: E;

  //capturar los errores al crear ususarios
  error: any;

  protected redirect: string;
  protected nombreModel: string;

  //En el constructor se importa alumno service para acceder al metodo de crear. Router es para redirigir a una pagina, route se obtiene el id para editar, eliminar
  constructor(protected service: S, protected router: Router, protected route: ActivatedRoute) {

  }

  ngOnInit() {
    //editar alumno
    this.route.paramMap.subscribe(params => {
      //+ es para convertir de string a number
      const id: number = +params.get('id');
      if (id) {
        this.service.ver(id).subscribe(m =>
         {
          this.model = m;
          this.titulo='Editar ' + this.nombreModel;}
        );
      }
    })
  }

  public crear(): void {
    this.service.crear(this.model).subscribe(m => {
      console.log(m);
      Swal.fire('Nuevo:', `${this.nombreModel} ${m.nombre} creado con exito`, 'success');
      this.router.navigate([this.redirect]);
    },
      //capturar los errores
      err => {
        if (err.status === 400) {
          this.error = err.error;
          console.log(this.error);
        }
      }

    );
  }

  public editar(): void {
    this.service.editar(this.model).subscribe(m => {
      console.log(m);
      Swal.fire('Modificado:', `${this.nombreModel} ${m.nombre} actualizado con exito`, 'success');
      this.router.navigate([this.redirect]);
    },
      //capturar los errores
      err => {
        if (err.status === 400) {
          this.error = err.error;
          console.log(this.error);
        }
      }

    );
  }

}
