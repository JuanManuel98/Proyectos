import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:image_picker/image_picker.dart';

class AlumnoItem{
  String id;
  String idUsuario;
  String correo = '';
  String foto;
  String nombre = '';
  String grupo = '';
  int matricula;
  int telefono;
  int edad;
  String genero = '';
  String carrera = '';
  String semestre = '';
  int calificacion = 0;

  AlumnoItem (this.idUsuario, {this.nombre, this.correo, this.foto, this.grupo, this.matricula, this.telefono,
              this.edad, this.genero, this.carrera, this.semestre, this.calificacion});

  AlumnoItem.form(DocumentSnapshot snapshot):
      id = snapshot.documentID,
      idUsuario = snapshot['idUsuario'],
      nombre = snapshot['nombre'],
      correo = snapshot['correo'],
      foto = snapshot['foto'],
      grupo = snapshot['grupo'],
      matricula = snapshot['matricula'],
      telefono = snapshot['telefono'],
      edad = snapshot['edad'],
      genero = snapshot['genero'],
      carrera = snapshot['carrera'],
      semestre = snapshot['semestre'],
      calificacion = snapshot['calificacion'];

  Map<String, dynamic > toJson(){
    return{
      'idUsuario': idUsuario,
      'nombre': nombre,
      'correo': correo,
      'foto': foto,
      'grupo': grupo,
      'matricula': matricula,
      'telefono': telefono,
      'edad': edad,
      'genero': genero,
      'carrera': carrera,
      'semestre': semestre,
      'calificacion': calificacion
    };

  }
}