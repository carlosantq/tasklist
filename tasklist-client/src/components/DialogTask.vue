<template>
  <v-row justify="center">
    <v-dialog v-model="dialogTask" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline"
            >New Task for {{ taskList ? taskList.name : "" }}</span
          >
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="task.description"
                  label="Description"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="hideDialogTaskAndClean()"
            >Cancel</v-btn
          >
          <v-btn
            color="blue darken-1"
            text
            @click="
              hideDialogTask();
              newTask(task);
            "
            >Save</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
export default {
  props: ["dialogTask", "taskList"],
  data: () => ({
    task: {
      id: "",
      description: "",
      done: false,
      taskList: {
        id: "",
        name: "",
      },
    },
  }),
  methods: {
    newTask(task) {
      console.log(this.taskList);
      this.task = task;
      this.task.taskList = this.taskList;
      let uri = "http://localhost:8080/api/task";
      this.$http.post(uri, this.task).then(
        () => {
          Object.keys(this.task).forEach((key) => (this.task[key] = ""));
          this.$emit("update-tasks");
        },
        (response) => {
          console.log("Error", response);
        }
      );
    },
    hideDialogTaskAndClean() {
      Object.keys(this.task).forEach((key) => (this.task[key] = ""));
      this.$emit("hide-event-task");
    },
    hideDialogTask() {
      this.$emit("hide-event-task");
    },
  },
};
</script>
