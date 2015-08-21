var MongoClient = require('mongodb').MongoClient;
var Server = require('mongodb').Server;
var util = require('util');
var moment = require('moment');

var data =
{
   "username" : "user",
   "password" : "password",
   "roles" : [
       "ROLE_ADMIN"
   ]
};

var mongoClient = new MongoClient(new Server('localhost', 27017));
var DB = 'test';
var DB_COLLECTION = 'users';

mongoClient.open(function(err, mongoClient) {
  var testDb = mongoClient.db(DB);
  testDb.collection(DB_COLLECTION).insert( data ,
    function(err, records){
      if(err){
        console.log('error occurred while updating a client, data `%s`, err `%s`',
          util.inspect(data), err);
      }
      mongoClient.close();
    }
  );
});
//http://stackoverflow.com/questions/26626186/object-mongoclient-has-no-method-open
