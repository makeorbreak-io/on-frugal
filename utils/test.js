const db = require('../utils/database.js');
const async = require('async');

var users = [{
    idFirebase: 1,
    name: 'Name1',
    photo: 'URL photo',
    age: 21,
    bio: '1111111111111111111111111111111111111111111111111111111111111111111',
    email: '1@gmail.com',
    phoneNumber: '912345678',
    facebook: 'facebook.com/1',
},
{
    idFirebase: 2,
    name: 'Name2',
    photo: 'URL photo',
    age: 22,
    bio: '222222222222222222222222222222222222222222222222222222222222222222',
    email: '2@gmail.com',
    phoneNumber: '912345678',
    facebook: 'facebook.com/2',
},
{
    idFirebase: 3,
    name: 'Name3',
    photo: 'URL photo',
    age: 23,
    bio: '33333333333333333333333333333333333333333333333333333333333333333333',
    email: '3@gmail.com',
    phoneNumber: '912345678',
    facebook: 'facebook.com/3',
},
{
    idFirebase: 4,
    name: 'Name4',
    photo: 'URL photo',
    age: 24,
    bio: '44444444444444444444444444444444444444444444444444444444444444444444',
    email: '4@gmail.com',
    phoneNumber: '912345678',
    facebook: 'facebook.com/4',
},
{
    idFirebase: 5,
    name: 'Name5',
    photo: 'URL photo',
    age: 25,
    bio: '555555555555555555555555555555555555555555555555555555555555555555555',
    email: '5@gmail.com',
    phoneNumber: '912345678',
    facebook: 'facebook.com/5',
}]

var offers = [{
    name: 'Offer1',
    host: {
        idFirebase: 1,
        name: 'Host1',
        email: 'Host mail1',
        phoneNumber: 918273645,
    },
    location: 'Porto',
    time: '2018-04-16T21:26:17Z',
    description: 'Offer description',
    price: 'Price that the person registring is going to pay',
    spots: 'Number of spots',
    accepted: [
        {
            idFirebase: 2,
        },
    ],
    candidates: [
        {
            idFirebase: 3,
        },
        {
            idFirebase: 4,
        },
    ],
},
{
    name: 'Offer2',
    host: {
        idFirebase: 1,
        name: 'Host1',
        email: 'Host mail1',
        phoneNumber: 918273645,
    },
    location: 'Porto',
    time: '2018-04-17T21:26:17Z',
    description: 'Offer description',
    price: 'Price that the person registring is going to pay',
    spots: 'Number of spots',
    accepted: [
        {
            idFirebase: 3,
        },
    ],
    candidates: [
        {
            idFirebase: 2,
        },
        {
            idFirebase: 5,
        },
    ],
}];

function populateDB() {
    db.connect().then(val => {
        async.each(users, (user, cb) => {
            db.createUser(user).then(() => {
                cb();
            });
        }, (err) => {
            console.log('All users');
        });
        async.each(offers, (offer, cb) => {
            db.createOffer(offer).then(() => {
                cb();
            });
        }, (err) => {
            console.log('All offers');
        });
    });
}

populateDB();