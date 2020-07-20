import 'package:flutter/material.dart';
import 'package:splashscreen/splashscreen.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'pages/login_state.dart';
import 'pages/root_page.dart';

void main(){
  runApp(new MaterialApp(
    home: new MyApp(),
  ));
}
class MyApp extends StatefulWidget{
  @override
  State<StatefulWidget> createState(){
    return _MyApp();
  }
}

class _MyApp extends State<MyApp>{
  @override
  Widget build(BuildContext context){
    return SplashScreen(
      seconds: 5,
      navigateAfterSeconds: AffterSplash(),
      title: Text(
        'PORTAL ASESORÍAS',
        textAlign: TextAlign.center,
        style: TextStyle(fontFamily: 'Signika' ,fontWeight: FontWeight.bold, fontSize: 75.0, color: Colors.white)
      ),
      backgroundColor: Colors.lightBlueAccent,
      styleTextUnderTheLoader: TextStyle(),
      photoSize: 50.0,
      useLoader: false,
    );
  }
}

class AffterSplash extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
        title: 'Portal Asesorías',
        debugShowCheckedModeBanner: false,
        localizationsDelegates: [
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
        ],
        supportedLocales: [
          const Locale('en', 'US'),
          const Locale('es', 'ES'),
        ],
        theme: new ThemeData(
          primaryColor: Colors.lightBlueAccent,
        ),
        home: new RootPage(
            auth: new Auth()
        ),
    );
  }
}