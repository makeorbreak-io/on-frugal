const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const url = 'mongodb://localhost:27017';
var db = null;

// Returns a promise
function connect() {
    return new Promise((res, rej) => MongoClient.connect(url, (err, dbArg) => {
        assert.equal(null, err);
        console.log("Connected successfully to server");
        db = dbArg.db('onFrugalDatabase');

        db.listCollections().toArray((err, collectInfos) => {
            if (collectInfos.length === 0)
                createCollections().then(() => res());
            else
                res();
        });
    }));
}

// Returns a promise
function createCollections() {
    return new Promise((resolve, reject) => db.createCollection('offer', (err, res) => {
        if (err) {
            console.log('error creating offers collection', err);
            reject(err);
            return;
        }
        console.log('User collection successfully created');

        db.createCollection('user', (err, res) => {
            if (err) {
                console.log('error creating users collection', err);
                reject(err);
                return;
            }
            console.log('User collection successfully created');

            resolve();
        });
    }));
}

// Events Database API

function search(searchQuery) {
    return new Promise((resolve, reject) => {
        let results = {offers: [], users: []};
        let deepIterate = function (obj, value) {
            for (let field in obj) {
                if (obj[field] == value) {
                    return true;
                }
                let found = false;
                if (typeof obj[field] === 'object') {
                    found = deepIterate(obj[field], value)
                    if (found) {
                        return true;
                    }
                }
            }
            return false;
        };

        // User
        db.collection('user').find({
            $where: () => {
                return deepIterate(this, searchQuery)
            }
        }).toArray((err, userResults) => {
            results.users = userResults;

            // Offers
            db.collection('offer').find({
                $where: () => {
                    return deepIterate(this, searchQuery)
                }
            }).toArray((err, offerResults) => {
                results.offers = offerResults;

                resolve(results);
            });
        });

    });
}

// @TODO need to check with duarte if he's sending all the json or is only sending a specific field
function editOffer() {
    // call find and modify
}

function createOffer(offer) {
    return new Promise((resolve, reject) => db.collection('offer').insertOne(offer, (err, res) => {
        if (err){
            console.log('Error inserting new offer', err);
            reject();
            return;
        }
        resolve();
    }));

}

function addUserToEvent(idUser, idOffer) {
    return new Promise((resolve, reject) => {
        db.collection('offer').findAndModifyCallback({
            query: {_id: idOffer},
            update: {$push: {candidates: idUser}}
        }, () => resolve())
    })
}

function allowUserToEvent(idUser, idOffer) {
    // call find and modify perhaps?
    return new Promise((resolve, reject) => {
        db.collection('offer').findAndModifyCallback({
            query: {_id: idOffer},
            update: {$push: {accepted: idUser}, $pull: {candidates: idUser}}
        }, () => resolve())
    })
}

// User Database API

function createUser(user) {
    return new Promise((resolve, reject) => db.collection('user').insertOne(user, (err, res) => {
        if (err){
            console.log('Error inserting new user', err);
            reject();
            return;
        }
        resolve();
    }));
}

function editUser() {
    // call find and modify
}

module.exports = {
    connect,
    createUser,
    editUser,
    editOffer,
    createOffer,
    search,
    addUserToEvent,
    allowUserToEvent,
};