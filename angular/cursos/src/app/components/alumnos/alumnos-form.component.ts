import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2';
import { CommonFormComponent } from '../common-form.component';
@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css'],
})
export class AlumnosFormComponent
  extends CommonFormComponent<Alumno, AlumnoService>
  implements OnInit {

  private fotoSeleccionada: File;

  //En el constructor se importa alumno service para acceder al metodo de crear. Router es para redirigir a una pagina, route se obtiene el id para editar, eliminar
  constructor(service: AlumnoService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Alumnos';
    this.model = new Alumno();
    this.redirect = '/alumnos';
    this.nombreModel = Alumno.name;
  }

  //Subir foto Alumno
  public seleccionarFoto(event): void {
    //Indice 0 porque solo va a subir un solo archivo(Foto)
    this.fotoSeleccionada = event.target.files[0];
    console.info(this.fotoSeleccionada);

    //verificar que sea formato de imagen jpg,png..
    if(this.fotoSeleccionada.type.indexOf('image')<0){
      this.fotoSeleccionada=null;
      Swal.fire('Error al seleccionar foto:', 'El archivo debe ser de tipo imagen','error');
    }
  }
  //crear con foto
  public override crear(): void {
    if (!this.fotoSeleccionada) {
      super.crear();
    } else {
      this.service.CrearConFoto(this.model, this.fotoSeleccionada).subscribe(alumno => {
        console.log(alumno);
        Swal.fire('Nuevo:', `${this.nombreModel} ${alumno.nombre} creado con exito`, 'success');
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

  //editar con foto
  public override editar(): void {

    this.service.editarConFoto(this.model, this.fotoSeleccionada).subscribe(alumno => {
      console.log(alumno);
      Swal.fire('Modificado:', `${this.nombreModel} ${alumno.nombre} actualizado con exito`, 'success');
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
