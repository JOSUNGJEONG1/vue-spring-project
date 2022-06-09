<template>
	<b-container>
		<b-card elevation="10" outlined width="100%" class="mx-auto">
			<b-card-title>
				<span class="mr-2">Detail</span>
			</b-card-title>
			<b-card-text>
				<b-row>
					<b-col>
						<b-textarea label="Title" readonly :value="title" />
					</b-col>
				</b-row>
				<b-row>
					<b-col>
						<b-textarea label="Writer" readonly  :value="writer" />
					</b-col>
					<b-col>
						<b-textarea
							label="Register Time"
							readonly
							dense
							:value="regDttm"
						/>
					</b-col>
					<b-col>
						<b-form-text label="View" readonly dense :value="view" />
					</b-col>
				</b-row>
				Content<br />
				<div style="height:300px;"><Viewer ref="viewer" /><br /></div>
				Reply ({{ replyCount }})<br />
				<b-table dense>
					<tbody>
						<tr v-for="(reply, index) in replies" :key="index">
							<td style="width:110px; padding: 0;" >
								<b-icon small>
									mdi-account
								</b-icon>
								{{ reply.writer }}
							</td>
							<td style="padding: 0;">{{ reply.content }}</td>
							<td style="width:140px; padding: 0;" >
								{{ reply.regDttm }}
							</td>
							<td style="width:30px; padding: 0;">
								<b-button
									@click="replyEdit(reply.replyNo, reply.content)"
									color="grey"
								/><svg xmlns="http://www.w3.org/2000/svg" width="5" height="5" fill="currentColor" class="bi bi-pen-fill" viewBox="0 0 16 16">
                <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001z"/>
              </svg>
							</td>
							<td style="width:30px; padding: 0;">
								<b-button
									@click="replyDel(reply.replyNo)"
									variant="danger"
								/><svg xmlns="http://www.w3.org/2000/svg" width="5" height="5" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
              </svg>
							</td>
						</tr>
					</tbody>
				</b-table>
				<b-row>
					<b-col cols="12" md="11" style="padding: 0px 12px;">
						<b-textarea
							clearable
							clear-icon="mdi-close-circle"
							rows="2"
							no-resize
							full-width
							v-model="comment"
						></b-textarea>
					</b-col>
					<b-col md="1" align-self="center" style="padding: 0px 10px;">
						<b-button
							@click="replySave"
							color="indigo"
							rounded
							small
							iconName="mdi-pencil"
							btnName="Save"
						></b-button>
					</b-col>
				</b-row>
			</b-card-text>

				<b-button
					@click="movePage('/edit?docNo=' + docNo)"
					color="warning"
					rounded
					small
					iconName="mdi-pencil"
					btnName="Edit"
				></b-button>
				<b-button
					@click="del"
					color="error"
					rounded
					small
					iconName="mdi-delete-forever"
					btnName="Delete"
				></b-button>
				<b-button
					@click="movePage('/list')"
					color="grey darken-1"
					rounded
					small
					iconName="mdi-arrow-left"
					btnName="Back"
				></b-button>
		</b-card>
	</b-container>
</template>

<script>
import Viewer from "../common/Viewer";
import {
	getBoardDetailAPI,
	deleteBoardAPI,
	insertReplyAPI,
	getReplyListAPI,
	deleteReplyAPI,
	updateReplyAPI,
} from '../../../api/index'

export default {
  components:{
    Viewer,
  },
	data() {
		return {
			docNo: 0,
			title: '',
			writer: '',
			regDttm: '',
			view: 0,
			replyCount: 0,
			replies: null,
			comment: '',
      reply:'',
		}
	},
	mounted() {
		getBoardDetailAPI({
			params: {
				docNo: this.$route.query.docNo,
			},
		})
			.then(response => {
				this.docNo = response.data.docNo
				this.title = response.data.title
				this.$refs.viewer.setContent(response.data.content)
				this.writer = response.data.writer
				this.regDttm = response.data.regDttm
				this.view = response.data.view
				this.replyCount = response.data.reply
			})
			.catch(error => {
				console.log(error)
			})
		getReplyListAPI({
			params: {
				docNo: this.$route.query.docNo,
			},
		})
			.then(response => {
				this.replies = response.data
			})
			.catch(error => {
				console.log(error)
			})
	},
	methods: {
		async del() {
      deleteBoardAPI({
        params: {
          docNo: this.docNo,
        },
      })
        .then(response => {
          if (response.data > 0) {
            this.movePage('/list')
          }
        })
        .catch(error => {
          console.log(error)
        })
		},
		replySave() {
			if (this.comment !== null) {
				insertReplyAPI({
					params: {
						docNo: this.docNo,
						comment: this.comment,
					},
				})
					.then(response => {
						if (response.data > 0) {
							this.refresh()
						}
					})
					.catch(error => {
						console.log(error)
					})
			}
		},
		async replyEdit(replyNo, comment) {
			let res = await this.promptDialog('Edit Reply', 'Comment', comment)
			if (res) {
				updateReplyAPI({
					params: {
						replyNo: replyNo,
						docNo: this.docNo,
						comment: res,
					},
				})
					.then(response => {
						if (response.data > 0) {
							this.refresh()
						}
					})
					.catch(error => {
						console.log(error)
					})
			}
		},
		async replyDel(replyNo) {
			let res = await this.confirmDialog(
				'Confirm Reply Delete',
				'Are you sure you want to delete it?',
			)
			if (res) {
				deleteReplyAPI({
					params: {
						replyNo: replyNo,
						docNo: this.docNo,
					},
				})
					.then(response => {
						if (response.data > 0) {
							this.refresh()
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
