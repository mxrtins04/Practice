import express from 'express'
const app = express();

app.use(express.json());
interface Recipe {
    id : number;
    title : string;
    ingredients : string[];
    instructions : string;
}

let recipes : Recipe[] = []

app.get("/", (req, res) => {
    res.json({"message": "Say Hi"});
});

app.listen(5000, () => {
    console.log("Server is running on port 5000");
});