<template>
  <v-app>
    <v-main>
      <Home />
    </v-main>
  </v-app>
</template>

<script>
import Home from "./components/Home";

export default {
  name: "TaskListClient",

  components: {
    Home,
  },

  data: () => ({
    //
  }),
};
</script>
