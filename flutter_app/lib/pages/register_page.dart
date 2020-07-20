import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutterapp/models/AlumnoItem.dart';
import 'package:flutterapp/models/EscolaresItem.dart';
import 'package:flutterapp/models/MaestroItem.dart';
import 'package:flutterapp/models/UsuarioItem.dart';
import 'package:flutterapp/pages/register_successful.dart';

class RegisterPage extends StatefulWidget {
  RegisterPage( {this.userType});
  final String userType;

  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final _formKey = GlobalKey<FormState>();
  final _scaffoldKey = GlobalKey<ScaffoldState>();
  String _user;
  String _email;
  String _password;
  int _phonenumber;
  int _matricula;
  String _grupo;
  String _instituto;
  String _rfc;
  int _edad;
  String _genero;
  String _empresa;
  String _puesto;
  String _userType;

  bool _isRegistering = false;
  bool _isLoading = false;
  bool _errorMessage = false;

  _register() async{
    if(_isRegistering) return;

    setState(() {
      _isRegistering = true;
    });
    
    _scaffoldKey.currentState.showSnackBar(SnackBar(
        content: Text ('Registrando usuario'),
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
      AuthResult result =  await FirebaseAuth.instance
          .createUserWithEmailAndPassword(email: _email, password: _password);
      FirebaseUser _firebaseUser = result.user;
      UsuarioItem usuarioItem;
      String documentID;
      String userID;
      switch(widget.userType) {
        case 'alumnos':
          _userType = 'alumno';
          AlumnoItem alumnoItem = new AlumnoItem(_firebaseUser.uid, nombre: _user,
            correo: _email, foto: null, grupo: _grupo, matricula: _matricula,
            telefono: _phonenumber, edad: _edad, genero: null,
              carrera: null, semestre: null, calificacion: 0);
          var document = await Firestore.instance.collection('alumnos').add(alumnoItem.toJson());
          documentID = document.documentID;
          usuarioItem = new UsuarioItem(_firebaseUser.uid, idTabla: document.documentID, userType: 1,
              nombre: alumnoItem.nombre, foto: null);
          var document2 = await Firestore.instance.collection('usuarios').add(usuarioItem.toJson());
          userID = document2.documentID;
          break;
        case 'maestros':
          _userType = 'maestro';
          MaestroItem maestroItem = new MaestroItem(_firebaseUser.uid, nombre: _user,
              correo: _email, foto: null, instituto: _instituto, rfc: _rfc,
              telefono: _phonenumber, edad: _edad,puesto: _puesto ,genero: _genero,
              calificacion: 0);
          var document = await Firestore.instance.collection('profesores').add(maestroItem.toJson());
          documentID = document.documentID;
          usuarioItem = new UsuarioItem(_firebaseUser.uid, idTabla: maestroItem.id, userType: 2,
              nombre: maestroItem.nombre);
          var document2 = await Firestore.instance.collection('usuarios').add(usuarioItem.toJson());
          userID = document2.documentID;
          break;
        case 'escolares':
          _userType = 'escolares';
          EscolaresItem escolaresItem = new EscolaresItem(_firebaseUser.uid, nombre: _user,
              correo: _email, foto: null, rfc: _rfc, telefono: _phonenumber,
              edad: _edad,puesto: _puesto, genero: _genero, empresa: _empresa);
          var document = await Firestore.instance.collection('escolares').add(escolaresItem.toJson());
          documentID = document.documentID;
          usuarioItem = new UsuarioItem(_firebaseUser.uid, idTabla: escolaresItem.id, userType: 3,
              nombre: escolaresItem.nombre);
          var document2 = await Firestore.instance.collection('usuarios').add(usuarioItem.toJson());
          userID = document2.documentID;
          break;
      }
      _firebaseUser.sendEmailVerification();
      Navigator.of(context).pop();
      Navigator.of(context).pop();
      showDialog(
          context: context,
          builder:  (_) => RegisterSuccess(userType: _userType, userName: _user,
                                          userID: userID, tableID: documentID,)
      );
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
  void initState() {
    _isLoading = false;
    _errorMessage = false;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    switch(widget.userType) {
      case 'alumnos':
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
      case 'maestros':
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
    }
  }

  Widget showFormAlumnos() {
    return Container(
      padding: EdgeInsets.all(15.0),
      child: Form(
        key: _formKey,
        child: ListView(
          padding: EdgeInsets.symmetric(horizontal: 15.0),
          children: <Widget>[
            showUserInput(),
            showGrupoInput(),
            showEdadInput(),
            showMatriculaInput(),
            showEmailInput(),
            showPhoneNumberInput(),
            showPasswordInput(),
            showSignUpButton(),
          ],
        ),
      ),
    );
  }

  Widget showFormMaestros() {
    return  Form(
        key: _formKey,
        child: ListView(
          shrinkWrap: true,
          children: <Widget>[
            showUserInput(),
            showPuestoInput(),
            showEdadInput(),
            showRFCInput(),
            showEmailInput(),
            showPhoneNumberInput(),
            showPasswordInput(),
            showSignUpButton()
          ],
        ),
      );
  }

  Widget showFormEscolares() {
    return Container(
      padding: EdgeInsets.all(15.0),
      child: Form(
        key: _formKey,
        child: ListView(
          shrinkWrap: true,
          children: <Widget>[
            showUserInput(),
            showEdadInput(),
            showEmpresaInput(),
            showPuestoInput(),
            showRFCInput(),
            showEmailInput(),
            showPhoneNumberInput(),
            showPasswordInput(),
            showSignUpButton()
          ],
        ),
      ),
    );
  }

  Widget showUserInput() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Usuario",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El usuario no puede estar vacío" : null,
        onSaved: (value) => this._user = value.trim(),
      ),
    );
  }

  Widget showEmailInput() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.emailAddress,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Correo",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El campo correo no puede estar vacío" : null,
        onSaved: (value) => _email = value.trim(),
      ),
    );
  }

  bool _showPassword = false;

  Widget showPasswordInput() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        autofocus: false,
        keyboardType: TextInputType.visiblePassword,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: 'Contraseña',
          hintStyle: TextStyle(
            fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
          suffixIcon: GestureDetector(
            onTap: (){
              setState(() {
                _showPassword = !_showPassword;
              });
            },
            child: Icon(
              _showPassword ? Icons.visibility : Icons.visibility_off,
            ),
          ),
        ),
        obscureText: !_showPassword,
        validator: (value) => value.isEmpty ? "El campo contraseña no puede estar vacía" : null,
        onSaved: (value) => _password = value.trim(),
      ),
    );
  }

  Widget showPhoneNumberInput(){
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.phone,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Teléfono (opcional)",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        onSaved: (value) => value.trim().isNotEmpty? _phonenumber =  int.parse(value.trim()) : _phonenumber = null,
      ),
    );//Padding
  }

  Widget showMatriculaInput(){
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.number,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Matricula",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El campo matrícula no puede estar vacío" : null,
        onSaved: (value) => _matricula = int.parse(value.trim()),
      ),
    );//Padding
  }

  Widget showGrupoInput(){
  return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.text,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Grupo",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El campo grupo no puede estar vacío" : null,
        onSaved: (value) => _grupo = value.trim(),
      ),
    );//Padding
  }

  Widget showRFCInput(){
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.text,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "RFC",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El campo rfc no puede estar vacío" : null,
        onSaved: (value) => _rfc = value.trim(),
      ),
    );//Padding
  }
  Widget showEdadInput(){
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.number,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Edad",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El campo edad no puede estar vacío" : null,
        onSaved: (value) => _edad =int.parse( value.trim()),
      ),
    );//Padding
  }

  Widget showPuestoInput(){
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.text,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Puesto",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El campo puesto no puede estar vacío" : null,
        onSaved: (value) => _puesto = value.trim(),
      ),
    );//Padding
  }

  Widget showEmpresaInput(){
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        keyboardType: TextInputType.text,
        autofocus: false,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black38, width: 2.0,),
          ),
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black, width: 3.0,),
          ),
          errorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 2.0,),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Color(0xffde383d), width: 3.0,),
          ),
          hintText: "Empresa",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
        ),
        validator: (value) => value.isEmpty ? "El campo empresa no puede estar vacío" : null,
        onSaved: (value) => _empresa = value.trim(),
      ),
    );//Padding
  }

  Widget showSignUpButton() {
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
            child: Text("Registrarme",
              style: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.white),
            ),
            onPressed: _register,
          ),
        ),
      ),
    );
  }
}