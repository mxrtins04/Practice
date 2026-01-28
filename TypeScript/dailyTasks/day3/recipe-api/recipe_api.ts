const express = require('express');
import { Request, Response } from 'express';
const app = express();

app.use(express.json());
interface Recipe {
    id : String;
    title : string;
    ingredients : string[];
    instructions : string;
}


let recipes : Recipe[] = []

app.get("/", (req: Request, res: Response) => {
    res.send("Hiii");
});

app.listen(5000, () => {
    console.log("Server is running on port 5000");
});

app.post("/recipes", (req: Request, res: Response) => {
    const {title, ingredients, instructions } : Recipe = req.body;

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

    const id: String = Date.now().toString();
    const newRecipe : Recipe = {id, title, ingredients, instructions};
    recipes.push(newRecipe);
    res.status(201).json(newRecipe);
});

app.get("/recipes", (req: Request, res: Response) => {
    res.json(recipes);
});

app.get("/recipes/:id", (req: Request, res: Response) => {
    req.params= {"id": "1769641724631"}
    const id = req.params.id;
    const recipe = recipes.find(r => r.id === id);
    res.json(recipe);
});

app.delete("rec")