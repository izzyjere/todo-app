<template>
  <div class="container">
    <div class="home-container">
      <!-- Statistics about todos -->
      <el-row class="statistics-row">
        <el-col :span="8">
          <el-statistic title="Total Todos" :value="todos.length" />
        </el-col>
        <el-col :span="8">
          <el-statistic
            title="Completed Todos"
            :value="completedTodos.length"
          />
        </el-col>
        <el-col :span="8">
          <el-statistic title="Pending Todo" :value="0">
            <template #suffix>
              <el-icon style="vertical-align: -0.125em">
                <Timer />
              </el-icon>
            </template>
          </el-statistic>
        </el-col>
      </el-row>

      <!-- Search bar and create todo button -->
      <div class="header">
        <el-input placeholder="Search..." style="margin-right: 20px"></el-input>
        <el-button type="primary" @click="createTodo">Create Todo</el-button>
      </div>

      <!-- Timeline of todos -->
      <el-timeline style="max-width: 400px; margin-top: 10px">
        <el-timeline-item
          v-for="todo in todos"
          :key="todo.id"
          :timestamp="todo.createdDate"
          placement="top"
        >
          <el-card
            class="timeline-card"
            style="max-height: 200px"
            :class="{ completed: todo.completedDate }"
          >
            <h3>{{ todo.details }}</h3>
            <p>{{ todo.description }}</p>
            <p v-if="todo.completedDate">
              Completed Date: {{ todo.completedDate }}
            </p>

            <div
              style="min-height: 60px; margin-top: 10px; margin-bottom: 10px"
              class="flex items-center is-align-bottom"
            >
              <el-icon
                color="brown"
                title="Edit todo"
                style="font-size: x-large; margin-right: 20px"
                ><Edit
              /></el-icon>
              <el-icon
                color="green"
                v-if="!todo.completedDate"
                title="Mark as done"
                style="font-size: x-large; margin-right: 20px"
                ><Finished
              /></el-icon>
              <el-icon
                color="red"
                title="delete todo"
                style="font-size: x-large"
                ><Delete
              /></el-icon>
            </div>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      todos: [
        {
          id: 1,
          details: "Task 1",
          description: "Description 1",
          status: "TODO",
          owner: "User 1",
          createdDate: "2024-04-01",
          completedDate: null,
        },
        {
          id: 2,
          details: "Task 2",
          description: "Description 2",
          status: "TODO",
          owner: "User 2",
          createdDate: "2024-04-02",
          completedDate: "2024-04-03",
        },
        {
          id: 3,
          details: "Task 3",
          description: "Description 3",
          status: "TODO",
          owner: "User 3",
          createdDate: "2024-04-03",
          completedDate: null,
        },
      ],
    };
  },
  computed: {
    completedTodos() {
      return this.todos.filter((todo) => todo.completedDate !== null);
    },
    incompleteTodos() {
      return this.todos.filter((todo) => todo.completedDate === null);
    },
  },
  methods: {
    createTodo() {
      // Logic for creating a new todo
      console.log("Creating todo...");
    },
    editTodo(todo) {
      // Logic for editing a todo
      console.log("Editing todo:", todo);
    },
    completeTodo(todo) {
      // Logic for completing a todo
      console.log("Completing todo:", todo);
    },
    deleteTodo(todo) {
      // Logic for deleting a todo
      console.log("Deleting todo:", todo);
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
}

.home-container {
  width: 600px;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.timeline-card {
  padding: 10px;
}

.timeline-card.completed {
  background-color: #f0f9eb; /* Light green background for completed todos */
}

.statistics-row {
  margin-bottom: 20px;
}

.el-col {
  text-align: center;
}
</style>
