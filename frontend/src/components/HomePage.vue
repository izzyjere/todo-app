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
                      <el-dropdown-item @click="signOut">
                        <el-icon><SwitchButton /></el-icon>
                        Logout
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
                <h3>{{ user?.fullName ?? "Unknown" }}</h3>
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
                  <el-statistic :value="statistics.total">
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
                  <el-statistic :value="statistics.completed">
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
                  <el-statistic
                    :value="statistics.pending"
                    title="New transactions today"
                  >
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
                v-model="searchTerm"
                style="width: 300px; margin-right: 30px"
                @input="searchTodos"
                clearable
                placeholder="Search for todos here"
              >
                <template #prefix>
                  <el-icon class="el-input__icon"><search /></el-icon>
                </template>
              </el-input>
              <el-button title="Create todo" type="primary" @click="createTodo">
                <el-icon><EditPen /></el-icon>
              </el-button>
            </div>
          </el-container>
          <el-scrollbar>
            <!-- Timeline of todos -->
            <el-timeline style="max-width: 500px; margin-top: 10px">
              <el-timeline-item
                v-for="todo in todos"
                :key="todo.id"
                :timestamp="todo.createdDate"
                placement="top"
              >
                <el-card
                  class="timeline-card"
                  style="min-height: 200px; min-width: 250px"
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
                      @click="() => editTodo(todo)"
                      style="font-size: x-large; margin-right: 20px"
                      ><Edit
                    /></el-icon>
                    <el-icon
                      color="green"
                      @click="() => markTodoAsComplete(todo)"
                      v-if="!todo.completedDate"
                      title="Mark as done"
                      style="font-size: x-large; margin-right: 20px"
                      ><Finished
                    /></el-icon>
                    <el-icon
                      @click="() => removeTodo(todo)"
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
  <el-dialog
    v-model="dialogFormVisible"
    :title="todoRequest.id === 0 ? 'Create Todo' : 'Edit Todo'"
    width="500"
  >
    <el-form ref="todoForm" :rules="todoRules" :model="todoRequest">
      <el-form-item label="Todo Details" prop="details" :label-width="100">
        <el-input v-model="todoRequest.details" autocomplete="on" />
      </el-form-item>
      <el-form-item label="Description" prop="description" :label-width="100">
        <el-input v-model="todoRequest.description" autocomplete="on" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideForm">Cancel</el-button>
        <el-button @click="submitTodo" :loading="loading" type="primary">
          Submit
        </el-button>
      </div>
    </template>
  </el-dialog>
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
import { mapActions, mapGetters } from "vuex";
import { ElNotification } from "element-plus";

export default {
  data() {
    return {
      dialogFormVisible: false,
      loading: false,
      searchTerm: "",
      todoRequest: {
        id: 0,
        details: "",
        description: "",
      },
      todoRules: {
        details: [
          {
            required: true,
            message: "Please enter valid task details",
            trigger: "blur",
          },
        ],
        description: [
          {
            required: true,
            message: "Please provide a description",
            trigger: "blur",
          },
        ],
      },
    };
  },
  computed: {
    ...mapGetters("auth", ["user"]),
    ...mapGetters("todo", ["todos"]),
    statistics() {
      return {
        total: this.todos.length,
        completed: this.getComplete(),
        pending: this.getPending(),
      };
    },
  },
  methods: {
    ...mapActions("todo", [
      "fetchTodos",
      "saveTodo",
      "deleteTodo",
      "completeTodo",
    ]),
    ...mapActions("auth", ["logout"]),
    hideForm() {
      this.dialogFormVisible = false;
    },
    getPending() {
      if (this.todos.length) {
        return this.todos.filter((t) => !t.complete).length;
      } else {
        return 0;
      }
    },
    getComplete() {
      if (this.todos.length) {
        return this.todos.filter((t) => t.complete).length;
      } else {
        return 0;
      }
    },
    refreshModel() {
      this.todoRequest = {
        id: 0,
        details: "",
        description: "",
      };
    },
    signOut() {
      this.logout();
      this.$router.push("/");
    },
    searchTodos() {
      this.fetchTodos(this.searchTerm);
    },
    createTodo() {
      this.dialogFormVisible = true;
      console.log("Creating todo...");
    },
    editTodo(todo) {
      this.todoRequest = {
        id: todo.id,
        details: todo.details,
        description: todo.description,
      };
      this.dialogFormVisible = true;
      console.log("Editing todo:", todo);
    },
    markTodoAsComplete(todo) {
      this.completeTodo(todo.id)
        .then((result) => {
          if (!result) {
            ElNotification({
              title: "Error",
              message: `Failed to complete todo try again later.`,
              type: "error",
            });
            return;
          }
          ElNotification({
            title: "Completed",
            message: `Todo ${result.details} successfully marked as.`,
            type: "success",
          });
          this.refreshModel();
        })
        .catch((error) => {
          console.error(error);
          ElNotification({
            title: "Error",
            message: `Failed to complete todo try again later.`,
            type: "error",
          });
        });
      console.log("Completing todo:", todo);
    },
    removeTodo(todo) {
      this.deleteTodo(todo.id)
        .then((result) => {
          console.log(result);
          ElNotification({
            title: "Delete",
            message: `Todo ${todo.details} successfully removed.`,
            type: "success",
          });
          this.refreshModel();
        })
        .catch((error) => {
          console.error(error);
          ElNotification({
            title: "Error",
            message: `Failed to delete todo try again later.`,
            type: "error",
          });
        });
      console.log("Deleting todo:", todo);
    },
    submitTodo() {
      this.$refs.todoForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.saveTodo(this.todoRequest)
            .then((result) => {
              if (result) {
                ElNotification({
                  title: "Save",
                  message: `Todo ${result.details} successfully saved.`,
                  type: "success",
                });
                this.dialogFormVisible = false;
                this.refreshModel();
              } else {
                ElNotification({
                  title: "Error",
                  message: `Failed to save todo try again later.`,
                  type: "error",
                });
              }
            })
            .catch((error) => {
              console.error(error);
              ElNotification({
                title: "Error",
                message: `Failed to save todo try again later.`,
                type: "error",
              });
            })
            .finally(() => {
              this.loading = false;
            });
        }
      });
    },
  },
  created() {
    this.fetchTodos();
  },
};
</script>
