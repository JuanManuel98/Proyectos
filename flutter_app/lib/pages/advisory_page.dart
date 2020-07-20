import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'login_state.dart';
import 'nav_drawer.dart';

class AdvisoryPage extends StatefulWidget {
  AdvisoryPage({this.auth, this.userId});

  final BaseAuth auth;
  final String userId;

  @override
  _AdvisoryPageState createState() => _AdvisoryPageState();
}

class _AdvisoryPageState extends State<AdvisoryPage> {

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
            "ASESORÍAS",
            style: TextStyle(fontFamily: 'Signika', fontSize: 35.0, color: Colors.white),
          ),
        ),
        body: Container(
          padding: EdgeInsets.all(15),
          child: Column(
            children: <Widget>[
              Expanded(
                flex: 9,
                child: showList(),
              ),
              Expanded(
                flex: 1,
                child: showNewButton(),
              ),
            ],
          ),
        )
    );
  }

  Widget showList() {
    return Container(
      padding: EdgeInsets.all(15),
      child: Table(
        border: TableBorder.all(),
        columnWidths: {0 : FractionColumnWidth(.8), 1 : FractionColumnWidth(.2)},
        children: [
          TableRow(children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Text(
                  'HOY',
                  style: TextStyle(fontFamily: 'Signika', fontSize: 25, fontWeight: FontWeight.bold),
                ),
              ],),
            Column(children: <Widget>[
              Text('')
            ],),
          ]),
          TableRow(children: [
            Padding(
                padding: EdgeInsets.fromLTRB(15, 0, 0, 0),
                child: Text(
                  'Programación',
                  style: TextStyle(fontFamily: 'Signika', fontSize: 25),
                ),
            ),
            Column(children: <Widget>[
              Text(
                '12:50',
                style: TextStyle(fontFamily: 'Signika', fontSize: 25),
              )
            ],),
          ]),
          TableRow(children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Text(
                  'LUNES 1 JUNIO',
                  style: TextStyle(fontFamily: 'Signika', fontSize: 25, fontWeight: FontWeight.bold),
                ),
              ],),
            Column(children: <Widget>[
              Text('')
            ],),
          ]),
          TableRow(children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                Padding(
                    padding: EdgeInsets.fromLTRB(15, 0, 0, 0),
                    child: Icon(Icons.check)
                ),
                Text(
                  'Algoritmos',
                  style: TextStyle(fontFamily: 'Signika', fontSize: 25),
                ),
              ],),
            Column(children: <Widget>[
              Text(
                '14:30',
                style: TextStyle(fontFamily: 'Signika', fontSize: 25),
              )
            ],),
          ]),
        ],
      ),
    );
  }

  Widget showNewButton() {
    return Padding(
      padding: EdgeInsets.fromLTRB(15, 0, 15, 15),
      child: Align(
        alignment: Alignment.bottomRight,
        child: IconButton(
          icon: Icon(Icons.library_add, size: 50,),
          onPressed: () {

          },
        ),
      ),
    );
  }
}