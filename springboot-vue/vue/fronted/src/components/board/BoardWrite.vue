<template>
	<b-container>
		<b-card elevation="10" outlined width="100%" class="mx-auto">
			<b-card-title>
				<span class="mr-2">Write</span>
			</b-card-title>
			<b-card-text>
				<b-form-input
					label="Title"
					v-model="title"
				></b-form-input>
				<Editor ref="editor" />
			</b-card-text>
				<b-button
					@click="save"
					variant="success"
					btnName="Save"
				>저장하기</b-button>
				<b-button
					@click="movePage('/list')"
					color="grey darken-1"
					btnName="Back"
				>뒤로가기</b-button>
		</b-card>
	</b-container>
</template>

<script>
import Editor from "../common/Editor";
import {insertBoardAPI} from "../../../api";

export default {
	components: {
		Editor,
	},
	data: () => ({
		rules: [
			value => !!value || 'Required.',
			value => (value && value.length >= 3) || 'Min 3 characters',
		],
		title: '',
	}),
	methods: {
    save() {
			var content = this.$refs.editor.getContent()
			var title = this.title

      insertBoardAPI({
        params: {
          title: title,
          content: content,
        },
      })
        .then(response => {
          if (response.data > 0) {
            this.movePage('/detail?docNo=' + response.data)
          }
        })
        .catch(error => {
          console.log(error)
        })
		},
	},
}
</script>

<style></style>
