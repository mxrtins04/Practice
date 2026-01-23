// Day 1: Playing with TypeScript
// 1. Basic Types
var taskTitle = "Learn TypeScript";
var taskCount = 5;
var isComplete = false;
// Create a task
var myFirstTask = {
    id: '1',
    title: 'Build REST API',
    status: 'in-progress',
    createdAt: new Date()
};
console.log('My task:', myFirstTask);
// 3. Function with types
function createTask(title, status) {
    return {
        id: Math.random().toString(),
        title: title,
        status: status,
        createdAt: new Date()
    };
}
var newTask = createTask('Learn databases', 'todo');
console.log('New task:', newTask);
// 4. Array of tasks
var taskList = [];
taskList.push(myFirstTask);
taskList.push(newTask);
console.log('All tasks:', taskList);
console.log('Total tasks:', taskList.length);
