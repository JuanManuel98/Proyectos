import 'package:flutter/material.dart';
import 'package:flutterapp/pages/advisory_page.dart';
import 'package:flutterapp/pages/agendaAsesoria.dart';
import 'package:flutterapp/pages/home_page.dart';
import 'package:flutterapp/pages/root_page.dart';
import 'login_state.dart';

class NavDrawer extends StatefulWidget {
  NavDrawer({this.auth, this.userId});

  final BaseAuth auth;
  final String userId;

  @override
  _NavDrawerState createState() => _NavDrawerState();
}

class _NavDrawerState extends State<NavDrawer> {
  signOut() async {
    try {
      await widget.auth.signOut();
    } catch(e) {
      print(e);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          Container(
            height: 100,
            child: DrawerHeader(
              child: Text(
                'NAVEGACIÓN',
                style: TextStyle(fontFamily: 'Signika', color: Colors.white, fontSize: 35,),
              ),
              decoration: BoxDecoration(
                color: Colors.lightBlueAccent,
              ),
            ),
          ),
          ListTile(
            leading: Icon(Icons.supervised_user_circle),
            title: Text(
              'Perfil',
              style: TextStyle(fontFamily: 'Signika', fontSize: 25),
            ),
            onTap: () {
              Navigator.pop(context);
              Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => HomePage(
                auth: widget.auth,
                userId: widget.userId,
              )));
            },
          ),
          ListTile(
            leading: Icon(Icons.list),
            title: Text(
              'Asesorías',
              style: TextStyle(fontFamily: 'Signika', fontSize: 25),
            ),
            onTap: () {
              Navigator.pop(context);
              Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => AgendaAsesoria(
                auth: widget.auth,
                userId: widget.userId,
              )));
            },
          ),
          ListTile(
            leading: Icon(Icons.exit_to_app),
            title: Text(
              'Cerrar Sesión',
              style: TextStyle(fontFamily: 'Signika', fontSize: 25),
            ),
            onTap: () {
              Navigator.pop(context);
              signOut();
              Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => RootPage(
                auth: new Auth()
              )));
            }
          ),
        ],
      ),
    );
  }
}