<template>
  <el-container style="height: 100vh">
    <el-main>
      <div class="container">
        <div class="home-container">
          <div class="d-flex justify-center align-center">
            <el-row style="margin-bottom: 10px" justify="center">
              <el-col :span="24">
                <el-dropdown>
                  <el-avatar>
                    <el-icon><UserFilled /></el-icon>
                  </el-avatar>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item>
                        <el-icon><SwitchButton /></el-icon>
                        Logout
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
                <h3>Wisdom Jere</h3>
              </el-col>
            </el-row>
          </div>
          <!-- Statistics -->
          <div
            class="d-flex justify-center align-center"
            style="margin-bottom: 20px"
          >
            <el-row :gutter="16">
              <el-col :span="8">
                <div class="statistic-card">
                  <el-statistic :value="3">
                    <template #title>
                      <div style="display: inline-flex; align-items: center">
                        Total
                      </div>
                    </template>
                  </el-statistic>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="statistic-card">
                  <el-statistic :value="1">
                    <template #title>
                      <div style="display: inline-flex; align-items: center">
                        Done
                        <el-icon style="margin-left: 4px" :size="12">
                          <Finished />
                        </el-icon>
                      </div>
                    </template>
                  </el-statistic>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="statistic-card">
                  <el-statistic :value="2" title="New transactions today">
                    <template #title>
                      <div style="display: inline-flex; align-items: center">
                        Todo                        
                        <el-icon style="margin-left: 4px" :size="12">
                          <Timer />
                        </el-icon>
                      </div>
                    </template>
                  </el-statistic>
                </div>
              </el-col>
            </el-row>
          </div>
          <el-container>
            <!-- Search Bar and Create Button -->
            <div class="search-bar-row">
              <el-input
                placeholder="Search..."
                style="margin-right: 20px; width: 350px"
              ></el-input>

              <el-button title="Create todo" type="primary" @click="createTodo">
                <el-icon><EditPen /></el-icon>
              </el-button>
            </div>
          </el-container>
          <el-scrollbar>
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
                  <el-badge
                    :type="todo.complete ? 'success' : 'primary'"
                    :value="todo.status"
                  >
                    <h3>{{ todo.details }}</h3>
                  </el-badge>
                  <p>{{ todo.description }}</p>
                  <p v-if="todo.completedDate">
                    Completed Date: {{ todo.completedDate }}
                  </p>

                  <div
                    style="
                      min-height: 60px;
                      margin-top: 10px;
                      margin-bottom: 10px;
                    "
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
          </el-scrollbar>
        </div>
      </div>
    </el-main>
  </el-container>
</template>
<style scoped>
.container {
  display: flex;
  justify-content: center;
}
.home-container {
  padding: 20px;
  min-width: 700px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.search-bar-row {
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

<script>
export default {
  data() {
    return {
      todos: [
        {
          id: 1,
          details: "Task 1",
          description: "Description 1",
          status: "Todo",
          owner: "User 1",
          createdDate: "2024-04-01",
          completedDate: null,
          complete: false,
        },
        {
          id: 2,
          details: "Task 2",
          description: "Description 2",
          status: "Completed",
          owner: "User 2",
          createdDate: "2024-04-02",
          completedDate: "2024-04-03",
          complete: true,
        },
        {
          id: 3,
          details: "Task 3",
          description: "Description 3",
          status: "Todo",
          owner: "User 3",
          createdDate: "2024-04-03",
          completedDate: null,
          complete: false,
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
