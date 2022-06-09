<template>
  <b-btn
    class="white--text"
    v-on="$listeners"
    v-bind="$attrs"
    v-if="!isMobile()"
  >
    <b-icon small>
      {{ iconName }}
    </b-icon>
    <span style="width: 5px" v-if="btnName"></span>
    {{ btnName }}
  </b-btn>
  <b-btn class="white--text" v-on="$listeners" v-bind="$attrs" v-else>
    <b-icon small>
      {{ iconName }}
    </b-icon>
  </b-btn>
</template>

<script>
export default {
  props: {
    iconName: String,
    btnName: String,
  },
}
</script>

<style></style>
