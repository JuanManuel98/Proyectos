import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:image_picker/image_picker.dart';

class MaestroItem{
  String id;
  String idUsuario;
  String correo = '';
  String foto;
  String nombre = '';
  String instituto = '';
  String rfc = '';
  int telefono;
  int edad;
  String puesto = '';
  String genero = '';
  int calificacion = 0;

  MaestroItem (this.idUsuario, {this.nombre, this.correo, this.foto, this.instituto, this.rfc, this.telefono,
              this.edad, this.puesto, this.genero, this.calificacion});

  MaestroItem.form(DocumentSnapshot snapshot):
        id = snapshot.documentID,
        idUsuario = snapshot['idUsuario'],
        nombre = snapshot['nombre'],
        correo = snapshot['correo'],
        foto = snapshot['foto'],
        instituto = snapshot['intituto'],
        rfc = snapshot['rfc'],
        telefono = snapshot['telefono'],
        edad = snapshot['edad'],
        puesto = snapshot['puesto'],
        genero = snapshot['genero'],
        calificacion = snapshot['calificacion'];


  Map<String, dynamic > toJson(){
    return{
      'idUsuario': idUsuario,
      'nombre': nombre,
      'correo': correo,
      'foto': foto,
      'instituto': instituto,
      'rfc': rfc,
      'telefono': telefono,
      'edad': edad,
      'puesto': puesto,
      'genero': genero,
      'calificacion': calificacion
    };

  }

}