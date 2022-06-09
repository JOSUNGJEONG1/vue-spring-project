<template>
	<b-container>
		<b-card elevation="10" outlined width="100%" class="mx-auto">
			<b-card-title>
				공모전
			</b-card-title>
			<b-card-text>
				<b-row>
          <b-col align-self="end" cols="12" md="2">
            <b-form-select
              v-model="schType"
              placeholder="검색조건"
              :options="conditions"
            ></b-form-select>
          </b-col>
          <b-col cols="12" md="8">
            <b-form-input
              v-model="schVal"
              placeholder="검색어"
              single-line
              @keypress.enter.prevent="getBoardList"
            ></b-form-input>
          </b-col>
					<b-col>
						<b-table
							class="elevation-1"
							@click:row="onClickRow"
							:headers="headers"
							:items="document"
							:options.sync="options"
							:server-items-length="totalCount"
							:footer-props="footerOptions"
							:loading="loading"
						>
							<template slot="items" slot-scope="props">
								<td>{{ props.item.docNo }}</td>
								<td>{{ props.item.title }}</td>
								<td>{{ props.item.writer }}</td>
								<td>{{ props.item.regDttm }}</td>
								<td>{{ props.item.view }}</td>
								<td>{{ props.item.reply }}</td>
							</template>
						</b-table>
					</b-col>
				</b-row>
			</b-card-text>
		</b-card>
			<b-button
				@click="movePage('/write')"
				color="blue-grey darken-1"
				fixed
			><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pen-fill" viewBox="0 0 16 16">
        <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001z"/>
      </svg></b-button>
	</b-container>
</template>

<script>
import {getBoardListAPI} from "../../../api";

export default {
	data() {
		return {
			headers: [
				{ text: '글 번호', align: 'center', value: 'docNo' },
				{ text: '제목', align: 'start', value: 'title' },
				{ text: '작성자', align: 'center', value: 'writer' },
				{ text: '작성일시', align: 'center', value: 'regDttm' },
				{ text: '조회수', align: 'center', value: 'view' },
				{ text: '댓글수', align: 'center', value: 'reply' },
			],
			document: [],
			options: {
				multiSort: true,
				sortBy: [],
				sortDesc: [],
				page: 1,
				itemsPerPage: 5,
			},
			footerOptions: {
				'items-per-page-options': [5, 10, 25, 50, 100],
			},
			totalCount: 0,
			loading: false,
			conditions: [
				{ text: '글 번호', value: 'docNo' },
				{ text: '제목', value: 'title' },
				{ text: '작성자', value: 'writer' },
			],
			schType: '',
			schVal: '',
		}
	},
	mounted() {
		this.getBoardList()
	},
	watch: {
		options: {
			handler() {
				this.getBoardList()
			},
			deep: true,
		},
	},
	methods: {
		getBoardDataFromAPI(page, itemsPerPage, sort) {
			return getBoardListAPI({
				params: {
					schType: this.schType,
					schVal: this.schVal,
					page: page,
					rows: itemsPerPage,
					sort: encodeURIComponent(sort),
				},
			})
				.then(response => {
					return response.data
				})
				.catch(error => {
					console.log(error)
				})
		},
		getBoardList() {
			const vm = this
			this.loading = true
			// eslint-disable-next-line
			return new Promise((resolve, reject) => {
				const { sortBy, sortDesc, page, itemsPerPage } = this.options
				let sort = []

				if (sortBy.length > 0) {
					// eslint-disable-next-line
					sortBy.forEach((value, index) => {
						sort.push(
							value
								.replace(/[A-Z]/g, function(str) {
									return '_' + str[0]
								})
								.toUpperCase() +
								' ' +
								(sortDesc[index] ? 'desc' : 'asc'),
						)
					})
				} else {
					sort.push('DOC_NO desc')
				}

				let items = this.getBoardDataFromAPI(page, itemsPerPage, sort).then(
					response => {
						items = response.data
						const total = response.total

						setTimeout(() => {
							vm.loading = false
							this.document = items
							this.totalCount = total
						}, 1000)
					},
				)
			})
		},
		onClickRow(event, data) {
			this.movePage('/detail?docNo=' + data.item.docNo)
		},
	},
}
</script>
