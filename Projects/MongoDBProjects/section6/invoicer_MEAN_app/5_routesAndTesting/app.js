const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

// Mongoose Connect
mongoose.connect('mongodb://localhost/invoicr');
const db = mongoose.connection;

// Init App
const app = express();
// Port
const port = 3000;

// Client Folder
app.use(express.static(__dirname+'/client'));
// Body Parser
app.use(bodyParser.json());

app.get('/', (req, res) => {
  res.send('Please use /api/customers or /api/invoices');
});

// Route Files
const customers = require('./routes/customers');
const invoices = require('./routes/invoices');

// Paths
app.use('/api/customers', customers);
app.use('/api/invoices', invoices);

app.listen(port, () => {
  console.log('Server Started on Port '+port);
});
