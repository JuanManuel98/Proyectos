import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutterapp/pages/agendaAsesoria.dart';
import 'package:mailer/mailer.dart';
import 'package:mailer/smtp_server.dart';


sendNotifications(context, auth, profesorId, userId, correoProfesor)async{
  //***Envio de correo electronico***//
  String username = "asesoriascikam@gmail.com";
  String password = "asesorias20*";

  final smtpServer = gmail(username, password);
  // Creating the Gmail server

  // Create our email message.
  final message = Message()
    ..from = Address(username)
    ..recipients.add(correoProfesor) //recipent email
    ..subject = 'PORTAL ASESORIAS: Solicitud de nueva asesoria' //subject of the email
    ..text = '¡Buen día! \n\nUn alumno le ha enviado una solicitud de asesorías dentro de nuestro PORTAL ASESORIAS. \n\nQue tenga un excelente día. \n\nAtentamente: CIKAMWARE.'; //body of the email

  try {
    final sendReport = await send(message, smtpServer);
    print('Message sent: ' + sendReport.toString()); //print if the email is sent
  } on MailerException catch (e) {
    print('Message not sent. \n'+ e.toString()); //print if the email is not sent
    // e.toString() will show why the email is not sending
  }

  //**Enviar a notificaciones del profesor y viseversa**//
  DateTime fecha_hr = DateTime.now();
  Timestamp fechaHora = Timestamp.fromDate(fecha_hr);
  
  DocumentReference ref =
    await Firestore.instance.collection('notificaciones').add({
      'Descripcion': '¡Un alumno ha solicitado una nueva asesoría!',
      'Emisor': userId,
      'Estatus': 0,
      'Fecha_Hora': fechaHora,
      'Receptor': profesorId
    });

  //**Envio de regreso a la pag principal**//
  Navigator.pop(context);
  Navigator.push(context, MaterialPageRoute(builder: (context) => AgendaAsesoria(auth: auth,userId: userId,)));
}
