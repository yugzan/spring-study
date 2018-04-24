// var server_port = process.env.OPENSHIFT_NODEJS_PORT || 8080
// var server_ip_address = process.env.OPENSHIFT_NODEJS_IP || '127.0.0.1'

const express = require('express');
const bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


var user = {
    "user_1": {
        "id": 1,
        "name": "John",
        "password": "password"
    }
}
var error_message = function(message) {
    return {
        "error": {
            "reason": message||"unknow"
        }
    }
}


var authorization = function(req, res, callback){
  // Grab the "Authorization" header.
  var auth = req.get("authorization");

  // On the first request, the "Authorization" header won't exist, so we'll set a Response
  // header that prompts the browser to ask for a username and password.
  if (!auth) {
    res.set("WWW-Authenticate", "Basic realm=\"Authorization Required\"");
    // If the user cancels the dialog, or enters the password wrong too many times,
    // show the Access Restricted error message.
    return res.status(401).send("Authorization Required");
  } else {
    // If the user enters a username and password, the browser re-requests the route
    // and includes a Base64 string of those credentials.
    var credentials = new Buffer(auth.split(" ").pop(), "base64").toString("ascii").split(":");
    if (credentials[0] === "username" && credentials[1] === "password") {
      // The username and password are correct, so the user is authorized.
      // return res.send("Access Granted!");
      return callback(res);
    } else {
      // The user typed in the username or password wrong.
      return res.status(401).send("Access Denied (incorrect credentials)");
    }
  }
}
app.get('/', function(req, res) {

    authorization(req, res, function (r) {
              r.status(200).send( { 'hellow':'world'} );
              console.log(JSON.stringify( { 'hellow':'world'} ));
    });
});
app.get('/auth', function(req, res) {

    authorization(req, res, function (r) {
              r.status(200).send( user );
              console.log(JSON.stringify(user));
    });
});

app.get('/400', function(req, res) {

    authorization(req, res, function (r) {
              r.status(400).send( error_message(400) );
              console.log(JSON.stringify(error_message(400)));
    });
});
app.get('/403', function(req, res) {

    authorization(req, res, function (r) {
              r.status(403).send( error_message(403) );
              console.log(JSON.stringify(error_message(403) ));
    });
});
app.get('/500', function(req, res) {

    authorization(req, res, function (r) {
              r.status(500).send( error_message(500) );
              console.log(JSON.stringify( error_message(500) ));
    });
});

app.get('*', function(req, res){
  res.status(404).send( error_message( 'what???' ) );
  console.log(JSON.stringify( error_message('what???') ));
});


var server = app.listen(9000, function() {
    var host = server.address().address
    var port = server.address().port

    console.log("Listening on http://%s:%s", host, port);
})