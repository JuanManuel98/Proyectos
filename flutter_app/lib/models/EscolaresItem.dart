import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:image_picker/image_picker.dart';

class EscolaresItem{
  String id;
  String idUsuario;
  String correo = '';
  String foto;
  String nombre = '';
  String rfc = '';
  int telefono = 0;
  int edad = 0;
  String puesto = '';
  String genero = '';
  String empresa = '';

  EscolaresItem (this.idUsuario, {this.nombre, this.correo, this.foto, this.rfc, this.telefono,
                this.edad, this.puesto, this.genero, this.empresa});

  EscolaresItem.form(DocumentSnapshot snapshot):
        id = snapshot.documentID,
        idUsuario = snapshot['idUsuario'],
        nombre = snapshot['nombre'],
        correo = snapshot['correo'],
        foto = snapshot['foto'],
        rfc = snapshot['rfc'],
        telefono = snapshot['telefono'],
        edad = snapshot['edad'],
        puesto = snapshot['puesto'],
        genero = snapshot['genero'],
        empresa = snapshot['empresa'];

  Map<String, dynamic > toJson(){
    return{
      'idUsuario': idUsuario,
      'nombre': nombre,
      'correo': correo,
      'foto': foto,
      'rfc': rfc,
      'telefono': telefono,
      'edad': edad,
      'puesto': puesto,
      'genero': genero,
      'empresa': empresa
    };

  }

}