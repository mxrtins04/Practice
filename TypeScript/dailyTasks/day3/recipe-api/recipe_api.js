"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var express_1 = require("express");
var app = (0, express_1.default)();
var recipes = [];
app.get("/", function (req, res) {
    res.send("Say Hi");
});
app.listen(5000, function () {
    console.log("Server is running on port 5000");
});
