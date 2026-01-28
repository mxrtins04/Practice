"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var express = require('express');
var app = express();
app.use(express.json());
var recipes = [];
app.get("/", function (req, res) {
    res.send("Hiii");
});
app.listen(5000, function () {
    console.log("Server is running on port 5000");
});
app.post("/recipes", function (req, res) {
    var _a = req.body, title = _a.title, ingredients = _a.ingredients, instructions = _a.instructions;
    if (!title || !instructions) {
        return res.status(400).json({
            error: 'Missing required fields: name and instructions are required'
        });
    }
    if (!ingredients || !Array.isArray(ingredients) || ingredients.length === 0) {
        return res.status(400).json({
            error: 'Ingredients must be a non-empty array'
        });
    }
    var id = Date.now().toString();
    var newRecipe = { id: id, title: title, ingredients: ingredients, instructions: instructions };
    recipes.push(newRecipe);
    res.status(201).json(newRecipe);
});
app.get("/recipes", function (req, res) {
    res.json(recipes);
});
app.get("/recipes/:id", function (req, res) {
    var id = req.params.id;
    var recipe = recipes.find(function (r) { return r.id === id; });
    res.json(recipe);
});
app.delete("/recipes/:id", function (req, res) {
    var id = req.params.id;
    recipes = recipes.filter(function (r) { return r.id !== id; });
    res.json({ message: "Recipe with id ".concat(id, " deleted successfully") });
});
