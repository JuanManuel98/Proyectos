import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/pages/home_page.dart';
import 'package:flutterapp/pages/login_state.dart';
import 'package:intl/intl.dart';

class NotificationPage extends StatefulWidget {
  NotificationPage({this.auth, this.userId});
  final BaseAuth auth;
  final String userId;

  @override
  _NotificationPageState createState() => _NotificationPageState();
}

class _NotificationPageState extends State<NotificationPage> {
  final _formKey = GlobalKey<FormState>();
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: Builder(builder: (BuildContext context) {
          return IconButton(
            icon: const Icon(Icons.keyboard_backspace),
            color: Colors.white,
            onPressed: () {
              Navigator.pop(context);
              Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(
                      builder: (context) => HomePage(
                            auth: widget.auth,
                            userId: widget.userId,
                          )));
            },
          );
        }),
        title: Text(
          'NOTIFICACIONES',
          style: TextStyle(
            fontFamily: 'Signika',
            color: Colors.white,
            fontSize: 25,
          ),
        ),
      ),
      body: StreamBuilder(
          stream: Firestore.instance
              .collection('notificaciones')
              .where('Receptor', isEqualTo: widget.userId)
              .snapshots(),
          builder:
              (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot) {
            if (!snapshot.hasData) return CircularProgressIndicator();
            if (snapshot.data.documents.length == 0)
              return Center(
                  child: Text(
                "Aun no tienes notificaciones.",
                style: TextStyle(fontFamily: 'Signika', fontSize: 20),
                textAlign: TextAlign.center,
              ));
            return ListView.builder(
                itemCount: snapshot.data.documents.length,
                itemBuilder: (BuildContext context, int index) {
                  return Container(
                      padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                      child: Column(
                        children: <Widget>[
                          Text(
                            snapshot.data.documents[index].data["Descripcion"],
                            style:
                                TextStyle(fontFamily: 'Signika', fontSize: 25),
                            overflow: TextOverflow.clip,
                          ),
                          Row(
                            children: <Widget>[
                              Text(
                                DateFormat.yMMMd().add_jm().format(snapshot
                                    .data.documents[index].data["Fecha_Hora"]
                                    .toDate()),
                                style: TextStyle(
                                    fontFamily: 'Signika', fontSize: 15),
                                textAlign: TextAlign.right,
                              ),
                            ],
                          ),
                          Divider(
                            height: 10,
                          ),
                        ],
                      ));
                });
          }),
    );
  }
}
