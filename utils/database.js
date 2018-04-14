const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const url = 'mongodb://localhost:27017';
var db = null;

// Returns a promise
function connect() {
    return MongoClient.connect(url, (err, dbArg) => {
        assert.equal(null, err);
        console.log("Connected successfully to server");
        db = dbArg.db('onFrugalDatabase');
        createCollections();
    });
}

function createCollections() {
    db.createCollection('offer', (err, res) => {
        if (err) {
            console.log('error creating offers collection', err);
            return;
        }
        console.log('User collection successfully created');
        db.createCollection('user', (err, res) => {
            if (err) {
                console.log('error creating users collection', err);
                return;
            }
            console.log('User collection successfully created');
        });
    });
}

// Events Database API

function search(searchQuery) {

}   

// @TODO need to check with duarte if he's sending all the json or is only sending a specific field
function editOffer() {
    // call find and modify
}

function createOffer(offer) {
    db.collection('offer').insertOne(offer, (err, res) => {
        if (err) console.log('Error inserting new offer', err);
    });
}

function addUserToEvent(user, idOffer) {

}

function allowUserToEvent(idUser, idOffer) {
    // call find and modify perhaps?
}

// User Database API

function createUser(user) {
    db.collection('user').insertOne(user, (err, res) => {
        if (err) console.log('Error inserting new offer', err);
    });
}

function editUser() {
    // call find and modify
}

/*
// Use connect method to connect to the server
MongoClient.connect(url, function(err, db) {
    assert.equal(null, err);
    console.log("Connected successfully to server");

    removeAllDocuments(db, () => findDocuments(db, () => db.close()))

    //insertDocuments(db, () => findDocuments(db, () => db.close()))
});

var removeAllDocuments = function(db, callback) {
    // Get the documents collection
    var collection = db.collection('documents');

    collection.removeMany({}, (err, result) => {
        callback(result)
    })
}

var insertDocuments = function(db, callback) {
    // Get the documents collection
    var collection = db.collection('documents');
    // Insert some documents
    collection.insertMany([
        {a : 1}, {a : 2}, {a : 3}
    ], function(err, result) {
        assert.equal(err, null);
        assert.equal(3, result.result.n);
        assert.equal(3, result.ops.length);
        console.log("Inserted 3 documents into the collection");
        callback(result);
    });
}

var findDocuments = function(db, callback) {
    // Get the documents collection
    var collection = db.collection('documents');
    // Find some documents
    collection.find({}).toArray(function(err, docs) {
        assert.equal(err, null);
        console.log("Found the following records");
        console.log(docs)
        callback(docs);
    });
}*/

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