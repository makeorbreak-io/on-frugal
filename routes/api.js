const express = require('express');
const database = require('../utils/database.js');
const router = express.Router();

router.get('/', (req, res) => {

});

router.get('/search/:searchParam', (req, res) => {
    console.log('param: ', req.params.searchParam);
    database.search(req.params.searchParam).then(val => {
        res.json(val);
    });
    /*
    res.json({
        name: 'Offer name',
        host: {
            idFirebase: '-',
            name: 'Host name',
            email: 'Host mail',
            phoneNumber: 'Host phone number',
        },
        location: {
            latitude: ,
            longitude
        }
        time: 'Offer time',
        description: 'Offer description',
        price: 'Price that the person registring is going to pay',
        spots: 'Number of spots',
        accepted: [
            {
                idFirebase: '-',
                name: 'Accepted name',
                photo: 'URL photo',
                age: 'Age',
                bio: 'Short bio',
                email: 'E-mail',
                phoneNumber: '912345678',
                facebook: 'mock',
            },
        ],
        candidates: [
            {
                idFirebase: '-',
                name: 'Accepted name',
                photo: 'URL photo',
                age: 'Age',
                bio: 'Short bio',
                email: 'E-mail',
                phoneNumber: '912345678',
                facebook: 'mock',
            },
        ],
    });
    */
});

// register user
router.post('/register', (req, res) => {
    console.log(req.headers.authorization);
    // insert into database

    // if ok, ack
    // if not, nack
    res.sendStatus(201);
});
// edit profile
router.post('/editProfile', (req, res) => {
    // @TODO need to check with duarte if he's sending all the json or is only sending a specific field
    
});
// create offer
router.post('/insertOffer', (req, res) => {
    // go to db with email of the user and see all its current offers
    // see if there's an event at the same time
    database.createOffer(req.body).then();
    // if not, create it and ack
    res.sendStatus(201);
});
// edit event
router.post('/editOffer', (req, res) => {

    // @TODO need to check with duarte if he's sending all the json or is only sending a specific field
})
// add user to event
router.get('/addUser/:event/:idFirebase', (req, res) => {
    // check if user is already in the event
    // if not, add it and ack
    database.addUserToEvent(req.params.idFirebase, req.params.event).then();
    res.sendStatus(200);
});
// allow user to event
router.get('/allowUser/:event/:idFirebase', (req, res) => {
    database.allowUserToEvent(req.params.idFirebase, req.params.event);
    res.sendStatus(200);
});

module.exports = router;