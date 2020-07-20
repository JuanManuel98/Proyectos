import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/pages/SendNotifications.dart';
import 'package:flutterapp/pages/add_advisory_page.dart';
import 'package:flutterapp/pages/agendaAsesoria.dart';
import 'package:flutterapp/pages/login_state.dart';
import 'package:intl/intl.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';

Timestamp _fechaHora1, _fechaHora2, _fechaHora3;
String _tema, _descripcion, _materia;

class RegistrarAsesoria extends StatefulWidget {
  RegistrarAsesoria(
      {this.auth, this.userId, this.profesorId, this.nombreProfesor, this.correoProfesor});
  final BaseAuth auth;
  final String userId;
  final String profesorId;
  final String nombreProfesor;
  final String correoProfesor;

  @override
  _RegistrarAsesoriaState createState() => _RegistrarAsesoriaState();
}

class _RegistrarAsesoriaState extends State<RegistrarAsesoria> {
  final _formKey = GlobalKey<FormState>();
  final _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        leading: Builder(
          builder: (BuildContext context) {
            return IconButton(
              icon: Icon(Icons.keyboard_backspace),
              color: Colors.white,
              onPressed: () {
                Navigator.pop(context);
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => AddAdvisoryPage(
                              auth: widget.auth,
                              userId: widget.userId,
                            )));
              },
            );
          },
        ),
        centerTitle: true,
        title: Text(
          'Registro de Asesoria',
          style: TextStyle(
              fontFamily: 'Signika', fontSize: 35.0, color: Colors.white),
        ),
      ),
      body: Container(
        child: Form(
          key: _formKey,
          child: ListView(
            shrinkWrap: true,
            padding: EdgeInsets.all(10),
            children: <Widget>[
              materiaInput(),
              temaInput(),
              descripcionInput(),
              FechaHora1(),
              SizedBox(
                height: 20,
              ),
              FechaHora2(),
              SizedBox(
                height: 20,
              ),
              FechaHora3(),
              SizedBox(
                height: 20,
              ),
              botonRegistrar()
            ],
          ),
        ),
      ),
    );
  }

  Widget materiaInput() {
    return Container(
        padding: EdgeInsets.fromLTRB(20, 20, 20, 10),
        child: TextFormField(
          textCapitalization: TextCapitalization.sentences,
          maxLines: 1,
          decoration: InputDecoration(
            border:
                OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
            hintText: 'Materia',
            hintStyle: TextStyle(
                fontFamily: 'Signika', fontSize: 20, color: Colors.grey),
            icon: Icon(
              Icons.book,
              size: 40,
              color: Colors.lightBlue[300],
            ),
            //errorText:
          ),
          validator: (value) =>
              value.isEmpty ? 'Se debe ingresar una materia' : null,
          onSaved: (value) => _materia = value.trim(),
        ));
  }

  Widget temaInput() {
    return Container(
        padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
        child: TextFormField(
          textCapitalization: TextCapitalization.sentences,
          maxLines: 1,
          decoration: InputDecoration(
            border:
                OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
            hintText: 'Tema',
            hintStyle: TextStyle(
                fontFamily: 'Signika', fontSize: 20, color: Colors.grey),
            icon: Icon(
              Icons.text_fields,
              size: 40,
              color: Colors.lightBlue[300],
            ),
          ),
          validator: (value) =>
              value.isEmpty ? 'Se debe ingresar un tema' : null,
          onSaved: (value) => _tema = value.trim(),
        ));
  }

  Widget descripcionInput() {
    return Container(
      height: 200,
      padding: EdgeInsets.fromLTRB(20, 15.0, 20, 10),
      child: TextFormField(
        maxLines: 8,
        textCapitalization: TextCapitalization.sentences,
        decoration: InputDecoration(
          hintText: 'Descripción del tema',
          hintStyle: TextStyle(
              fontFamily: 'Signika', fontSize: 20, color: Colors.grey),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
        ),
        validator: (value) =>
            value.isEmpty ? 'Se debe ingresar una descripción' : null,
        onSaved: (value) => _descripcion = value.trim(),
      ),
    );
  }

  Widget botonRegistrar() {
    return Padding(
      padding: EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 0.0),
      child: Align(
        alignment: Alignment.center,
        child: SizedBox(
          height: 50.0,
          width: 250,
          child: RaisedButton(
            elevation: 5.0,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20.0),
            ),
            color: Colors.lightBlueAccent,
            child: Text(
              "Registrar Asesoria",
              style: TextStyle(
                  fontFamily: "Signika", fontSize: 20.0, color: Colors.white),
            ),
            onPressed: registroAsesoria,
          ),
        ),
      ),
    );
  }

  void registroAsesoria() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();
      DocumentReference ref =
          await Firestore.instance.collection('asesorias').add({
        'IDUsuario': widget.userId,
        'materia': '$_materia',
        'status': 1,
        'tema': '$_tema',
        'profesor': widget.nombreProfesor,
        'descripcion': '$_descripcion',
        'fechaConfirmada': 0,
        'fechas': [_fechaHora1, _fechaHora2, _fechaHora3]
      });
      //setState(() => )
      _scaffoldKey.currentState.showSnackBar(SnackBar(
        content: Text('Registrando Asesoria'),
      ));
      print(ref.documentID);
      sendNotifications(context, widget.auth, widget.profesorId, widget.userId, widget.correoProfesor);
    }
  }
}

class FechaHora1 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DateTimeField(
      decoration: InputDecoration(
          hintText: 'Ingresa fecha y hora 1',
          hintStyle: TextStyle(
              fontFamily: 'Signika', fontSize: 20, color: Colors.grey),
          helperText: 'Hora y Fecha, Propuesta 1',
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
          icon: Icon(
            Icons.calendar_today,
            size: 40,
            color: Colors.lightBlue[300],
          )),
      format: DateFormat.yMMMd().add_jm(),
      onShowPicker: (context, currentValue) async {
        final fecha1 = await showDatePicker(
          context: context,
          initialDate: new DateTime.now(),
          firstDate: new DateTime.now(),
          lastDate: new DateTime(2030),
          locale: Locale('es', 'ES'),
        );
        if (fecha1 != null) {
          TimeOfDay hora1 = await showTimePicker(
              context: context, initialTime: new TimeOfDay.now());

          return DateTimeField.combine(fecha1, hora1);
        }
      },
      onSaved: (value) => _fechaHora1 = Timestamp.fromDate(value),
    );
  }
}

class FechaHora2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DateTimeField(
      decoration: InputDecoration(
          hintText: 'Ingresa fecha y hora 2',
          hintStyle: TextStyle(
              fontFamily: 'Signika', fontSize: 20, color: Colors.grey),
          helperText: 'Hora y Fecha, Propuesta 2',
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
          icon: Icon(
            Icons.calendar_today,
            size: 40,
            color: Colors.lightBlue[300],
          )),
      format: DateFormat.yMMMd().add_jm(),
      onShowPicker: (context, currentValue) async {
        final fecha2 = await showDatePicker(
          context: context,
          initialDate: new DateTime.now(),
          firstDate: new DateTime.now(),
          lastDate: new DateTime(2030),
          locale: Locale('es', 'ES'),
        );
        if (fecha2 != null) {
          TimeOfDay hora2 = await showTimePicker(
              context: context, initialTime: new TimeOfDay.now());

          return DateTimeField.combine(fecha2, hora2);
        }
      },
      onSaved: (value) => _fechaHora2 = Timestamp.fromDate(value),
    );
  }
}

class FechaHora3 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DateTimeField(
      decoration: InputDecoration(
          hintText: 'Ingresa fecha y hora 3',
          hintStyle: TextStyle(
              fontFamily: 'Signika', fontSize: 20, color: Colors.grey),
          helperText: 'Hora y Fecha, Propuesta 3',
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
          icon: Icon(
            Icons.calendar_today,
            size: 40,
            color: Colors.lightBlue[300],
          )),
      format: DateFormat.yMMMd().add_jm(),
      onShowPicker: (context, currentValue) async {
        final fecha3 = await showDatePicker(
          context: context,
          initialDate: new DateTime.now(),
          firstDate: new DateTime.now(),
          lastDate: new DateTime(2030),
          locale: Locale('es', 'ES'),
        );
        if (fecha3 != null) {
          TimeOfDay hora3 = await showTimePicker(
              context: context, initialTime: new TimeOfDay.now());

          return DateTimeField.combine(fecha3, hora3);
        }
      },
      onSaved: (value) => _fechaHora3 = Timestamp.fromDate(value),
    );
  }
}
