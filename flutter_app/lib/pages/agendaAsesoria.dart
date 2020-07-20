import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/pages/nav_drawer.dart';
import 'add_advisory_page.dart';
import 'login_state.dart';
import 'package:intl/intl.dart';

class AgendaAsesoria extends StatefulWidget {
  AgendaAsesoria({this.auth, this.userId});
  final BaseAuth auth;
  final String userId;

  @override
  _AgendaAsesoriaState createState() => _AgendaAsesoriaState();
}

class _AgendaAsesoriaState extends State<AgendaAsesoria> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: NavDrawer(
        auth: widget.auth,
        userId: widget.userId,
      ),
      appBar: AppBar(
        centerTitle: true,
        title: Text(
          "Agenda de Asesorias",
          style: TextStyle(
              fontFamily: 'Signika', fontSize: 35.0, color: Colors.white),
        ),
      ),
      body: new Container(
          child: new Center(
              child: StreamBuilder(
                  stream: Firestore.instance
                      .collection('asesorias')
                      .where('IDUsuario', isEqualTo: widget.userId)
                      .snapshots(),
                  builder: (BuildContext context,
                      AsyncSnapshot<QuerySnapshot> snapshot) {
                    if (!snapshot.hasData) return CircularProgressIndicator();
                    if (snapshot.data.documents.length == 0)
                      return Text("Aun no tienes Asesorias registradas",
                          style:
                              TextStyle(fontFamily: 'Signika', fontSize: 25));
                    return ListView.builder(
                        itemCount: snapshot.data.documents.length,
                        itemBuilder: (BuildContext context, int index) {
                          return drawNewCard(snapshot, index /*, context*/);
                        });
                  }))),
      floatingActionButton: showNewButton(),
    );
  }

  Widget drawNewCard(AsyncSnapshot<QuerySnapshot> snapshot, int index) {
    int statusAsesoria = snapshot.data.documents[index].data['status'];
    return Container(
      padding: EdgeInsets.only(left: 20.0, right: 20.0, top: 20.0),
      child: Card(
        elevation: 10,
        shape:
            RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
        color: colorCard(statusAsesoria),
        child: Padding(
          padding: const EdgeInsets.all(10.0),
          child: new Column(
            children: <Widget>[
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  status(statusAsesoria),
                  fechaHora(snapshot, index),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Text(snapshot.data.documents[index].data['materia'],
                      style: TextStyle(fontFamily: 'Signika', fontSize: 20)),
                ],
              ),
              Row(
                children: <Widget>[
                  Text(snapshot.data.documents[index].data['tema'],
                      style: TextStyle(fontFamily: 'Signika', fontSize: 20)),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Text(snapshot.data.documents[index].data['profesor'],
                      style: TextStyle(fontFamily: 'Signika', fontSize: 20)),
                  eliminar(statusAsesoria),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  //Dibuja un icon para eliminar en las asesorias rechazadas
  Container eliminar(int status) {
    if (status == 0) {
      //rechazada
      return Container(
          child: IconButton(
        icon: Icon(
          Icons.delete_forever,
          size: 45,
          color: Colors.black,
        ),
        onPressed: () {},
      ));
    } else if (status == 2) //confirmada
      return Container(
          child: IconButton(
        icon: Icon(
          Icons.check_box,
          size: 45,
          color: Colors.black,
        ),
        onPressed: () {},
      ));
    else
      return Container(
          child: IconButton(
        icon: Image.asset('lib/imagenes/iconPin.png'),
        onPressed: () {/*eliminar asesoria de BD*/},
      ));
  }

  //Dibuja el texto dependiendo del estado de la Asesoria
  Text status(int estado) {
    if (estado == 1)
      return Text('Pendiente',
          style: TextStyle(fontFamily: 'Signika', fontSize: 25));
    else if (estado == 2)
      return Text('Aceptada',
          style: TextStyle(fontFamily: 'Signika', fontSize: 25));
    else
      return Text('Rechazada',
          style: TextStyle(fontFamily: 'Signika', fontSize: 25));
  }

  //Dibuja la hora de la asesoria
  Text fechaHora(AsyncSnapshot<QuerySnapshot> snapshot, int index) {
    int status = snapshot.data.documents[index].data['status'];
    int fechaSelec = snapshot.data.documents[index].data['fechaConfirmada'];
    DateTime fechaConfirmada =
        (snapshot.data.documents[index].data['fechas'][fechaSelec]).toDate();

    if (status == 1)
      return Text('Por confirmar',
          style: TextStyle(fontFamily: 'Signika', fontSize: 20));
    else if (status == 0)
      return Text('');
    else
      return Text(DateFormat.yMMMd().add_jm().format(fechaConfirmada),
          style: TextStyle(fontFamily: 'Signika', fontSize: 20));
  }

  //Establece el color de la tarjeta por cada asesoria
  Color colorCard(int estado) {
    if (estado == 1)
      return Colors.deepOrange[300];
    else if (estado == 2)
      return Colors.lightGreenAccent[100];
    else
      return Colors.red[400];
  }

  //Dibuja el boton para agregar una asesoria
  Widget showNewButton() {
    return Padding(
      padding: EdgeInsets.fromLTRB(15, 0, 15, 15),
      child: Align(
        alignment: Alignment.bottomCenter,
        child: IconButton(
          icon: Icon(
            Icons.add_circle,
            size: 50,
            color: Colors.lightBlueAccent,
          ),
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
        ),
      ),
    );
  }
}
