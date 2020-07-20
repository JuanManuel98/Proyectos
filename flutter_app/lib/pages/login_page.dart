import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/models/AlumnoItem.dart';
import 'package:flutterapp/pages/register_options.dart';
import 'login_state.dart';
import 'register_page.dart';

class LoginPage extends StatefulWidget {
  LoginPage({this.auth, this.loginCallback});

  final BaseAuth auth;
  final VoidCallback loginCallback;

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {

  final _formKey = GlobalKey<FormState>();
  bool _isLoading = false;
  bool _errorMessage = false;
  String _email;
  String _password;
  CollectionReference alumnosRef;

  /*addTodo() async{
    AlumnoItem alumno = await showDialog(
        context: context,
        builder:  (_) => RegisterPage()
    );
    if(alumno != null){
      alumnosRef = Firestore.instance.collection('alumnos');
      DocumentReference user = await alumnosRef.add(alumno.toJson());
    }
  }*/

  bool validateAndSave() {
    final form = _formKey.currentState;
    if(form.validate()) {
      form.save();
      return true;
    }
    return false;
  }

  void validateAndSubmit() async {
    setState(() {
      _isLoading = true;
      _errorMessage = false;
    });
    if(validateAndSave()) {
      String userId = "";
      try {
        userId = await widget.auth.signIn(_email, _password);
        print("Inició sesión: $userId");
        setState(() {
          _isLoading = true;
        });
        if(userId.length > 0 && userId != null) {
          widget.loginCallback();
        }
      } catch (e) {
        print("Error: $e");
        setState(() {
          _isLoading = false;
          _errorMessage = true;
          _formKey.currentState.reset();
        });
      }
    }
    setState(() {
      _isLoading = false;
    });
  }

  @override
  void initState() {
    _isLoading = false;
    _errorMessage = false;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text(
          'PORTAL ASESORÍAS',
          style: TextStyle(fontFamily: 'Signika', fontSize: 35.0, color: Colors.white,),
        ),
      ),
      body: Stack(
        children: <Widget>[
          showForm(),
          showCircularProgress(),
        ],
      )
    );
  }

  Widget showForm() {
    return Container(
      padding: EdgeInsets.all(15.0),
      child: Form(
        key: _formKey,
        child: ListView(
          shrinkWrap: true,
          children: <Widget>[
            showEmailInput(),
            showPasswordInput(),
            //showSaveUser(),
            showLogInButton(),
            showErrorMessage(),
            showHelpText(),
          ],
        ),
      ),
    );
  }

  Widget showEmailInput() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 100.0, 0.0, 0.0),
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
          hintText: "usuario",
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
          ),
        validator: (value) => value.isEmpty ? "El usuario no puede estar vacío" : null,
        onSaved: (value) => _email = value.trim(),
      ),
    );
  }

  bool _obscureText = true;
  bool _saveUser = false;
  var _visibilityOn = Icon(Icons.visibility);
  var _visibilityOff = Icon(Icons.visibility_off);

  void _toggle() {
    setState(() {
      _obscureText = !_obscureText;
    });
  }

  Widget showPasswordInput() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: TextFormField(
        maxLines: 1,
        obscureText: _obscureText,
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
          hintText: 'contraseña',
          hintStyle: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.grey,),
          suffixIcon: IconButton(
            icon: _obscureText? _visibilityOn : _visibilityOff,
            onPressed: _toggle,
          ),
        ),
        validator: (value) => value.isEmpty ? "La contraseña no puede estar vacía" : null,
        onSaved: (value) => _password = value.trim(),
      ),
    );
  }

  Widget showSaveUser() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: <Widget>[
          Text('Guardar Usuario', style: TextStyle(fontFamily: 'Signika', fontSize: 15),),
          Switch(
            value: _saveUser,
            onChanged: (value) {
              setState(() {
                _saveUser = value;
              });
            },
            activeColor: Colors.lightBlueAccent,
          ),
        ],
      )
    );
  }

  Widget showLogInButton() {
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
              child: Text("Ingresar",
                  style: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.white),
              ),
              onPressed: validateAndSubmit,
            ),
          ),
        ),
    );
  }

  Widget showErrorMessage() {
    if (_errorMessage) {
      return Padding(
        padding: EdgeInsets.fromLTRB(0.0, 10.0, 0.0, 0.0),
        child: Align(
          alignment: Alignment.centerRight,
          child: Text(
            "Usuario o contraseña incorrectos",
            style: TextStyle(
              fontFamily: "Signika",
              fontSize: 20.0,
              color: Color(0xffde383d),
              fontWeight: FontWeight.w300,
            ),
          ),
        ),
      );
    }
    return Container(
      height: 0.0,
    );
  }

  Widget showHelpText() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 40.0, 0.0, 0.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.end,
        mainAxisAlignment: MainAxisAlignment.end,
        children: <Widget>[
          FlatButton(
            onPressed:(){
              showDialog(
                  context: context,
                  builder:  (_) => RegisterOptions()
              );
              //_addTodo,
            },
            child: Text("Registrarme", style: TextStyle(fontFamily: 'Signika', decoration: TextDecoration.underline, fontSize: 15, color: Colors.blue),),
          ),
          FlatButton(
            onPressed: () {
              /*  */
            },
            child: Text("Olvidé mi contraseña", style: TextStyle(fontFamily: 'Signika', decoration: TextDecoration.underline, fontSize: 15, color: Colors.blue),),
          )
        ],
      ),
    );
  }

  Widget showCircularProgress() {
    if (_isLoading) {
      return Center(child: CircularProgressIndicator());
    }
    return Container(
      height: 0.0,
      width: 0.0,
    );
  }
}
