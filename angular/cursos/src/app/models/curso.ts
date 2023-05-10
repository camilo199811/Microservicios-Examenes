import { Alumno } from "./alumno";
import { Examen } from "./examen";
import { Generic } from "./generic";
export class Curso implements Generic {
  id:number;
  nombre:string;
  createAt:string;
  //Se inicializa el arreglo
  alumnos:Alumno[]=[];
  examenes:Examen[]=[];
}
