import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/services.dart';
import 'dart:async';
import 'dart:io';
import 'dart:convert';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  static const platform = const MethodChannel('PayUmoney');

  @override
  void initState() {
    super.initState();
    // Setting up the payment details
  }

  // Get battery level.

  void _initPayment(dynamic res) async {
    String paymentStatus;
    try {
      final String result = await platform.invokeMethod("initPayment", res);
      paymentStatus = '$result';
    } on PlatformException catch (e) {
      paymentStatus = "Payment Failed : '${e.message}'";
    }
    print(paymentStatus);
  }

  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Pay Test',
        theme: ThemeData(
          primarySwatch: Colors.blue,
          visualDensity: VisualDensity.adaptivePlatformDensity,
        ),
        home: Scaffold(
          appBar: AppBar(
            title: Text("Pay Test"),
          ),
          body: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                RaisedButton(
                  onPressed: () async {
                    var data = {"shipping_address": 5, "shop_pickup": false};
                    var headers = {
                      'Content-Type': 'application/json; charset=UTF-8',
                      "auth-token":
                          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3RVc2VyNEBkZWx4LmluIiwiZXhwIjoxNjE5NjE0MTAxfQ.vY4K1AnmovMp_p_k2fMFpH4M4SMQfXp9iDdG_OUrDgM',
                    };

                    var url = 'http://23be6e9d063f.ngrok.io/v1/payment/pay';
                    var response = await http.post(
                      url,
                      headers: headers,
                      body: jsonEncode(data),
                    );

                    print('res - ' +
                        json.decode(response.body.toString()).toString());

                    final res =
                        json.decode(response.body.toString())['payload'];

                    if (response.statusCode == 200) {
                      print("initPayment called");
                      _initPayment(res);
                    } else {
                      print("Error - " + res.message);
                    }
                  },
                  textColor: Colors.white,
                  color: Colors.white,
                  padding: const EdgeInsets.all(0),
                  shape: new RoundedRectangleBorder(
                    borderRadius: new BorderRadius.circular(5.0),
                  ),
                  child: Container(
                    width: double.infinity,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(5.0),
                      gradient: LinearGradient(
                        colors: [
                          Colors.deepPurple,
                          Colors.greenAccent,
                        ],
                      ),
                    ),
                    padding: const EdgeInsets.fromLTRB(20.0, 12, 20, 12),
                    child: const Text(
                      'Proceed To Payment',
                      style: TextStyle(fontSize: 28),
                      textAlign: TextAlign.center,
                    ),
                  ),
                ),
              ],
            ),
          ),
          // This trailing comma makes auto-formatting nicer for build methods.
        ));
  }
}
