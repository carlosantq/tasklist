<template>
  <v-row justify="center">
    <v-dialog v-model="dialogTaskList" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">New Task List</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="taskList.name"
                  label="Name"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="hideDialogTaskList()"
            >Cancel</v-btn
          >
          <v-btn
            color="blue darken-1"
            text
            @click="
              hideDialogTaskList();
              newTaskList(taskList);
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
  props: ["dialogTaskList"],
  data: () => ({
    taskList: {
      id: "",
      name: "",
    },
  }),
  methods: {
    newTaskList(taskList) {
      this.taskList = taskList;
      let uri = "http://localhost:8080/api/tasklist";
      this.$http.post(uri, this.taskList).then(
        (response) => {
          console.log(response);
          Object.keys(this.taskList).forEach(
            (key) => (this.taskList[key] = "")
          );
          this.$emit("update-tasklists");
        },
        (response) => {
          console.log("Error", response);
        }
      );
    },
    hideDialogTaskList() {
      this.$emit("hide-event-tasklist");
    },
  },
};
</script>
