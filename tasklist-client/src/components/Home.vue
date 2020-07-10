<template>
  <div>
    <v-container>
      <v-row class="text-center">
        <v-col cols="12">
          <h1>
            Task Lists
            <v-btn icon v-on:click.native="newTaskList()">
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </h1>
        </v-col>

        <v-row
          justify="center"
          v-for="taskList in this.taskLists"
          :key="taskList.id"
          style="margin: 1em"
        >
          <v-col cols="12">
            <v-card min-width="30em" height="100%">
              <v-toolbar color="teal" dark>
                <v-btn icon @click="newTask(taskList)">
                  <v-icon>mdi-plus</v-icon>
                </v-btn>
                <v-btn icon>
                  <v-icon @click="deleteTaskList(taskList)">
                    mdi-close
                  </v-icon>
                </v-btn>

                <v-toolbar-title>{{ taskList.name }}</v-toolbar-title>
              </v-toolbar>
              <v-list-item
                v-for="task in tasks.filter(function(task) {
                  return task.taskList.id == taskList.id;
                })"
                :key="task.id"
              >
                <v-list-item-content>
                  <v-list-item-title
                    @click="changeStatus(task)"
                    :class="{ active: task.done }"
                  >
                    {{ task.description }}
                  </v-list-item-title>
                </v-list-item-content>

                <v-list-item-icon>
                  <!-- <v-btn icon @click="deleteTask(task)"> -->
                  <v-icon @click="deleteTask(task)">mdi-close</v-icon>
                  <!-- </v-btn> -->
                </v-list-item-icon>
              </v-list-item>
            </v-card>
          </v-col>
        </v-row>
      </v-row>
    </v-container>

    <dialog-task-list
      :dialogTaskList="dialogTaskList"
      @hide-event-tasklist="hideDialogTaskList"
      @update-tasklists="getTaskLists"
    ></dialog-task-list>
    <dialog-task
      :dialogTask="dialogTask"
      :taskList="selectedTaskList"
      @hide-event-task="hideDialogTask"
      @update-tasks="getTasks"
    >
    </dialog-task>
  </div>
</template>

<script>
import DialogTaskList from "./DialogTaskList";
import DialogTask from "./DialogTask";

export default {
  components: { DialogTaskList, DialogTask },
  data() {
    return {
      dialogTaskList: false,
      dialogTask: false,
      taskLists: [],
      tasks: [],
      selectedTaskList: {
        id: "",
        name: "",
      },
    };
  },
  created() {
    this.getTaskLists();
    this.getTasks();
  },
  methods: {
    getTaskLists() {
      let uri = "http://localhost:8080/api/tasklist/all";
      this.$http.get(uri).then(
        (response) => {
          this.taskLists = response.data;
        },
        (response) => {
          console.log("Error", response);
        }
      );
    },
    getTasks() {
      let uri = "http://localhost:8080/api/task/all";
      this.$http.get(uri).then(
        (response) => {
          this.tasks = response.data;
        },
        (response) => {
          console.log("Error", response);
        }
      );
    },
    changeStatus(task) {
      console.log("Changing status");
      let uri = "http://localhost:8080/api/task";
      this.$http.put(uri, task).then(
        (response) => {
          console.log(response);
          this.getTasks();
        },
        (response) => {
          console.log("Error", response);
        }
      );
    },
    deleteTask(task) {
      let uri = "http://localhost:8080/api/task/" + task.id;
      this.$http.delete(uri).then(
        (response) => {
          console.log("Deleted: " + response);
          this.getTasks();
        },
        (response) => {
          console.log("Error", response);
        }
      );
    },
    deleteTaskList(taskList) {
      let uri = "http://localhost:8080/api/tasklist/" + taskList.id;
      this.$http.delete(uri).then(
        (response) => {
          console.log(response);
          this.getTaskLists();
        },
        (response) => {
          console.log("Error", response);
        }
      );
    },
    newTaskList() {
      this.dialogTaskList = true;
      console.log(this.dialogTaskList);
    },
    newTask(taskList) {
      this.selectedTaskList = taskList;
      this.dialogTask = true;
      console.log(this.dialogTask);
    },
    hideDialogTaskList() {
      this.dialogTaskList = false;
      Object.keys(this.selectedTaskList).forEach(
        (key) => (this.selectedTaskList[key] = "")
      );
      console.log("Nova task list: " + this.selectedTaskList);
      console.log(this.dialogTaskList);
    },
    hideDialogTask() {
      this.dialogTask = false;
      console.log("dialog task: " + this.dialogTask);
    },
  },
};
</script>

<style type="text/css">
@import url("https://fonts.googleapis.com/css2?family=Nunito:wght@600&display=swap");

.active {
  text-decoration: line-through;
}

* {
  font-family: "Nunito", sans-serif !important;
}
</style>
