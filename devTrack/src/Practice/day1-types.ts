let taskTitle: string = "Learn TypeScript";
let taskCount: number = 5;
let isComplete: boolean = false;

interface Task {
  id: string;
  title: string;
  status: 'todo' | 'in-progress' | 'done';
  createdAt: Date;
}

const myFirstTask: Task = {
  id: '1',
  title: 'Build REST API',
  status: 'in-progress',
  createdAt: new Date()
};

console.log('My task:', myFirstTask);

function createTask(title: string, status: 'todo' | 'in-progress' | 'done'): Task {
  return {
    id: Math.random().toString(),
    title: title,
    status: status,
    createdAt: new Date()
  };
}

const newTask = createTask('Learn databases', 'todo');
console.log('New task:', newTask);

const taskList: Task[] = [];
taskList.push(myFirstTask);
taskList.push(newTask);

console.log('All tasks:', taskList);
console.log('Total tasks:', taskList.length);