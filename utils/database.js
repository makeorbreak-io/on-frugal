const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const distance = require('euclidean-distance');
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

function getOffersSortedByDistance(latitude, longitude) {
    return new Promise(((resolve, reject) => db.collection('offer').find()
        .toArray((err, docs) => {
            docs = docs.sort((doc1, doc2) => {
                let currentPosition = [latitude, longitude];

                let a = [doc1.location.latitude, doc1.location.longitude];
                let b = [doc2.location.latitude, doc2.location.longitude];

                return distance(currentPosition, a) - distance(currentPosition, b);
            });

            resolve(docs);
        })))
}

function getUser(idUser) {
    return new Promise((resolve, reject) => db.collection('user').find({idFirebase: idUser})
        .toArray((err, res) => {
            if (err) {
                console.log('Error getting user', err);
                reject(err);
                return;
            }

            resolve(res);
        }));
}

function getOffersByUser(idUser) {
    return new Promise((resolve, reject) =>
        db.collection('offer').find(
            {
                $or: [
                    {host: {idFirebase: idUser}},
                    {accepted: {$elemMatch: {idFirebase: idUser}}},
                    {candidates: {$elemMatch: {idFirebase: idUser}}},
                ]
            })
            .toArray((err, res) => {
                if (err) {
                    console.log('Error getting offers by user', err);
                    reject(err);
                    return;
                }

                let results = {own: [], others: []};

                res.forEach(elem => {
                    let key = elem.host.idFirebase === idUser ? 'own' : 'others';
                    results[key].push(elem);
                });

                resolve(res);
            })
    );
}

function editOffer(offer) {
    // call find and modify
    return new Promise(((resolve, reject) => db.collection('offer').findAndModifyCallback({
        // TODO check this id attribute
        query: {_id: offer.id},
        update: offer
    }, () => resolve())));
}

function createOffer(offer) {
    return new Promise((resolve, reject) => db.collection('offer').insertOne(offer, (err, res) => {
        if (err) {
            console.log('Error inserting new offer', err);
            reject(err);
            return;
        }
        console.log('Success creating offer');
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
        if (err) {
            console.log('Error inserting new user', err);
            reject(err);
            return;
        }
        console.log('Success creating user');
        resolve();
    }));
}

function editUser(user) {
    // call find and modify
    return new Promise(((resolve, reject) => db.collection('user').findAndModifyCallback({
        query: {idFirebase: user.idFirebase},
        update: user
    }, () => resolve())));
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
    getOffersByUser,
    getUser,
    getOffersSortedByDistance
};