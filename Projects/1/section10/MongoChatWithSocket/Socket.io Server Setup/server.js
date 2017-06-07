const mongo = require('mongodb').MongoClient;
const client = require('socket.io').listen(3000).sockets;

mongo.connect('mongodb://127.0.0.1/mongochat', (err, db) => {
  if(err){
    throw err;
  }
  console.log('Server Started & MongoDB Connected...');
  client.on('connection', (socket) => {

  });
});
