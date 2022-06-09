<template>
	<b-container>
		<b-card elevation="10" outlined width="100%" class="mx-auto">
			<b-card-title>
				<span class="mr-2">Edit</span>
			</b-card-title>
			<b-card-text>
				<b-textarea
					label="Title"
					:rules="rules"
					:counter="100"
					v-model="title"
				></b-textarea>
				<Editor ref="editor" />
			</b-card-text>
			<b-card>

				<b-button
					@click="edit"
					variant="warning"
					iconName="mdi-pencil"
					btnName="Edit"
				></b-button>
				<b-button
					@click="movePage('/detail?docNo=' + docNo)"
					color="grey darken-1"
					iconName="mdi-arrow-left"
					btnName="Back"
				>뒤록가기</b-button>
			</b-card>
		</b-card>
	</b-container>
</template>

<script>
import Editor from "../common/Editor";
import {getBoardDetailAPI, updateBoardAPI} from "../../../api";

export default {
	components: {
		Editor,
	},
	data: () => ({
		rules: [
			value => !!value || 'Required.',
			value => (value && value.length >= 3) || 'Min 3 characters',
		],
		docNo: '',
		title: '',
		writer: '',
	}),
	mounted() {
		getBoardDetailAPI({
			params: {
				docNo: this.$route.query.docNo,
			},
		})
			.then(response => {
				this.docNo = response.data.docNo
				this.title = response.data.title
				this.$refs.editor.setContent(response.data.content)
				// this.writer = response.data.writer
			})
			.catch(error => {
				console.log(error)
			})
	},
	methods: {
		async edit() {
			var docNo = this.docNo
			var title = this.title
			var content = this.$refs.editor.getContent()

			let res = await this.confirmDialog(
				'Confirm Edit',
				'Do you want to save it?',
			)
			if (res) {
				updateBoardAPI({
					params: {
						docNo: docNo,
						title: title,
						content: content,
					},
				})
					.then(response => {
						if (response.data > 0) {

							this.movePage('/detail?docNo=' + docNo)
						}
					})
					.catch(error => {
						console.log(error)
					})
			}
		},
	},
}
</script>

<style></style>
