import { Component,OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2'
import { Generic } from '../models/generic';
import { CommonService } from '../services/common.service';

import {  Directive } from '@angular/core';

@Directive()
export abstract class CommonListarComponent<E extends Generic,S extends CommonService<E>> implements OnInit{

  titulo: string;
  lista:E[];
  protected nombreModel:string;
  totalRegistros=0;
  paginaActual=0;
  totalPorPagina=4;
  pageSizeOptions:number[]=[3,5, 10, 25, 100];

  @ViewChild(MatPaginator) paginator:MatPaginator;

  constructor(protected service: S){}

  ngOnInit(){
    this.calcularRangos();
  }
  //Paginar para el resto de paginas
  paginar(event: PageEvent): void{
    this.paginaActual=event.pageIndex;
    this.totalPorPagina=event.pageSize;
    this.calcularRangos();

  }

  private calcularRangos(){

    this.service.listarPaginas(this.paginaActual.toString(),this.totalPorPagina.toString())
    .subscribe(p=>{
     //simular cuando la tabla esta vacia
     // this.alumnos=[]
     this.lista=p.content as E[];
     this.totalRegistros=p.totalElements as number;
     this.paginator._intl.itemsPerPageLabel='Registros por pagina';
  });
  }

  //eliminar alumno
  public eliminar(e:E):void{
    Swal.fire({
      title: 'Cuidado:',
      text: `¿Seguro que desea eliminar a ${e.nombre} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.eliminar(e.id).subscribe(()=>{
          //Se elimina de la lista y se muestra nuevamente
          //this.alumnos=this.alumnos.filter(a=>a!== alumno);
          this.calcularRangos();
          Swal.fire('Eliminado:',`${this.nombreModel} ${e.nombre} eliminado con exito`,'success');
        });
      }
    });


  }


}
