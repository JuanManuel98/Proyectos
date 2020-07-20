import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/pages/nav_drawer.dart';
import 'login_state.dart';
import 'package:intl/intl.dart';

import 'notification_page.dart';
class HomePage extends StatefulWidget {
  HomePage({this.auth, this.userId});
  final BaseAuth auth;
  final String userId;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
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
        backgroundColor: Colors.lightBlueAccent,
        actions: <Widget>[

          SizedBox(
            width: 100.0,
            height: 200.0,
            child: new Container(
                padding: EdgeInsets.only(top: 15),
                child: StreamBuilder(
                    stream: Firestore.instance
                        .collection('usuarios')
                        .where('idUsuario',
                        isEqualTo:  widget.userId)
                        .snapshots(),
                    builder: (BuildContext context,
                        AsyncSnapshot<QuerySnapshot> snapshot) {
                      if (!snapshot.hasData) return CircularProgressIndicator();
                      if (snapshot.data.documents.length == 0)
                        return Text("No existe", style: TextStyle(fontFamily: 'Signika', fontSize: 25));
                      return ListView.builder(
                          itemCount: snapshot.data.documents.length,
                          itemBuilder: (BuildContext context, int index) {
                            return  Center(

                              child: Text(snapshot.data.documents[index].data['nombre'],
                                style: TextStyle(fontFamily: 'Signika', fontSize: 20)),
                            );
                          });
                    }
                )
            ),
          ),
          SizedBox(
            width: 50.0,
            height: 100.0,
            child: new Center(
                child: StreamBuilder(
                    stream: Firestore.instance
                        .collection('usuarios')
                        .where('idUsuario',
                        isEqualTo:  widget.userId)
                        .snapshots(),
                    builder: (BuildContext context,
                        AsyncSnapshot<QuerySnapshot> snapshot) {
                      if (!snapshot.hasData) return CircularProgressIndicator();
                      if (snapshot.data.documents.length == 0)
                        return  CircleAvatar(child: Text("No", style: TextStyle(fontFamily: 'Signika', fontSize: 25)));
                      return ListView.builder(
                          itemCount: snapshot.data.documents.length,
                          itemBuilder: (BuildContext context, int index) {
                            return showPicture(snapshot, index);

                          });
                    }
                )
            ),

          ),


          IconButton(
            icon: Icon(Icons.notifications),
            onPressed: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => NotificationPage(
                        auth: widget.auth,
                        userId: widget.userId,
                      )));
            },
          )
        ],
      ),
      body: new Container(
          child: new Center(
              child: StreamBuilder(
                  stream: Firestore.instance
                      .collection('asesorias')
                      .where('IDUsuario',
                      isEqualTo: widget.userId)
                      .where('status', isEqualTo: 2)
                      .snapshots(),
                  builder: (BuildContext context,
                      AsyncSnapshot<QuerySnapshot> snapshot) {
                    if (!snapshot.hasData) return CircularProgressIndicator();
                    if (snapshot.data.documents.length == 0)
                      return Text("Aun no tienes Asesorias Confirmadas",
                          style:
                          TextStyle(fontFamily: 'Signika', fontSize: 25));
                    return ListView.builder(
                        itemCount: snapshot.data.documents.length,
                        itemBuilder: (BuildContext context, int index) {
                          return drawNewCard(snapshot, index /*, context*/);
                        });
                  }))),
    );
  }

  Widget showPicture(AsyncSnapshot<QuerySnapshot> snapshot, int index) {
    return snapshot == null ?
    Container() :
    Container(
      width: 50.0,
      height: 50.0,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        image: DecorationImage(
          fit: BoxFit.cover,
          image: NetworkImage(snapshot.data.documents[index].data['foto']),
        ),
      ),
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
        color:Colors.lightGreenAccent[100],
        child: Padding(
          padding: const EdgeInsets.all(10.0),
          child: new Column(
            children: <Widget>[
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Text('Aceptada', style: TextStyle(fontFamily: 'Signika', fontSize: 25)),
                  Text(DateFormat.yMMMd().add_jm().format(snapshot.data.documents[index].data['fechas'][snapshot.data.documents[index].data['fechaConfirmada']].toDate()),
                      style: TextStyle(fontFamily: 'Signika', fontSize: 20))
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
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
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
                  IconButton(
                    icon: Icon(
                      Icons.check_box,
                      size: 45,
                      color: Colors.black,
                    ),
                    onPressed: () {},
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
