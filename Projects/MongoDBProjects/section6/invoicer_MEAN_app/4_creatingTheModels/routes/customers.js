const express = require('express');
const router = express.Router();

// Get All customers
router.get('/', (req, res) => {
  res.send('Customers');
});

module.exports = router;
