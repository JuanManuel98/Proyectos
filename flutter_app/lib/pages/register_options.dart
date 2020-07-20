import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'register_page.dart';

class RegisterOptions extends StatefulWidget{
  @override
  _RegisterOptionsState createState() => _RegisterOptionsState();
}

class _RegisterOptionsState extends State<RegisterOptions>{
  final _formKey = GlobalKey<FormState>();
  final _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        key: _scaffoldKey,
        appBar: AppBar(
          centerTitle: true,
          title: Text(
            'OPCIONES DE REGISTRO',
            style: TextStyle(fontFamily: 'Signika',
                fontSize: 20.0,
                color: Colors.white,
                fontWeight: FontWeight.w300),
          ),
          backgroundColor: Colors.lightBlueAccent,
        ),
        body: showForm()
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
            showAlumnosButton(),
            showMaestrosButton(),
            showEscolaresButton()
          ],
        ),
      ),
    );
  }

  Widget showAlumnosButton() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 80.0, 0.0, 0.0),
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
            child: Text("Alumnos",
              style: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.white),
            ),
            onPressed: (){
              showDialog(
                  context: context,
                  builder:  (_) => RegisterPage(userType: 'alumnos')
              );
            }
          ),
        ),
      ),
    );
  }

  Widget showMaestrosButton() {
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
              child: Text("Maestros",
                style: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.white),
              ),
              onPressed: (){
                showDialog(
                    context: context,
                    builder:  (_) => RegisterPage(userType: 'maestros')
                );
              }
          ),
        ),
      ),
    );
  }

  Widget showEscolaresButton() {
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
              child: Text("Escolares",
                style: TextStyle(fontFamily: "Signika", fontSize: 20.0, color: Colors.white),
              ),
              onPressed: (){
                showDialog(
                    context: context,
                    builder:  (_) => RegisterPage(userType: 'escolares')
                );
              }
          ),
        ),
      ),
    );
  }

}