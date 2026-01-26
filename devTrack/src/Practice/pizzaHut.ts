import PromptSync from 'prompt-sync';
const prompt = PromptSync();


let orderId = 0;

type Order = {
    id: number;
    pizza : Pizza;
    status: 'pending' | 'completed';
}


type Pizza = {
    name : "pepperoni" | "aboki" | "lekki";
    price : number | undefined;
}

let orderHistory: Order[] = [];

function orderPizza(name : string): Order {
    const order: Order = {
        id: orderId++,
        pizza: {name: validatePizzaName(name), price: getPizzaPrice(name)},
        status: 'pending'
    };
    orderHistory.push(order);
    return order;
}

function validatePizzaName(name : string){
    if( name === "pepperoni" || name === "aboki" || name === "lekki")
        return name;
    throw new Error("Invalid pizza name");
}
function getPizzaPrice(name : string){
    switch(name){
        case "pepperoni":
            return 7000;
        case "aboki":
            return 800;
        case "lekki":
            return 12000;
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

function displayMenu(): void {
    console.log('\n=== Welcome to Pizza Hut ===');
    console.log('\n Available Pizzas:');
    console.log('1. Pepperoni - ₦7,000');
    console.log('2. Aboki - ₦800');
    console.log('3. Lekki - ₦12,000');
    console.log('4. View Order History');
    console.log('5. Complete Order');
    console.log('6. Exit');
    console.log('\n========================');
}

function getUserChoice(): string {
    const choice = prompt('\nEnter your choice (1-6): ');
    return choice || '';
}

function handleOrderChoice(choice: string): void {
    switch(choice) {
        case '1':
            handlePizzaOrder('pepperoni');
            break;
        case '2':
            handlePizzaOrder('aboki');
            break;
        case '3':
            handlePizzaOrder('lekki');
            break;
        case '4':
            displayOrderHistory();
            break;
        case '5':
            handleCompleteOrder();
            break;
        case '6':
            console.log('Thank you for visiting Pizza Hut! ');
            return;
        default:
            console.log('Invalid choice. Please try again.');
    }
    
    if (choice !== '6') {
        displayMenu();
        const nextChoice = getUserChoice();
        handleOrderChoice(nextChoice);
    }
}

function handlePizzaOrder(pizzaName: string): void {
    try {
        const order = orderPizza(pizzaName);
        console.log(`\nOrder placed successfully!`);
        console.log(`Order ID: ${order.id}`);
        console.log(`Pizza: ${order.pizza.name}`);
        console.log(`Price: ₦${order.pizza.price}`);
        console.log(`Status: ${order.status}`);
    } catch (error) {
        console.log(` Error: ${error instanceof Error ? error.message : 'Unknown error'}`);
    }
}

function displayOrderHistory(): void {
    console.log('\n Order History:');
    if (orderHistory.length === 0) {
        console.log('No orders yet.');
    } else {
        orderHistory.forEach(order => {
            console.log(`ID: ${order.id} | Pizza: ${order.pizza.name} | Price: ₦${order.pizza.price} | Status: ${order.status}`);
        });
    }
}

function handleCompleteOrder(): void {
    const orderIdStr = prompt('Enter order ID to complete: ');
    if (orderIdStr) {
        const orderId = parseInt(orderIdStr);
        const result = completeOrder(orderId);
        if (typeof result === 'object') {
            console.log(`\nOrder ${result.id} marked as completed!`);
        } else {
            console.log(` ${result}`);
        }
    }
}


function startPizzaHut(): void {
    displayMenu();
    const choice = getUserChoice();
    handleOrderChoice(choice);
}

startPizzaHut();
