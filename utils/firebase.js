const admin = require('firebase-admin');

const serviceAccount = require('../files/onfrugal-1523653506834-firebase-adminsdk-sjuy1-8f163ff365.json');

function init() {
    admin.initializeApp({
        credential: admin.credential.cert(serviceAccount),
        databaseURL: "https://onfrugal-1523653506834.firebaseio.com"
    });
}

function verifyToken(idToken) {
    admin.auth().verifyIdToken(idToken).then(
        (decodedToken) => {
            var uid = decodedToken.uid;
        }).catch((err) => {
            console.log(err);
        });
}

module.exports = {
    init,
};