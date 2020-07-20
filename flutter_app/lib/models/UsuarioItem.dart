import 'package:cloud_firestore/cloud_firestore.dart';

class UsuarioItem{
  String id;
  String idUsuario;
  String idTabla;
  int userType;
  String nombre;
  String foto;

  UsuarioItem(this.idUsuario,{this.idTabla, this.userType, this.nombre, this.foto});

  UsuarioItem.form(DocumentSnapshot snapshot):
      id = snapshot.documentID,
      idUsuario = snapshot['idUsuario'],
      idTabla = snapshot['idTabla'],
      userType = snapshot['userType'],
      nombre = snapshot['nombre'],
      foto = snapshot['foto'];

  Map<String, dynamic> toJson(){
    return {
      'idUsuario': idUsuario,
      'idTabla': idTabla,
      'userType': userType,
      'nombre': nombre,
      'foto': foto
    };
  }

}