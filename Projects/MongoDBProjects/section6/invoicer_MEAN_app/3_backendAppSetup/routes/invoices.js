const express = require('express');
const router = express.Router();

// Get All Invoices
router.get('/', (req, res) => {
  res.send('Invoices');
});

module.exports = router;
