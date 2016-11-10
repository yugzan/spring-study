// for example:
// node generator.js -h 140.92.71.97 -p 49155 -f 53b27b96e4b0b41837223cab -c 53b27b96e4b0b41837223cac -n 1000
// -b 1000
var argv = require('optimist')
  .usage('Usage: $0 -h [host] -p [port] -f [fid] -c [cid] -n [num of value]' +
  ' -b [size of resource array]')
  .demand(['h', 'p', 'f', 'c', 'n', 'b'])
  .argv;
var moment = require('moment');
var request = require('request');

var fid = argv.f;
var cid = argv.c;
var numValues = argv.n;
var host = 'http://' + argv.h + ':' + argv.p + '/feeds/' + fid + '/channels/' +
  cid + '/data/insert';
console.log('tequila url: %s', host);

var baseTime = moment('2014-01-01T00:00:00.000');
var timeoutDelayMillis = 0;

function channelData(dataSize){
  var testValue = function(numFields){
    var v = {};
    for(var i = 0; i < numFields; i++){
      v['test' + i] = i;
    }
    return v;
  };

  var arr = [];
  for(var i = 0; i < dataSize; i++){
    var data = {
      at: baseTime.format(),
      value: testValue(300)
    };
    arr.push(data);
    baseTime.add('seconds', 1);
  }

  numValues = numValues - dataSize;
  return arr;
}

function requestBody(){
  var batchSize = argv.b;
  if(batchSize > numValues) batchSize = numValues;
  return {
    'kind': 'insertDataRequest',
    'payload':{
      'resources': channelData(batchSize)
    }
  };
}

function insertChannelData(){
  request.post({
    headers: {'content-type' : 'application/json'},
    url: host,
    json: requestBody(),
    timeout: 5000 // millis
  }, function(error, response, body){
    var respCode = response.statusCode;
    if(respCode !== 200){
      console.error('ERROR %s, resp code %d, host %s ', error, respCode, host);
    }

    if(numValues > 0){
      setTimeout(insertChannelData, timeoutDelayMillis);
    }else{
      var end = moment();
      console.log('done. It took %s millis', end.diff(start));
    }
  });
}

var start = moment();
setTimeout(insertChannelData, timeoutDelayMillis);
