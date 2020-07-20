import 'dart:async';
import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/models/AlumnoItem.dart';
import 'package:flutterapp/models/UsuarioItem.dart';
import 'package:image_picker/image_picker.dart';
import 'package:uuid/uuid.dart';
import 'register_page.dart';

// ignore: must_be_immutable
class RegisterSuccess extends StatefulWidget{
  RegisterSuccess ({this.userType, this.userName, this.userID, this.tableID});
  String userType;
  String userName;
  String userID;
  String tableID;
  @override
  _RegisterSuccessState createState() => _RegisterSuccessState();
}

class _RegisterSuccessState extends State<RegisterSuccess>{
  final _formKey = GlobalKey<FormState>();
  final _scaffoldKey = GlobalKey<ScaffoldState>();
  String _genero;
  String _empresa;
  String _semestre;
  String _carrera;
  String _instituto;
  File _selectedPicture;

  bool _isRegistering = false;
  bool _isLoading = false;
  bool _errorMessage = false;

  var _generos = ['Masculino', 'Femenino', 'No binario'];
  var _generoSeleccionado = 'No binario';
  var _institutos = ['Computación', 'Electrónica y mecatrónica',
    'Ciencias Sociales y Humanidades', 'Física y Matemáticas',
    'Minería', 'Diseño', 'Agroindustrias', 'Hidrología',
    'Industrial y Automotriz'];
  var _institutoSeleccionado = 'Computación';
  var _semestres = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto',
    'Sexto', 'Séptimo', 'Octavo', 'Noveno', 'Décimo'];
  var _semestreSeleccionado = 'Primero';
  var _carreras = ['Ing. en Computación', 'Ing. en Diseño', 'Lic. en Matemáticas Aplicadas',
    'Ing. en Electrónica', 'Ing. en Mecatrónica', 'Ing. en Físicas Aplicadas',
    'Ing. Industrial', 'Ing. Automotriz', 'Ing. en Alimentos',
    'Lic. en Ciencias Empresariales', 'Lic. en Estudios Mexicanos'];
  var _carreraSeleccionada = 'Ing. en Computación';

  _upgrade() async{
    if(_isRegistering) return;

    setState(() {
      _isRegistering = true;
    });

    _scaffoldKey.currentState.showSnackBar(SnackBar(
      content: Text ('Agregando datos al usuario'),
    ));

    final form = _formKey.currentState;

    if(!form.validate()){
      _scaffoldKey.currentState.hideCurrentSnackBar();
      setState(() {
        _isRegistering=false;
      });
      return;
    }
    form.save();
    try {
      switch(widget.userType) {
        case 'alumno':
          var document = Firestore.instance.collection('alumnos').document(widget.tableID);
          await document.updateData(
            {'genero': _genero, 'carrera': _carrera, 'semestre':_semestre}
          );
          if(_selectedPicture != null){
            final StorageReference postImageRef = FirebaseStorage.instance.ref().child("Post Images");
            var timeKey = DateTime.now();
            final StorageUploadTask uploadTask =
                postImageRef.child(timeKey.toString()+".jpg").putFile(_selectedPicture);
            var imageUrl = await(await uploadTask.onComplete).ref.getDownloadURL();
            await document.updateData({'foto' : imageUrl});
            await Firestore.instance.collection('usuarios').document(widget.userID).updateData({'foto' : imageUrl});
          }
          break;
        case 'maestro':
          var document = Firestore.instance.collection('profesores').document(widget.tableID);
          await document.updateData(
              {'genero': _genero, 'instituto': _instituto}
          );
          if(_selectedPicture != null){
            final StorageReference postImageRef = FirebaseStorage.instance.ref().child("Post Images");
            var timeKey = DateTime.now();
            final StorageUploadTask uploadTask =
            postImageRef.child(timeKey.toString()+".jpg").putFile(_selectedPicture);
            var imageUrl = await(await uploadTask.onComplete).ref.getDownloadURL();
            await document.updateData({'foto' : imageUrl});
            await Firestore.instance.collection('usuarios').document(widget.userID).updateData({'foto' : imageUrl});
          }
          break;
        case 'escolares':
          var document = Firestore.instance.collection('escolares').document(widget.tableID);
          await document.updateData(
              {'genero': _genero}
          );
          if(_selectedPicture != null){
            final StorageReference postImageRef = FirebaseStorage.instance.ref().child("Post Images");
            var timeKey = DateTime.now();
            final StorageUploadTask uploadTask =
            postImageRef.child(timeKey.toString()+".jpg").putFile(_selectedPicture);
            var imageUrl = await(await uploadTask.onComplete).ref.getDownloadURL();
            await document.updateData({'foto' : imageUrl});
            await Firestore.instance.collection('usuarios').document(widget.userID).updateData({'foto' : imageUrl});
          }
          break;
      }
      Navigator.of(context).pop();
    } catch (e){
      _scaffoldKey.currentState.hideCurrentSnackBar();
      _scaffoldKey.currentState.showSnackBar(SnackBar(
        content: Text(e.toString()),
        duration: Duration(seconds: 10),
        action: SnackBarAction(
          label: 'Rechazado',
          onPressed: (){
            _scaffoldKey.currentState.hideCurrentSnackBar();
          },
        ),
      ));
    } finally{
      setState(() {
        _isRegistering = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    switch(widget.userType) {
      case 'alumno':
        return Scaffold(
            key: _scaffoldKey,
            appBar: AppBar(
              centerTitle: true,
              title: Text(
                'REGISTRO ALUMNOS',
                style: TextStyle(fontFamily: 'Signika',
                    fontSize: 20.0,
                    color: Colors.white,
                    fontWeight: FontWeight.w300),
              ),
              backgroundColor: Colors.lightBlueAccent,
            ),
            body: showFormAlumnos()
        );
        break;
      case 'maestro':
        return Scaffold(
            key: _scaffoldKey,
            appBar: AppBar(
              centerTitle: true,
              title: Text(
                'REGISTRO MAESTROS',
                style: TextStyle(fontFamily: 'Signika',
                    fontSize: 20.0,
                    color: Colors.white,
                    fontWeight: FontWeight.w300),
              ),
              backgroundColor: Colors.lightBlueAccent,
            ),
            body: showFormMaestros()
        );
        break;
      case 'escolares':
        return Scaffold(
            key: _scaffoldKey,
            appBar: AppBar(
              centerTitle: true,
              title: Text(
                'REGISTRO ESCOLARES',
                style: TextStyle(fontFamily: 'Signika',
                    fontSize: 20.0,
                    color: Colors.white,
                    fontWeight: FontWeight.w300),
              ),
              backgroundColor: Colors.lightBlueAccent,
            ),
            body: showFormEscolares()
        );
        break;
      default:
        print(widget.userType);
        return Container(
          width: 0.0,
          height: 0.0,
        );
        break;
    }
  }

  Widget showFormAlumnos(){
    return Container(
      padding: EdgeInsets.all(15.0),
      child: Form(
        key: _formKey,
        child: ListView(
          padding: EdgeInsets.symmetric(horizontal: 15.0),
          children: <Widget>[
            showPhotoInput(),
            if(_selectedPicture != null)
              SizedBox(
                height: 100,
                child: Image.file(_selectedPicture),
              ),
            SizedBox(height: 5.0,),
            showGeneroInput(),
            showSemestreInput(),
            showCarreraInput(),
            showUpgradeButton(),
          ],
        ),
      ),
    );
  }

  Widget showFormMaestros(){
    return Container(
      padding: EdgeInsets.all(15.0),
      child: Form(
        key: _formKey,
        child: ListView(
          padding: EdgeInsets.symmetric(horizontal: 15.0),
          children: <Widget>[
            showPhotoInput(),
            if(_selectedPicture != null)
              SizedBox(
                height: 100,
                child: Image.file(_selectedPicture),
              ),
            SizedBox(height: 5.0,),
            showGeneroInput(),
            showInstitutoInput(),
            showUpgradeButton()
          ],
        ),
      ),
    );
  }

  Widget showFormEscolares(){
    return Container(
      padding: EdgeInsets.all(15.0),
      child: Form(
        key: _formKey,
        child: ListView(
          padding: EdgeInsets.symmetric(horizontal: 15.0),
          children: <Widget>[
            showPhotoInput(),
            if(_selectedPicture != null)
              SizedBox(
                height: 100,
                child: Image.file(_selectedPicture),
              ),
            SizedBox(height: 5.0,),
            showGeneroInput(),
            showUpgradeButton(),
          ],
        ),
      ),
    );
  }

  Widget showPhotoInput(){
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: SizedBox(
        height: 40.0,
        width:  double.infinity,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            Text("Foto de perfil: ", textScaleFactor: 1.2, textAlign: TextAlign.left,),
            //SizedBox(height: 10.0,),
            IconButton(
              icon: Icon(Icons.add_a_photo),
              color: Colors.grey,
              onPressed: () async{
                var image = await ImagePicker().getImage(source: ImageSource.gallery);
                setState(() {
                  _selectedPicture = File(image.path);
                });
              },
            ),
          ],
        ),
      ),
    );
  }


  Widget showGeneroInput(){
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Text("Genero: ", textScaleFactor: 1.1, textAlign: TextAlign.right,),
        //SizedBox(height: 10.0,),
        DropdownButton<String>(
          items: _generos.map((String dropDownStringItem){
            return DropdownMenuItem<String>(
              value: dropDownStringItem,
              child: Text(dropDownStringItem, textScaleFactor: 1,),
            );
          }).toList(),
          onChanged: (String newValueSelected){
            setState(() {
              this._generoSeleccionado = newValueSelected;
              this._genero = newValueSelected;
            });
          },
          value: _generoSeleccionado,
          isExpanded: false,
        ),
      ],
    );
  }

  Widget showInstitutoInput(){
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Text("Instituto: ", textScaleFactor: 1.1, textAlign: TextAlign.right,),
        //SizedBox(height: 10.0,),
        DropdownButton<String>(
          items: _institutos.map((String dropDownStringItem){
            return DropdownMenuItem<String>(
              value: dropDownStringItem,
              child: Text(dropDownStringItem, textScaleFactor: 1,),
            );
          }).toList(),
          onChanged: (String newValueSelected){
            setState(() {
              this._institutoSeleccionado = newValueSelected;
              this._instituto = newValueSelected;
            });
          },
          value: _institutoSeleccionado,
          isExpanded: false,
        ),
      ],
    );
  }

  Widget showCarreraInput(){
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Text("Carrera: ", textScaleFactor: 1.1, textAlign: TextAlign.center,),
        //SizedBox(height: 10.0,),
        DropdownButton<String>(
          items: _carreras.map((String dropDownStringItem){
            return DropdownMenuItem<String>(
              value: dropDownStringItem,
              child: Text(dropDownStringItem, textScaleFactor: 0.95,),
            );
          }).toList(),
          onChanged: (String newValueSelected){
            setState(() {
              this._carreraSeleccionada = newValueSelected;
              this._carrera = newValueSelected;
            });
          },
          value: _carreraSeleccionada,
          isExpanded: false,
        ),
      ],
    );
  }

  Widget showSemestreInput(){
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Text("Semestre: ", textScaleFactor: 1.2, textAlign: TextAlign.right,),
        //SizedBox(height: 10.0,),
        DropdownButton<String>(
          items: _semestres.map((String dropDownStringItem){
            return DropdownMenuItem<String>(
              value: dropDownStringItem,
              child: Text(dropDownStringItem, textScaleFactor: 1.2,),
            );
          }).toList(),
          onChanged: (String newValueSelected){
            setState(() {
              this._semestreSeleccionado = newValueSelected;
              this._semestre = newValueSelected;
            });
          },
          value: _semestreSeleccionado,
        ),
      ],
    );
  }

  Widget showUpgradeButton() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: Align(
        alignment: Alignment.center,
        child: SizedBox(
          height: 40.0,
          width: double.infinity,
          child: RaisedButton(
            elevation: 5.0,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(5.0),
            ),
            color: Colors.lightBlueAccent,
            child: Text("Subir datos",
              style: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.white),
            ),
            onPressed: _upgrade,
          ),
        ),
      ),
    );
  }

}