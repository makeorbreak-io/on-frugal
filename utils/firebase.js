const admin = require('firebase-admin');

const serviceAccount = require('../files/onfrugal-1523653506834-firebase-adminsdk-sjuy1-8f163ff365.json');

function init() {
    admin.initializeApp({
        credential: admin.credential.cert(serviceAccount),
        databaseURL: "https://onfrugal-1523653506834.firebaseio.com"
    });
}

function verifyToken(idToken) {
    return new Promise((resolve, reject) =>
        admin.auth().verifyIdToken(idToken).then(
            (decodedToken) => {
                var uid = decodedToken.uid;
                resolve(uid);
            }).catch((err) => {
                console.err(err);
                reject(err);
            })
    );
}

function retrieveInfoByToken(idToken) {
    return new Promise((resolve, reject) => verifyToken(idToken).then(val => {
        admin.database().ref('/users/' + val).then((snapshot) => {
            console.log(snapshot);
            resolve(snapshot);
        });
    }));
}

module.exports = {
    init,
};