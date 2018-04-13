const express = require('express');
const path = require('path');
const favicon = require('serve-favicon');
const logger = require('morgan');
const cookieParser = require('cookie-parser');
const bb = require('express-busboy');
const bodyParser = require('body-parser');
const fs = require('fs');

const utils = require('./utils/utils.js');
const api = require('./routes/api');

const app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));

// app.use(favicon(path.join(__dirname, 'public','images','logo.jpg')));
app.use(logger('dev'));
app.use(bodyParser.json({ limit: '5mb' }));
app.use(bodyParser.urlencoded({ limit: '5mb', extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'images')));
bb.extend(app, {
    upload: true,
    allowedPath: /./
});

app.use('/', api);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('404', {
            message: err.message,
            error: err,
            title: 'OnFrugal: 404'
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.render('404', {
        message: err.message,
        error: {},
        title: 'OnFrugal: 404',
    });
});

function logError(err) {
    console.error(err);
    const dateNow = utils.getDateTime();
    fs.writeFile('error/' + 'errorOn' + dateNow + ".txt", "Message: " + err.message + "/n/n/n" + "Stack: " + err.stack, function (err1) {
        if (err1) return console.log(err1);
        console.log("The file error has been saved!");
        process.exit(1);
    });
}

process.on('uncaughtException', (err) => {
    logError(err);
});


module.exports = app;