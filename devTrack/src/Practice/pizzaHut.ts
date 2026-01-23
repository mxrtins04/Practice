
let orderId = 0;

type Order = {
    id: number;
    pizza : Pizza;
    status: 'pending' | 'completed';
}


type Pizza = {
    name : "pepperoni" | "aboki" | "lekki";
    price : number;
}

let orderHistory: Order[] = [];

function orderPizza(name : string): Order {
    const order: Order = {
        id: orderId++,
        pizza: pizza,
        status: 'pending'
    };
    orderHistory.push(order);
    return order;
}


function getPizzaPrice(name : string){
    switch(name){
        case "pepperoni":
            return 7000;
        case "aboki":
            return 800;
        case "lekki":
            return 12000;
        default:
            return 0;
    }
}

function completeOrder(id : number){
    const order = orderHistory.find(order => order.id === id);
    if(order){
        order.status = 'completed';
        return order
    }
    return "Order not found";
}

const 