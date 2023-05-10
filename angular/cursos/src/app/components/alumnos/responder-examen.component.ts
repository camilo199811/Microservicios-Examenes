import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { Curso } from 'src/app/models/curso';
import { Examen } from 'src/app/models/examen';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import { ViewChild } from '@angular/core'
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { ResponderExamenModalComponent } from './responder-examen-modal.component';
import { RespuestaService } from 'src/app/services/respuesta.service';
import { Respuesta } from 'src/app/models/respuesta';
import Swal from 'sweetalert2';
import { VerExamenModalComponent } from './ver-examen-modal.component';
@Component({
  selector: 'app-responder-examen',
  templateUrl: './responder-examen.component.html',
  styleUrls: ['./responder-examen.component.css']
})
export class ResponderExamenComponent implements OnInit {

  alumno: Alumno;
  curso: Curso;
  examenes: Examen[] = [];
  pageSizeOptions=['3','5','10','15'];
  mostrarColumnasAlumnos=['id','nombre','asignaturas','preguntas','responder','ver'];
  dataSource:MatTableDataSource<Examen>;
  @ViewChild(MatPaginator,{static:true}) paginator:MatPaginator;
  constructor(private route: ActivatedRoute,
    private alumnoSercice: AlumnoService,
    private cursoService: CursoService,
    private respuestaService:RespuestaService,
    public dialog:MatDialog
    ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = +params.get('id');
      this.alumnoSercice.ver(id).subscribe(alumno => {
        this.alumno = alumno;
        this.cursoService.obtenerCursoPorAlumnoId(this.alumno).subscribe(
          curso=>{
            this.curso=curso;
            //verificar si el alumno pertenece a un curso si no arreglo vacio
            this.examenes= (curso && curso.examenes)? curso.examenes:[];
            this.dataSource=new MatTableDataSource<Examen>(this.examenes);
            this.dataSource.paginator=this.paginator;
            this.paginator._intl.itemsPerPageLabel='Registros por página:';
          }
        );
      });
    })

  }

  responderExamen(examen:Examen):void{
   const modalRef= this.dialog.open(ResponderExamenModalComponent,{width:'750px',data:{curso:this.curso,alumno:this.alumno,examen:examen}});
   //Enviar las respuestas obtenidas al backend
   modalRef.afterClosed().subscribe((respuestasMap:Map<Number,Respuesta> )=>{
    console.log('modal responder examen ha sido enviado y cerrado'),
    console.log(respuestasMap);
    if(respuestasMap){
      //guardar respuestas en el backend
      const respuestas:Respuesta[]=Array.from(respuestasMap.values());
      this.respuestaService.crear(respuestas).subscribe(rs=>{
        examen.respondido=true;
        Swal.fire(
          'Enviadas',
          'Preguntas enviadas con exito',
          'success'
        );
        console.log(rs);
      });
    }
   });
  }

  verExamen(examen:Examen):void{
    this.respuestaService.obtenerRespuestasPorAlumnoPorExamen(this.alumno,examen)
    .subscribe(rs=>{
      const modalRef=this.dialog.open(VerExamenModalComponent,{
        width:'750px',
        data:{
          curso:this.curso,examen:examen,respuestas:rs
        }
      });
      modalRef.afterClosed().subscribe(()=>{
        console.log('Modal cerrado');
      })
    });
  }

}
