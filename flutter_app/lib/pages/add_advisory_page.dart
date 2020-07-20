import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/pages/registrarAsesoria.dart';
import 'login_state.dart';
import 'nav_drawer.dart';

class AddAdvisoryPage extends StatefulWidget {
  AddAdvisoryPage({this.auth, this.userId});
  final BaseAuth auth;
  final String userId;

  @override
  _AddAdvisoryPageState createState() => _AddAdvisoryPageState();
}

class _AddAdvisoryPageState extends State<AddAdvisoryPage> {
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
            "Añadir Asesoria",
            style: TextStyle(
                fontFamily: 'Signika', fontSize: 35.0, color: Colors.white),
          ),
        ),
        body: Container(
          child: Center(
            child: listaProfesores(context),
          ),
        ));
  }

  Widget listaProfesores(BuildContext context) {
    return StreamBuilder(
        stream: Firestore.instance
            .collection('profesores')
            .orderBy('calificacion', descending: true)
            .snapshots(),
        builder: (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot) {
          if (!snapshot.hasData) return CircularProgressIndicator();
          if (snapshot.data.documents.length == 0)
            return Text("Aun no hay profesores registrados",
                style: TextStyle(fontFamily: 'Signika', fontSize: 25));
          return GridView.count(
              padding: EdgeInsets.only(right: 10, left: 10, top: 10),
              crossAxisSpacing: 10,
              crossAxisCount: 2,
              children: List.generate(snapshot.data.documents.length, (index) {
                return tarjetaProfesor(snapshot, index);
              }));
        });
  }

  Widget tarjetaProfesor(AsyncSnapshot<QuerySnapshot> snapshot, int index) {
    return IconButton(
      icon: Card(
        color: Colors.teal[50],
        elevation: 15,
        shape:
            RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
        child: Column(
          children: <Widget>[
            Image.network(snapshot.data.documents[index].data['foto'],
                height: 85),
            Container(
              child: Column(
                children: <Widget>[
                  Text(snapshot.data.documents[index].data['nombre'],
                      style: TextStyle(fontFamily: 'Signika', fontSize: 18)),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Icon(
                            Icons.star,
                            color: Colors.yellow[600],
                          ),
                          Text(
                              snapshot
                                  .data.documents[index].data['calificacion']
                                  .toString(),
                              style: TextStyle(
                                  fontFamily: 'Signika', fontSize: 18))
                        ],
                      ),
                      IconButton(
                          icon: Icon(
                            Icons.arrow_forward_ios, /*size: 10*/
                          ),
                          //iconSize: 5,
                          onPressed: () {
                            Navigator.pop(context);
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => RegistrarAsesoria(
                                          auth: widget.auth,
                                          userId: widget.userId,
                                          profesorId: snapshot
                                              .data.documents[index].data['idUsuario'],
                                          nombreProfesor: snapshot.data
                                              .documents[index].data['nombre'],
                                          correoProfesor: snapshot.data.documents[index].data['correo'],
                                        )));
                          })
                    ],
                  )
                ],
              ),
            ),
          ],
        ),
      ),
      onPressed: () {
        Navigator.pop(context);
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => RegistrarAsesoria(
                      auth: widget.auth,
                      userId: widget.userId,
                      profesorId: snapshot.data.documents[index].data['idUsuario'],
                      nombreProfesor: snapshot.data.documents[index].data['nombre'],
                      correoProfesor: snapshot.data.documents[index].data['correo'],
                    )));
      },
    );
  }
}
